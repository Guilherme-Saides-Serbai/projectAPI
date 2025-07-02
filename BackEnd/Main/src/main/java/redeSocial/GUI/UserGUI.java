package redeSocial.GUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import redeSocial.DAOs.UserDAO;
import redeSocial.Entidade.User;

import java.util.List;

@RestController
@RequestMapping({ "/users", "/users/" })
public class UserGUI {

    @Autowired
    private UserDAO userDAO;

    // CREATE
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User saved = userDAO.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
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
    @PutMapping({ "/{id}", "/{id}/" })
    public ResponseEntity<User> update(
            @PathVariable Long id,
            @RequestBody User payload) {

        return userDAO.findById(id)
                .map(existing -> {
                    existing.setEmail(payload.getEmail());
                    existing.setSenha(payload.getSenha());
                    existing.setSlug(payload.getSlug());
                    existing.setAdmin(payload.getAdmin());
                    existing.setNomeUser(payload.getNomeUser());
                    // dataInsercao NÃO é alterada
                    User updated = userDAO.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
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