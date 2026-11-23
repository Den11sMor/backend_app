
package com.mastermartini.backend.entity;

// Importaciones de Jakarta Persistence para trabajar con JPA.
import jakarta.persistence.*;

/**
 * Entidad Interes.
 *
 * Representa los intereses disponibles dentro de Master Martini.
 * Se relaciona directamente con la tabla "intereses" de MySQL.
 * Ejemplos:
 * - Cocina
 * - Recetas
 * - Coctelería
 * - Gastronomía
 */
@Entity
@Table(name = "intereses")
public class Interes {

    // Identificador único del interés.
    // MySQL genera este valor automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interes")
    private Long idInteres;

    // Nombre del interés.
    // No puede estar vacío y debe ser único.
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    // Descripción opcional del interés.
    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // Constructor vacío requerido por JPA.
    public Interes() {
    }

    // Constructor para crear un interés.
    public Interes(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // GETTERS Y SETTERS

    public Long getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(Long idInteres) {
        this.idInteres = idInteres;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
