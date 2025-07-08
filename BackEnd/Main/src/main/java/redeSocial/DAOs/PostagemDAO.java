// PostagemDAO.java
package redeSocial.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import redeSocial.Entidade.Postagem;

public interface PostagemDAO extends JpaRepository<Postagem, Long> {
    // Aqui você pode definir buscas customizadas,
    // ex: List<Postagem> findByUserIdUser(Long idUser);
}
