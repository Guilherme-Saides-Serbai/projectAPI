// Postagem.java
package redeSocial.Entidade;

import jakarta.persistence.*;

import java.time.Instant;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "postagem")
public class Postagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postagem")
    private Long idPostagem;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, foreignKey = @ForeignKey(name = "fk_postagem_users", foreignKeyDefinition = "FOREIGN KEY (id_user) REFERENCES users(id_user) ON DELETE CASCADE ON UPDATE CASCADE"))
    private User user;

    @Column(name = "anuncio", nullable = false)
    private Boolean anuncio = false;

    @Column(name = "texto_post", columnDefinition = "TEXT", nullable = false)
    private String textoPost;

    @Column(name = "atributos_json", columnDefinition = "JSONB")
    @JdbcTypeCode(SqlTypes.JSON)
    private String atributosJson = "{}";

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

    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Boolean anuncio) {
        this.anuncio = anuncio;
    }

    public String getTextoPost() {
        return textoPost;
    }

    public void setTextoPost(String textoPost) {
        this.textoPost = textoPost;
    }

    public String getAtributosJson() {
        return atributosJson;
    }

    public void setAtributosJson(String atributosJson) {
        this.atributosJson = atributosJson;
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