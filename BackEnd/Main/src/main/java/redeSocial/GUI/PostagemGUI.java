// PostagemGUI.java
package redeSocial.GUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import redeSocial.Entidade.User;
import redeSocial.DAOs.PostagemDAO;
import redeSocial.DTOs.PostagemDTO;
import redeSocial.DTOs.UserDTO;
import redeSocial.Entidade.Postagem;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping({ "/postagens", "/postagens/", "/postagem", "/postagem/" })
public class PostagemGUI {

    @Autowired
    private PostagemDAO postagemDAO;

    // Método para converter Postagem para DTO
    private PostagemDTO convertToDTO(Postagem postagem) {
        PostagemDTO dto = new PostagemDTO();
        dto.setIdPostagem(postagem.getIdPostagem());
        dto.setAnuncio(postagem.getAnuncio());
        dto.setTextoPost(postagem.getTextoPost());
        dto.setAtributosJson(postagem.getAtributosJson());
        dto.setDataInsercao(postagem.getDataInsercao());
        dto.setDataAlteracao(postagem.getDataAlteracao());

        UserDTO userDTO = new UserDTO();
        userDTO.setIdUser(postagem.getUser().getIdUser());
        userDTO.setEmail(postagem.getUser().getEmail());
        userDTO.setSlug(postagem.getUser().getSlug());
        userDTO.setIsAdmin(postagem.getUser().getIsAdmin());
        userDTO.setNomeUser(postagem.getUser().getNomeUser());

        dto.setUser(userDTO);
        return dto;
    }

    // Método para converter DTO para Postagem
    private Postagem convertToEntity(PostagemDTO dto) {
        Postagem postagem = new Postagem();
        postagem.setIdPostagem(dto.getIdPostagem());
        postagem.setAnuncio(dto.getAnuncio() != null ? dto.getAnuncio() : false);
        postagem.setTextoPost(dto.getTextoPost());

        String jsonValue = dto.getAtributosJson();
        if (jsonValue == null || jsonValue.trim().isEmpty()) {
            jsonValue = "{}";
        }
        postagem.setAtributosJson(jsonValue);

        // Corrigindo o relacionamento com User
        if (dto.getUser() != null && dto.getUser().getIdUser() != null) {
            User user = new User();
            user.setIdUser(dto.getUser().getIdUser());
            postagem.setUser(user);
        } else {
            throw new IllegalArgumentException("O ID do usuário é obrigatório");
        }

        return postagem;
    }

    // CREATE com DTO
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PostagemDTO postagemDTO) {
        try {
            // Validação do usuário
            if (postagemDTO.getUser() == null || postagemDTO.getUser().getIdUser() == null) {
                return ResponseEntity.badRequest().body("O ID do usuário é obrigatório");
            }

            // Garante que sempre tenha um JSON válido
            String json = (postagemDTO.getAtributosJson() == null || postagemDTO.getAtributosJson().trim().isEmpty())
                    ? "{}"
                    : postagemDTO.getAtributosJson();
            postagemDTO.setAtributosJson(json);

            Postagem postagem = convertToEntity(postagemDTO);
            Postagem saved = postagemDAO.save(postagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar postagem: " + e.getMessage());
        }
    }

    // READ ALL com DTO
    @GetMapping
    public ResponseEntity<List<PostagemDTO>> listarTodas() {
        List<Postagem> postagens = postagemDAO.findAll();
        List<PostagemDTO> dtos = postagens.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // READ ONE com DTO
    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return postagemDAO.findById(id)
                .map(postagem -> ResponseEntity.ok(convertToDTO(postagem)))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE com DTO
    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody PostagemDTO postagemDTO) {
        try {
            return postagemDAO.findById(id)
                    .map(existing -> {
                        // Atualiza apenas os campos permitidos pelo DTO
                        existing.setAnuncio(postagemDTO.getAnuncio());
                        existing.setTextoPost(postagemDTO.getTextoPost());
                        existing.setAtributosJson(postagemDTO.getAtributosJson());

                        Postagem updated = postagemDAO.save(existing);
                        return ResponseEntity.ok(convertToDTO(updated));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar postagem: " + e.getMessage());
        }
    }

    // DELETE (mantido igual, pois não retorna a entidade)
    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return postagemDAO.findById(id)
                .map(p -> {
                    postagemDAO.deleteById(id);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body("✅ Postagem com ID " + id + " foi removida com sucesso.");
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("⚠️ Postagem com ID " + id + " não foi encontrada."));
    }
}
