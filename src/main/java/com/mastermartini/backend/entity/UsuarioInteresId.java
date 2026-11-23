
package com.mastermartini.backend.entity;

// Importaciones de Jakarta Persistence.
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// Importaciones para comparar identificadores.
import java.io.Serializable;
import java.util.Objects;

/**
 * Identificador compuesto de UsuarioInteres.
 *
 * La tabla usuario_intereses utiliza dos columnas
 * para formar su clave primaria:
 *
 * - id_usuario
 * - id_interes
 *
 * Esta clase permite representar ambas columnas
 * como un único identificador mediante JPA.
 */
@Embeddable
public class UsuarioInteresId implements Serializable {

    // Identificador del usuario.
    @Column(name = "id_usuario")
    private Long idUsuario;

    // Identificador del interés.
    @Column(name = "id_interes")
    private Long idInteres;

    // Constructor vacío requerido por JPA.
    public UsuarioInteresId() {
    }

    // Constructor para asignar ambos identificadores.
    public UsuarioInteresId(Long idUsuario, Long idInteres) {
        this.idUsuario = idUsuario;
        this.idInteres = idInteres;
    }

    // GETTERS Y SETTERS

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(Long idInteres) {
        this.idInteres = idInteres;
    }

    // Compara dos identificadores compuestos.
    // Ambos campos deben coincidir para considerarlos iguales.
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        UsuarioInteresId that = (UsuarioInteresId) objeto;

        return Objects.equals(idUsuario, that.idUsuario)
                && Objects.equals(idInteres, that.idInteres);
    }

    // Genera un código hash utilizando ambos identificadores.
    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, idInteres);
    }
}
