
package com.mastermartini.backend.exception;

// Estructura de las respuestas de error del backend.
public class ErrorResponse {

    // Código HTTP del error.
    private int status;

    // Mensaje que explica el problema.
    private String mensaje;

    // Constructor.
    public ErrorResponse(int status, String mensaje) {
        this.status = status;
        this.mensaje = mensaje;
    }

    // Obtiene el código de estado.
    public int getStatus() {
        return status;
    }

    // Modifica el código de estado.
    public void setStatus(int status) {
        this.status = status;
    }

    // Obtiene el mensaje del error.
    public String getMensaje() {
        return mensaje;
    }

    // Modifica el mensaje del error.
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
