package redeSocial.Entidade;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "seguidor")
public class Seguidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguidor")
    private Long idSeguidor;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_seguidor_user_being_followed", foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User userSeguido; // Usuário que está sendo seguido

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_seguidor_user", nullable = false, foreignKey = @ForeignKey(name = "fk_seguidor_user_who_is_following", foreignKeyDefinition = "FOREIGN KEY (id_seguidor_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User seguidor; // Usuário que está seguindo

    @Column(name = "data_insercao", nullable = false, updatable = false)
    private Instant dataInsercao;

    @PrePersist
    protected void onCreate() {
        this.dataInsercao = Instant.now();
    }

    // Getters e Setters
    public Long getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(Long idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public User getUserSeguido() {
        return userSeguido;
    }

    public void setUserSeguido(User userSeguido) {
        this.userSeguido = userSeguido;
    }

    public User getSeguidor() {
        return seguidor;
    }

    public void setSeguidor(User seguidor) {
        this.seguidor = seguidor;
    }

    public Instant getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Instant dataInsercao) {
        this.dataInsercao = dataInsercao;
    }
}