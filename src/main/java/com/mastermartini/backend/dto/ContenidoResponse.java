
package com.mastermartini.backend.dto;

import com.mastermartini.backend.entity.Contenido;

public class ContenidoResponse {

    private Integer id;
    private String titulo;
    private String tipo;
    private String categoria;
    private String duracion;
    private String descripcion;
    private String detalle;

    public ContenidoResponse() {
    }

    public ContenidoResponse(Contenido contenido) {
        this.id = contenido.getId();
        this.titulo = contenido.getTitulo();
        this.tipo = contenido.getTipo();
        this.categoria = contenido.getCategoria();
        this.duracion = contenido.getDuracion();
        this.descripcion = contenido.getDescripcion();
        this.detalle = contenido.getDetalle();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
