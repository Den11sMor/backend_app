
package com.mastermartini.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contenidos")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Contenido")
public class Contenido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 160)
    private String titulo;

    @Column(name = "tipo", nullable = false, length = 50, insertable = false, updatable = false)
    private String tipo;

    @Column(name = "categoria", nullable = false, length = 100)
    private String categoria;

    @Column(name = "duracion", nullable = false, length = 80)
    private String duracion;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "detalle", nullable = false, columnDefinition = "TEXT")
    private String detalle;

    @Column(name = "video_url", length = 2000)
    private String videoUrl = "";

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public Contenido() {
    }

    public Contenido(Integer id, String titulo, String tipo, String categoria,
                     String duracion, String descripcion, String detalle) {
        this.id = id;
        this.titulo = titulo;
        this.tipo = tipo;
        this.categoria = categoria;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.detalle = detalle;
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
