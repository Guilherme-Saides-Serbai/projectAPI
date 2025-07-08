package redeSocial.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import redeSocial.Entidade.Curtida;

public interface CurtidaDAO extends JpaRepository<Curtida, Long> {
    // Verifica se um usuário já curtiu uma postagem específica
    boolean existsByUserIdUserAndPostagemIdPostagem(Long userId, Long postagemId);

    // Verifica se um usuário já curtiu um comentário específico
    boolean existsByUserIdUserAndComentarioIdComentario(Long userId, Long comentarioId);

    // Conta curtidas de uma postagem
    long countByPostagemIdPostagem(Long postagemId);

    // Conta curtidas de um comentário
    long countByComentarioIdComentario(Long comentarioId);

    // Busca curtida por usuário e postagem
    Curtida findByUserIdUserAndPostagemIdPostagem(Long userId, Long postagemId);

    // Busca curtida por usuário e comentário
    Curtida findByUserIdUserAndComentarioIdComentario(Long userId, Long comentarioId);
}