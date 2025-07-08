package redeSocial.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import redeSocial.Entidade.Seguidor;

public interface SeguidorDAO extends JpaRepository<Seguidor, Long> {
    // Verifica se já existe uma relação de seguimento
    boolean existsByUserSeguidoIdUserAndSeguidorIdUser(Long idUserSeguido, Long idSeguidor);

    // Conta quantos seguidores um usuário tem
    long countByUserSeguidoIdUser(Long idUserSeguido);

    // Conta quantos usuários um usuário está seguindo
    long countBySeguidorIdUser(Long idSeguidor);

    // Busca relação de seguimento específica
    Seguidor findByUserSeguidoIdUserAndSeguidorIdUser(Long idUserSeguido, Long idSeguidor);
}