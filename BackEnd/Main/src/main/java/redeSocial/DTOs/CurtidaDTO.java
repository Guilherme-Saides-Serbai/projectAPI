package redeSocial.DTOs;

import java.time.Instant;

public class CurtidaDTO {
    private Long idCurtida;
    private Long idUser;
    private Long idComentario;
    private Long idPostagem;
    private Instant dataInsercao;
    private UserDTO user;

    // Getters e Setters
    public Long getIdCurtida() {
        return idCurtida;
    }

    public void setIdCurtida(Long idCurtida) {
        this.idCurtida = idCurtida;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

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

    public Instant getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Instant dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}