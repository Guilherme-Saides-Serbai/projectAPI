package redeSocial.Entidade;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "users") // usa "users" para evitar palavra reservada
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false)
    private Boolean admin;

    @Column(name = "nome_user", length = 100)
    private String nomeUser;

    @Column(name = "data_insercao", nullable = false, updatable = false)
    private Instant dataInsercao;

    @PrePersist
    protected void onCreate() {
        this.dataInsercao = Instant.now();
    }

    // --- getters e setters ---
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
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