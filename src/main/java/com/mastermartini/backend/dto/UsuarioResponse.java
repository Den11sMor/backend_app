package com.mastermartini.backend.dto;

// DTO que representa la información pública de un usuario.
public class UsuarioResponse {

    // Identificador del usuario.
    private Long idUsuario;

    // Nombre del usuario.
    private String nombre;

    // Correo electrónico.
    private String correo;

    // Teléfono de contacto.
    private String telefono;

    // Fotografía del usuario.
    private String foto;

    public UsuarioResponse() {
    }

    public UsuarioResponse(
            Long idUsuario,
            String nombre,
            String correo,
            String telefono,
            String foto) {

        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.foto = foto;
    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
