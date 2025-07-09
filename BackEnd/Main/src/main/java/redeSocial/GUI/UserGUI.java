package redeSocial.GUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import redeSocial.DAOs.UserDAO;
import redeSocial.Entidade.User;

import java.util.List;

@RestController
@RequestMapping({ "/users", "/users/", "/user", "/user/" })
public class UserGUI {

    @Autowired
    private UserDAO userDAO;

    // CREATE
    // @PostMapping
    // public ResponseEntity<User> create(@RequestBody User user) {
    // User saved = userDAO.save(user);
    // return ResponseEntity
    // .status(HttpStatus.CREATED)
    // .body(saved);
    // }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody User user) {
        try {
            User saved = userDAO.save(user);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(saved);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // ou HttpStatus.INTERNAL_SERVER_ERROR ou CREATED
                    .body("Erro ao criar INSERIR: usuário: " + e.getMessage());
        }
    }

    // READ ALL
    @GetMapping
    public List<User> listAll() {
        return userDAO.findAll();
    }

    // READ ONE
    @GetMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userDAO.findById(id)
                .map(u -> ResponseEntity.ok(u))
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    // @PutMapping({ "/{id}", "/{id}/" })
    // public ResponseEntity<User> update(
    // @PathVariable Long id,
    // @RequestBody User payload) {

    // return userDAO.findById(id)
    // .map(existing -> {
    // existing.setEmail(payload.getEmail());
    // existing.setSenhaHash(payload.getSenhaHash());
    // existing.setSlug(payload.getSlug());
    // existing.setIsAdmin(payload.getIsAdmin());
    // existing.setNomeUser(payload.getNomeUser());
    // // dataInsercao NÃO é alterada
    // // update antigo
    // User updated = userDAO.save(existing);
    // return ResponseEntity.ok(updated);
    // })
    // .orElse(ResponseEntity.notFound().build());
    // }
    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody User payload) {
        try {
            return userDAO.findById(id)
                    .map(existing -> {
                        existing.setEmail(payload.getEmail());
                        existing.setSenhaHash(payload.getSenhaHash());
                        existing.setSlug(payload.getSlug());
                        existing.setIsAdmin(payload.getIsAdmin());
                        existing.setNomeUser(payload.getNomeUser());
                        User updated = userDAO.save(existing);
                        return ResponseEntity.ok(updated);
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return userDAO.findById(id)
                .map(u -> {
                    userDAO.deleteById(id);
                    return ResponseEntity
                            .status(HttpStatus.OK)
                            .body("✅ Usuário com ID " + id + " foi removido com sucesso.");
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("⚠️ Usuário com ID " + id + " não foi encontrado."));
    }
}
