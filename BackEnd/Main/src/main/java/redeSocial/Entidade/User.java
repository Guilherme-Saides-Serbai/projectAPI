package redeSocial.Entidade;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "senha_hash", nullable = false, length = 255)
    private String senhaHash;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "nome_user", length = 100)
    private String nomeUser;

    @Column(name = "data_insercao", nullable = false, updatable = false)
    private Instant dataInsercao;

    @PrePersist
    protected void onCreate() {
        this.dataInsercao = Instant.now();
    }

    public User() {
        // Construtor vazio necessário para o Hibernate
    }

    // Getters e Setters

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getNomeUser() {
        return nomeUser;
    }

    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public Instant getDataInsercao() {
        return dataInsercao;
    }
}
