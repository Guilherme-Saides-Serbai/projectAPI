package redeSocial.GUI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import redeSocial.Entidade.Seguidor;
import redeSocial.Entidade.User;
import redeSocial.DAOs.SeguidorDAO;
import redeSocial.DTOs.SeguidorDTO;
import redeSocial.DTOs.UserDTO;

@RestController
@RequestMapping({ "/seguidores", "/seguidores/", "/seguidor", "/seguidor/" })
public class SeguidorGUI {

    @Autowired
    private SeguidorDAO seguidorDAO;

    // Método para converter Seguidor para DTO
    private SeguidorDTO convertToDTO(Seguidor seguidor) {
        SeguidorDTO dto = new SeguidorDTO();
        dto.setIdSeguidor(seguidor.getIdSeguidor());
        dto.setDataInsercao(seguidor.getDataInsercao());

        if (seguidor.getUserSeguido() != null) {
            dto.setIdUserSeguido(seguidor.getUserSeguido().getIdUser());

            UserDTO userSeguidoDTO = new UserDTO();
            userSeguidoDTO.setIdUser(seguidor.getUserSeguido().getIdUser());
            userSeguidoDTO.setEmail(seguidor.getUserSeguido().getEmail());
            userSeguidoDTO.setSlug(seguidor.getUserSeguido().getSlug());
            userSeguidoDTO.setIsAdmin(seguidor.getUserSeguido().getIsAdmin());
            userSeguidoDTO.setNomeUser(seguidor.getUserSeguido().getNomeUser());

            dto.setUserSeguido(userSeguidoDTO);
        }

        if (seguidor.getSeguidor() != null) {
            dto.setIdSeguidorUser(seguidor.getSeguidor().getIdUser());

            UserDTO seguidorUserDTO = new UserDTO();
            seguidorUserDTO.setIdUser(seguidor.getSeguidor().getIdUser());
            seguidorUserDTO.setEmail(seguidor.getSeguidor().getEmail());
            seguidorUserDTO.setSlug(seguidor.getSeguidor().getSlug());
            seguidorUserDTO.setIsAdmin(seguidor.getSeguidor().getIsAdmin());
            seguidorUserDTO.setNomeUser(seguidor.getSeguidor().getNomeUser());

            dto.setSeguidorUser(seguidorUserDTO);
        }

        return dto;
    }

    // Método para converter DTO para Seguidor
    private Seguidor convertToEntity(SeguidorDTO dto) {
        Seguidor seguidor = new Seguidor();
        seguidor.setIdSeguidor(dto.getIdSeguidor());

        // Usuário sendo seguido
        if (dto.getIdUserSeguido() != null) {
            User userSeguido = new User();
            userSeguido.setIdUser(dto.getIdUserSeguido());
            seguidor.setUserSeguido(userSeguido);
        }

        // Usuário seguidor
        if (dto.getIdSeguidorUser() != null) {
            User seguidorUser = new User();
            seguidorUser.setIdUser(dto.getIdSeguidorUser());
            seguidor.setSeguidor(seguidorUser);
        }

        return seguidor;
    }

    // READ ALL com DTO
    @GetMapping
    public ResponseEntity<List<SeguidorDTO>> listarTodos() {
        List<Seguidor> seguidores = seguidorDAO.findAll();
        List<SeguidorDTO> dtos = seguidores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // SEGUIR (CREATE)
    @PostMapping
    public ResponseEntity<?> seguir(@RequestBody SeguidorDTO seguidorDTO) {
        try {
            // Validações
            if (seguidorDTO.getIdUserSeguido() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário a ser seguido é obrigatório");
            }

            if (seguidorDTO.getIdSeguidorUser() == null) {
                return ResponseEntity.badRequest().body("O ID do seguidor é obrigatório");
            }

            if (seguidorDTO.getIdUserSeguido().equals(seguidorDTO.getIdSeguidorUser())) {
                return ResponseEntity.badRequest().body("Um usuário não pode seguir a si mesmo");
            }

            // Verificar se já existe relação
            boolean alreadyFollowing = seguidorDAO.existsByUserSeguidoIdUserAndSeguidorIdUser(
                    seguidorDTO.getIdUserSeguido(),
                    seguidorDTO.getIdSeguidorUser());

            if (alreadyFollowing) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("O usuário já está seguindo este perfil");
            }

            Seguidor seguidor = convertToEntity(seguidorDTO);
            Seguidor saved = seguidorDAO.save(seguidor);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao seguir usuário: " + e.getMessage());
        }
    }

    // DEIXAR DE SEGUIR (DELETE)
    @DeleteMapping
    public ResponseEntity<String> deixarDeSeguir(@RequestBody SeguidorDTO seguidorDTO) {
        try {
            // Validações
            if (seguidorDTO.getIdUserSeguido() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário seguido é obrigatório");
            }

            if (seguidorDTO.getIdSeguidorUser() == null) {
                return ResponseEntity.badRequest().body("O ID do seguidor é obrigatório");
            }

            // Buscar relação de seguimento
            Seguidor seguidor = seguidorDAO.findByUserSeguidoIdUserAndSeguidorIdUser(
                    seguidorDTO.getIdUserSeguido(),
                    seguidorDTO.getIdSeguidorUser());

            if (seguidor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Relação de seguimento não encontrada");
            }

            seguidorDAO.delete(seguidor);
            return ResponseEntity.ok("✅ Deixou de seguir com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deixar de seguir: " + e.getMessage());
        }
    }

    // Contar seguidores de um usuário
    @GetMapping("/usuario/{idUser}/seguidores/count")
    public ResponseEntity<Long> contarSeguidores(@PathVariable Long idUser) {
        long count = seguidorDAO.countByUserSeguidoIdUser(idUser);
        return ResponseEntity.ok(count);
    }

    // Contar quem o usuário segue
    @GetMapping("/usuario/{idUser}/seguindo/count")
    public ResponseEntity<Long> contarSeguindo(@PathVariable Long idUser) {
        long count = seguidorDAO.countBySeguidorIdUser(idUser);
        return ResponseEntity.ok(count);
    }

    // Verificar se um usuário segue outro
    @GetMapping("/usuario/{idSeguidor}/segue/{idSeguido}")
    public ResponseEntity<Boolean> verificarSeSegue(
            @PathVariable Long idSeguidor,
            @PathVariable Long idSeguido) {
        boolean segue = seguidorDAO.existsByUserSeguidoIdUserAndSeguidorIdUser(idSeguido, idSeguidor);
        return ResponseEntity.ok(segue);
    }
}