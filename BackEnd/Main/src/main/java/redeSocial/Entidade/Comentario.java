package redeSocial.Entidade;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_postagem", nullable = false, foreignKey = @ForeignKey(name = "fk_comentario_postagem", foreignKeyDefinition = "FOREIGN KEY (id_postagem) REFERENCES postagem(id_postagem) ON DELETE CASCADE ON UPDATE CASCADE"))
    private Postagem postagem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_comentario_users", foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    @Column(name = "texto_comentario", columnDefinition = "TEXT", nullable = false)
    private String textoComentario;

    @Column(name = "data_insercao", nullable = false, updatable = false)
    private Instant dataInsercao;

    @Column(name = "data_alteracao", nullable = false)
    private Instant dataAlteracao;

    @PrePersist
    protected void onCreate() {
        this.dataInsercao = Instant.now();
        this.dataAlteracao = this.dataInsercao;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAlteracao = Instant.now();
    }

    // Getters e Setters
    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Postagem getPostagem() {
        return postagem;
    }

    public void setPostagem(Postagem postagem) {
        this.postagem = postagem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTextoComentario() {
        return textoComentario;
    }

    public void setTextoComentario(String textoComentario) {
        this.textoComentario = textoComentario;
    }

    public Instant getDataInsercao() {
        return dataInsercao;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataInsercao(Instant dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public void setDataAlteracao(Instant dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}