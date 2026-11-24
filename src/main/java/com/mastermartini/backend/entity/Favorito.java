
package com.mastermartini.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favoritos")
public class Favorito {

    @EmbeddedId
    private FavoritoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contenidoId")
    @JoinColumn(name = "contenido_id", nullable = false)
    private Contenido contenido;

    public Favorito() {
    }

    public Favorito(Usuario usuario, Contenido contenido) {
        this.usuario = usuario;
        this.contenido = contenido;
        this.id = new FavoritoId(
                usuario.getIdUsuario(),
                contenido.getId()
        );
    }

    public FavoritoId getId() {
        return id;
    }

    public void setId(FavoritoId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }
}
