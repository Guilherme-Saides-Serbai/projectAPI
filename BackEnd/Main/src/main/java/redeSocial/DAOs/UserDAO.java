package redeSocial.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import redeSocial.Entidade.User;

// Com esta interface, você herda todos os métodos de CRUD:
// save(), findAll(), findById(), deleteById(), etc.
public interface UserDAO extends JpaRepository<User, Long> {
    // Se quiser buscas customizadas, ex:
    // Optional<User> findByEmail(String email);

}