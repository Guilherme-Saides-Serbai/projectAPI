package redeSocial.GUI;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import redeSocial.Entidade.Curtida;
import redeSocial.Entidade.Comentario;
import redeSocial.Entidade.Postagem;
import redeSocial.Entidade.User;
import redeSocial.DAOs.CurtidaDAO;
import redeSocial.DTOs.CurtidaDTO;
import redeSocial.DTOs.UserDTO;

@RestController
@RequestMapping({ "/curtidas", "/curtidas/", "/curtida", "/curtida/" })
public class CurtidaGUI {

    @Autowired
    private CurtidaDAO curtidaDAO;

    // Método para converter Curtida para DTO
    private CurtidaDTO convertToDTO(Curtida curtida) {
        CurtidaDTO dto = new CurtidaDTO();
        dto.setIdCurtida(curtida.getIdCurtida());
        dto.setDataInsercao(curtida.getDataInsercao());

        if (curtida.getUser() != null) {
            dto.setIdUser(curtida.getUser().getIdUser());

            UserDTO userDTO = new UserDTO();
            userDTO.setIdUser(curtida.getUser().getIdUser());
            userDTO.setEmail(curtida.getUser().getEmail());
            userDTO.setSlug(curtida.getUser().getSlug());
            userDTO.setIsAdmin(curtida.getUser().getIsAdmin());
            userDTO.setNomeUser(curtida.getUser().getNomeUser());

            dto.setUser(userDTO);
        }

        if (curtida.getComentario() != null) {
            dto.setIdComentario(curtida.getComentario().getIdComentario());
        }

        if (curtida.getPostagem() != null) {
            dto.setIdPostagem(curtida.getPostagem().getIdPostagem());
        }

        return dto;
    }

    // Método para converter DTO para Curtida
    private Curtida convertToEntity(CurtidaDTO dto) {
        Curtida curtida = new Curtida();
        curtida.setIdCurtida(dto.getIdCurtida());

        // Relacionamento com User
        if (dto.getUser() != null && dto.getUser().getIdUser() != null) {
            User user = new User();
            user.setIdUser(dto.getUser().getIdUser());
            curtida.setUser(user);
        }

        // Relacionamento com Comentário
        if (dto.getIdComentario() != null) {
            Comentario comentario = new Comentario();
            comentario.setIdComentario(dto.getIdComentario());
            curtida.setComentario(comentario);
        }

        // Relacionamento com Postagem
        if (dto.getIdPostagem() != null) {
            Postagem postagem = new Postagem();
            postagem.setIdPostagem(dto.getIdPostagem());
            curtida.setPostagem(postagem);
        }

        return curtida;
    }

    // CREATE (Curtir)
    @PostMapping
    public ResponseEntity<?> curtir(@RequestBody CurtidaDTO curtidaDTO) {
        try {
            // Validações
            if (curtidaDTO.getUser() == null || curtidaDTO.getUser().getIdUser() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário é obrigatório");
            }

            // Verificar se já existe curtida para o mesmo usuário e target
            boolean alreadyLiked = false;

            if (curtidaDTO.getIdPostagem() != null) {
                alreadyLiked = curtidaDAO.existsByUserIdUserAndPostagemIdPostagem(
                        curtidaDTO.getUser().getIdUser(),
                        curtidaDTO.getIdPostagem());
            } else if (curtidaDTO.getIdComentario() != null) {
                alreadyLiked = curtidaDAO.existsByUserIdUserAndComentarioIdComentario(
                        curtidaDTO.getUser().getIdUser(),
                        curtidaDTO.getIdComentario());
            }

            if (alreadyLiked) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("O usuário já curtiu este conteúdo");
            }

            // Validação do target (postagem ou comentário)
            if (curtidaDTO.getIdPostagem() == null && curtidaDTO.getIdComentario() == null) {
                return ResponseEntity.badRequest().body("Deve ser informado um ID de postagem ou comentário");
            }

            if (curtidaDTO.getIdPostagem() != null && curtidaDTO.getIdComentario() != null) {
                return ResponseEntity.badRequest()
                        .body("A curtida deve ser em uma postagem ou um comentário, não ambos");
            }

            Curtida curtida = convertToEntity(curtidaDTO);
            Curtida saved = curtidaDAO.save(curtida);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao curtir: " + e.getMessage());
        }
    }

    // DELETE (Descurtir)
    @DeleteMapping
    public ResponseEntity<String> descurtir(@RequestBody CurtidaDTO curtidaDTO) {
        try {
            // Validações
            if (curtidaDTO.getUser() == null || curtidaDTO.getUser().getIdUser() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário é obrigatório");
            }

            if (curtidaDTO.getIdPostagem() == null && curtidaDTO.getIdComentario() == null) {
                return ResponseEntity.badRequest().body("Deve ser informado um ID de postagem ou comentário");
            }

            // Buscar curtida existente
            Curtida curtida = null;

            if (curtidaDTO.getIdPostagem() != null) {
                curtida = curtidaDAO.findByUserIdUserAndPostagemIdPostagem(
                        curtidaDTO.getUser().getIdUser(),
                        curtidaDTO.getIdPostagem());
            } else if (curtidaDTO.getIdComentario() != null) {
                curtida = curtidaDAO.findByUserIdUserAndComentarioIdComentario(
                        curtidaDTO.getUser().getIdUser(),
                        curtidaDTO.getIdComentario());
            }

            if (curtida == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Curtida não encontrada");
            }

            curtidaDAO.delete(curtida);
            return ResponseEntity.ok("✅ Curtida removida com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao descurtir: " + e.getMessage());
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<CurtidaDTO>> listarTodas() {
        List<Curtida> curtidas = curtidaDAO.findAll();
        List<CurtidaDTO> dtos = curtidas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // paginacao
    // @GetMapping
    // public ResponseEntity<Page<CurtidaDTO>> listarTodas(
    // @RequestParam(defaultValue = "0") int page,
    // @RequestParam(defaultValue = "10") int size) {

    // Pageable pageable = PageRequest.of(page, size,
    // Sort.by("dataInsercao").descending());
    // Page<Curtida> curtidasPage = curtidaDAO.findAll(pageable);

    // Page<CurtidaDTO> dtosPage = curtidasPage.map(this::convertToDTO);

    // return ResponseEntity.ok(dtosPage);
    // }

    // GET Count for Post
    @GetMapping("/postagem/{idPostagem}/count")
    public ResponseEntity<Long> countCurtidasPostagem(@PathVariable Long idPostagem) {
        long count = curtidaDAO.countByPostagemIdPostagem(idPostagem);
        return ResponseEntity.ok(count);
    }

    // GET Count for Comment
    @GetMapping("/comentario/{idComentario}/count")
    public ResponseEntity<Long> countCurtidasComentario(@PathVariable Long idComentario) {
        long count = curtidaDAO.countByComentarioIdComentario(idComentario);
        return ResponseEntity.ok(count);
    }

    // GET Check if user liked a post
    @GetMapping("/postagem/{idPostagem}/usuario/{idUsuario}")
    public ResponseEntity<Boolean> usuarioCurtiuPostagem(
            @PathVariable Long idPostagem,
            @PathVariable Long idUsuario) {
        boolean liked = curtidaDAO.existsByUserIdUserAndPostagemIdPostagem(idUsuario, idPostagem);
        return ResponseEntity.ok(liked);
    }

    // GET Check if user liked a comment
    @GetMapping("/comentario/{idComentario}/usuario/{idUsuario}")
    public ResponseEntity<Boolean> usuarioCurtiuComentario(
            @PathVariable Long idComentario,
            @PathVariable Long idUsuario) {
        boolean liked = curtidaDAO.existsByUserIdUserAndComentarioIdComentario(idUsuario, idComentario);
        return ResponseEntity.ok(liked);
    }
}