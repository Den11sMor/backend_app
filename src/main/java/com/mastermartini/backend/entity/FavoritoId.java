
package com.mastermartini.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FavoritoId implements Serializable {

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "contenido_id")
    private Integer contenidoId;

    public FavoritoId() {
    }

    public FavoritoId(Long usuarioId, Integer contenidoId) {
        this.usuarioId = usuarioId;
        this.contenidoId = contenidoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getContenidoId() {
        return contenidoId;
    }

    public void setContenidoId(Integer contenidoId) {
        this.contenidoId = contenidoId;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) return true;
        if (!(objeto instanceof FavoritoId)) return false;

        FavoritoId that = (FavoritoId) objeto;

        return Objects.equals(usuarioId, that.usuarioId)
                && Objects.equals(contenidoId, that.contenidoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, contenidoId);
    }
}
