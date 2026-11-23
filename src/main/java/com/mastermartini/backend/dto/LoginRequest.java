
package com.mastermartini.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO que recibe las credenciales para iniciar sesión.
public class LoginRequest {

    // Correo electrónico registrado.
    @NotBlank
    @Email
    private String correo;

    // Contraseña ingresada por el usuario.
    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
