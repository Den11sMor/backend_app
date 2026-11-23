
package com.mastermartini.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ContenidoRequest {

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 150)
    private String titulo;

    @NotBlank(message = "El tipo es obligatorio.")
    @Size(max = 50)
    private String tipo;

    @NotBlank(message = "La categoría es obligatoria.")
    @Size(max = 100)
    private String categoria;

    private String duracion;

    private String descripcion;

    private String detalle;

    public ContenidoRequest() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
