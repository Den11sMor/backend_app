package com.mastermartini.backend.dto;

// Respuesta que se entrega cuando el usuario inicia sesión.
public class LoginResponse {

    // Token de autenticación JWT.
    private String token;

    // Tipo de autenticación.
    private String tipo;

    // Información pública del usuario.
    private UsuarioResponse usuario;

    public LoginResponse() {
    }

    public LoginResponse(
            String token,
            String tipo,
            UsuarioResponse usuario) {

        this.token = token;
        this.tipo = tipo;
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioResponse getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponse usuario) {
        this.usuario = usuario;
    }
}
