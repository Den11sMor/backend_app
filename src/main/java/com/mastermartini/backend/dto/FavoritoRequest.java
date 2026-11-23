
package com.mastermartini.backend.dto;

import jakarta.validation.constraints.NotNull;

public class FavoritoRequest {

    @NotNull(message = "El ID del contenido es obligatorio.")
    private Integer idContenido;

    public FavoritoRequest() {
    }

    public Integer getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Integer idContenido) {
        this.idContenido = idContenido;
    }
}
