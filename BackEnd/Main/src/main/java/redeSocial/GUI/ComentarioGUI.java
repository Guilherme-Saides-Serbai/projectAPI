package redeSocial.GUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import redeSocial.Entidade.Comentario;
import redeSocial.Entidade.Postagem;
import redeSocial.Entidade.User;
import redeSocial.DAOs.ComentarioDAO;
import redeSocial.DTOs.ComentarioDTO;
import redeSocial.DTOs.UserDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({ "/comentarios", "/comentarios/", "/comentario", "/comentario/" })
public class ComentarioGUI {

    @Autowired
    private ComentarioDAO comentarioDAO;

    // Método para converter Comentario para DTO
    private ComentarioDTO convertToDTO(Comentario comentario) {
        ComentarioDTO dto = new ComentarioDTO();
        dto.setIdComentario(comentario.getIdComentario());
        dto.setIdPostagem(comentario.getPostagem().getIdPostagem());
        dto.setTextoComentario(comentario.getTextoComentario());
        dto.setDataInsercao(comentario.getDataInsercao());
        dto.setDataAlteracao(comentario.getDataAlteracao());

        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(comentario.getUser().getIdUser());
        userDTO.setEmail(comentario.getUser().getEmail());
        userDTO.setSlug(comentario.getUser().getSlug());
        userDTO.setIsAdmin(comentario.getUser().getIsAdmin());
        userDTO.setNomeUser(comentario.getUser().getNomeUser());

        dto.setUser(userDTO);
        return dto;
    }

    // Método para converter DTO para Comentario
    private Comentario convertToEntity(ComentarioDTO dto) {
        Comentario comentario = new Comentario();
        comentario.setIdComentario(dto.getIdComentario());
        comentario.setTextoComentario(dto.getTextoComentario());

        // Relacionamento com Postagem
        if (dto.getIdPostagem() != null) {
            Postagem postagem = new Postagem();
            postagem.setIdPostagem(dto.getIdPostagem());
            comentario.setPostagem(postagem);
        }

        // Relacionamento com User
        if (dto.getUser() != null && dto.getUser().getIdUser() != null) {
            User user = new User();
            user.setIdUser(dto.getUser().getIdUser());
            comentario.setUser(user);
        }

        return comentario;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ComentarioDTO comentarioDTO) {
        try {
            // Validações
            if (comentarioDTO.getIdPostagem() == null) {
                return ResponseEntity.badRequest().body("O ID da postagem é obrigatório");
            }
            if (comentarioDTO.getUser() == null || comentarioDTO.getUser().getIdUser() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário é obrigatório");
            }
            if (comentarioDTO.getTextoComentario() == null || comentarioDTO.getTextoComentario().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("O texto do comentário é obrigatório");
            }

            Comentario comentario = convertToEntity(comentarioDTO);
            Comentario saved = comentarioDAO.save(comentario);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar comentário: " + e.getMessage());
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> listarTodos() {
        List<Comentario> comentarios = comentarioDAO.findAll();
        List<ComentarioDTO> dtos = comentarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // READ BY POSTAGEM
    @GetMapping("/postagem/{idPostagem}")
    public ResponseEntity<List<ComentarioDTO>> listarPorPostagem(@PathVariable Long idPostagem) {
        List<Comentario> comentarios = comentarioDAO.findByPostagemIdPostagem(idPostagem);
        List<ComentarioDTO> dtos = comentarios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // READ ONE
    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return comentarioDAO.findById(id)
                .map(comentario -> ResponseEntity.ok(convertToDTO(comentario)))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody ComentarioDTO comentarioDTO) {
        try {
            return comentarioDAO.findById(id)
                    .map(existing -> {
                        existing.setTextoComentario(comentarioDTO.getTextoComentario());

                        Comentario updated = comentarioDAO.save(existing);
                        return ResponseEntity.ok(convertToDTO(updated));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar comentário: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return comentarioDAO.findById(id)
                .map(c -> {
                    comentarioDAO.deleteById(id);
                    return ResponseEntity.ok("✅ Comentário com ID " + id + " foi removido com sucesso.");
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("⚠️ Comentário com ID " + id + " não foi encontrado."));
    }
}