package redeSocial.DTOs;

import java.time.Instant;

public class PostagemDTO {
    private Long idPostagem;
    private Boolean anuncio;
    private String textoPost;
    private String atributosJson = "{}";
    private Instant dataInsercao;
    private Instant dataAlteracao;
    private UserDTO user;

    // Getters e Setters
    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
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
        return atributosJson != null ? atributosJson : "{}";
    }

    public void setAtributosJson(String atributosJson) {
        this.atributosJson = atributosJson != null ? atributosJson : "{}";
    }

    public Instant getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Instant dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public Instant getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Instant dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
