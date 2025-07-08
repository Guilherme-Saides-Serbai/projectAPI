package redeSocial.Entidade;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "curtida")
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curtida")
    private Long idCurtida;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_curtida_users", foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_comentario", foreignKey = @ForeignKey(name = "fk_curtida_comentario", foreignKeyDefinition = "FOREIGN KEY (id_comentario) REFERENCES comentario(id_comentario) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Comentario comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_postagem", foreignKey = @ForeignKey(name = "fk_curtida_postagem", foreignKeyDefinition = "FOREIGN KEY (id_postagem) REFERENCES postagem(id_postagem) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Postagem postagem;

    @Column(name = "data_insercao", nullable = false, updatable = false)
    private Instant dataInsercao;

    @PrePersist
    protected void onCreate() {
        this.dataInsercao = Instant.now();
    }

    // Getters e Setters
    public Long getIdCurtida() {
        return idCurtida;
    }

    public void setIdCurtida(Long idCurtida) {
        this.idCurtida = idCurtida;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    public Instant getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Instant dataInsercao) {
        this.dataInsercao = dataInsercao;
    }
}