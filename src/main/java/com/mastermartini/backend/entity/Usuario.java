
package com.mastermartini.backend.entity;

// Importaciones de Jakarta Persistence para trabajar con JPA.
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entidad Usuario.
 *
 * Representa a un usuario registrado en Master Martini.
 * Esta clase se relaciona directamente con la tabla
 * "usuarios" de nuestra base de datos MySQL.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    // Identificador único del usuario.
    // MySQL genera este valor automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    // Nombre completo del usuario.
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    // Correo electrónico.
    // Debe ser único y no puede estar vacío.
    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String correo;

    // Número telefónico del usuario.
    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    // Contraseña almacenada como hash BCrypt.
    // Nunca debemos guardar la contraseña original.
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    // Ruta o URL de la fotografía de perfil.
    @Column(name = "foto", length = 500)
    private String foto;

    // Fecha de registro.
    // MySQL asigna automáticamente la fecha de creación.
    @Column(name = "fecha_registro", insertable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    // Indica si la cuenta está activa.
    @Column(name = "activo")
    private Boolean activo = true;

    // Constructor vacío requerido por JPA.
    public Usuario() {
    }

    // Constructor para crear usuarios sin asignar el ID.
    public Usuario(String nombre, String correo,
                   String telefono, String passwordHash) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.passwordHash = passwordHash;
        this.activo = true;
    }

    // GETTERS Y SETTERS

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
