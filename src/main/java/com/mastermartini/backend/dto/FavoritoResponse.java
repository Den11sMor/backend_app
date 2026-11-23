
package com.mastermartini.backend.dto;

import com.mastermartini.backend.entity.Favorito;

public class FavoritoResponse {

    private Long idUsuario;
    private Integer idContenido;
    private String titulo;
    private String tipo;
    private String categoria;
    private String descripcion;

    public FavoritoResponse() {
    }

    public FavoritoResponse(Favorito favorito) {
        this.idUsuario = favorito.getUsuario().getIdUsuario();
        this.idContenido = favorito.getContenido().getId();
        this.titulo = favorito.getContenido().getTitulo();
        this.tipo = favorito.getContenido().getTipo();
        this.categoria = favorito.getContenido().getCategoria();
        this.descripcion = favorito.getContenido().getDescripcion();
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdContenido() {
        return idContenido;
    }

    public void setIdContenido(Integer idContenido) {
        this.idContenido = idContenido;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
