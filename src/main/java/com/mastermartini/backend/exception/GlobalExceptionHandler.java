
package com.mastermartini.backend.exception;

// Respuesta HTTP.
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

// Manejo global de excepciones.
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Errores de validación.
import org.springframework.web.bind.MethodArgumentNotValidException;

// Clase encargada de manejar los errores de toda la API.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja los errores de validación de los DTO.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(
            MethodArgumentNotValidException exception) {

        // Obtiene el primer mensaje de validación.
        String mensaje = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Los datos enviados no son válidos.");

        ErrorResponse respuesta = new ErrorResponse(
                400,
                mensaje
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    // Un correo duplicado es un conflicto, no un error interno.
    @ExceptionHandler(CorreoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> manejarCorreoDuplicado(
            CorreoDuplicadoException exception) {

        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuesta);
    }

    // Maneja carreras concurrentes o restricciones únicas de MySQL.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manejarIntegridad(
            DataIntegrityViolationException exception) {

        ErrorResponse respuesta = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "El correo electrónico ya está registrado."
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(respuesta);
    }

    // Maneja los errores de datos incorrectos o inexistentes.
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> manejarDatosIncorrectos(
            IllegalArgumentException exception) {

        ErrorResponse respuesta = new ErrorResponse(
                400,
                exception.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }

    // Maneja errores inesperados del servidor.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(
            Exception exception) {

        ErrorResponse respuesta = new ErrorResponse(
                500,
                "Ocurrió un error interno en el servidor."
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(respuesta);
    }
}
