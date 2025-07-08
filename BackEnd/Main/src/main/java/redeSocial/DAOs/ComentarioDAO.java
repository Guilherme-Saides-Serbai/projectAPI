package redeSocial.DAOs;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import redeSocial.Entidade.Comentario;

public interface ComentarioDAO extends JpaRepository<Comentario, Long> {
    // Buscas customizadas podem ser adicionadas aqui
    List<Comentario> findByPostagemIdPostagem(Long idPostagem);
}