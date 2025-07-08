package redeSocial.DTOs;

import java.time.Instant;

public class ComentarioDTO {
    private Long idComentario;
    private Long idPostagem;
    private Long idUser;
    private String textoComentario;
    private Instant dataInsercao;
    private Instant dataAlteracao;
    private UserDTO user;

    // Getters e Setters
    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Long getIdPostagem() {
        return idPostagem;
    }

    public void setIdPostagem(Long idPostagem) {
        this.idPostagem = idPostagem;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
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
