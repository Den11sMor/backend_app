
package com.mastermartini.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

// DTO que recibe los datos para registrar un usuario.
public class RegistroUsuarioRequest {

    // Nombre del usuario.
    @NotBlank
    private String nombre;

    // Correo electrónico del usuario.
    @NotBlank
    @Email
    private String correo;

    // Número de teléfono.
    @NotBlank
    private String telefono;

    // Contraseña que posteriormente se cifrará con BCrypt.
    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    // URL o dirección de la fotografía del usuario.
    private String foto;

    // Lista de intereses seleccionados durante el registro.
    private List<String> intereses;

    public RegistroUsuarioRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<String> getIntereses() {
        return intereses;
    }

    public void setIntereses(List<String> intereses) {
        this.intereses = intereses;
    }
}
