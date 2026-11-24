
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
    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<?> credenciales(CredencialesInvalidasException e) {
        return ResponseEntity.status(401).body(java.util.Map.of("mensaje", "Correo o contraseña incorrectos"));
    }

    @ExceptionHandler(com.mastermartini.backend.shared.NoEncontradoException.class)
    public ResponseEntity<?> noEncontrado(com.mastermartini.backend.shared.NoEncontradoException e) {
        return ResponseEntity.status(404).body(java.util.Map.of("mensaje", e.getMessage()));
    }
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<?> jsonInvalido() {
        return ResponseEntity.badRequest().body(java.util.Map.of("mensaje", "El cuerpo JSON no es válido"));
    }


    // Maneja los errores de validación de los DTO.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> manejarValidaciones(MethodArgumentNotValidException exception) {
        java.util.Map<String,String> errores = new java.util.LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(e -> errores.put(e.getField(),e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(java.util.Map.of("mensaje","Revisa los campos","errores",errores));
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
                "No se pudieron guardar los datos por un conflicto de integridad."
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

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<?> prohibido() { return ResponseEntity.status(403).body(java.util.Map.of("mensaje","Acceso no permitido")); }

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
