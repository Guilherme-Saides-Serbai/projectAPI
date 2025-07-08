package redeSocial.DTOs;

import java.time.Instant;

public class SeguidorDTO {
    private Long idSeguidor;
    private Long idUserSeguido;
    private Long idSeguidorUser;
    private Instant dataInsercao;
    private UserDTO userSeguido;
    private UserDTO seguidorUser;

    // Getters e Setters
    public Long getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(Long idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public Long getIdUserSeguido() {
        return idUserSeguido;
    }

    public void setIdUserSeguido(Long idUserSeguido) {
        this.idUserSeguido = idUserSeguido;
    }

    public Long getIdSeguidorUser() {
        return idSeguidorUser;
    }

    public void setIdSeguidorUser(Long idSeguidorUser) {
        this.idSeguidorUser = idSeguidorUser;
    }

    public Instant getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Instant dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public UserDTO getUserSeguido() {
        return userSeguido;
    }

    public void setUserSeguido(UserDTO userSeguido) {
        this.userSeguido = userSeguido;
    }

    public UserDTO getSeguidorUser() {
        return seguidorUser;
    }

    public void setSeguidorUser(UserDTO seguidorUser) {
        this.seguidorUser = seguidorUser;
    }
}