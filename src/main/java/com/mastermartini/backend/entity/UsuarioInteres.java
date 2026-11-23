
package com.mastermartini.backend.entity;

// Importaciones de Jakarta Persistence para trabajar con JPA.
import jakarta.persistence.*;

/**
 * Entidad UsuarioInteres.
 *
 * Representa la relación entre usuarios e intereses
 * dentro de Master Martini.
 *
 * Se relaciona directamente con la tabla
 * "usuario_intereses" de MySQL.
 *
 * Esta tabla utiliza una clave primaria compuesta
 * formada por id_usuario e id_interes.
 */
@Entity
@Table(name = "usuario_intereses")
public class UsuarioInteres {

    // Identificador compuesto de la relación.
    // Contiene el ID del usuario y el ID del interés.
    @EmbeddedId
    private UsuarioInteresId id;

    // Relación con la entidad Usuario.
    // Cada registro de esta tabla pertenece a un usuario.
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Relación con la entidad Interes.
    // Cada registro de esta tabla corresponde a un interés.
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idInteres")
    @JoinColumn(name = "id_interes", nullable = false)
    private Interes interes;

    // Constructor vacío requerido por JPA.
    public UsuarioInteres() {
    }

    // Constructor para crear una relación entre usuario e interés.
    public UsuarioInteres(Usuario usuario, Interes interes) {
        this.usuario = usuario;
        this.interes = interes;

        // Construimos la clave primaria compuesta.
        this.id = new UsuarioInteresId(
                usuario.getIdUsuario(),
                interes.getIdInteres()
        );
    }

    // GETTERS Y SETTERS

    public UsuarioInteresId getId() {
        return id;
    }

    public void setId(UsuarioInteresId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Interes getInteres() {
        return interes;
    }

    public void setInteres(Interes interes) {
        this.interes = interes;
    }
}
