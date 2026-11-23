package com.mastermartini.backend.controller;

// Entidad y DTO
import com.mastermartini.backend.entity.Usuario;
import com.mastermartini.backend.dto.RegistroUsuarioRequest;
import com.mastermartini.backend.dto.LoginRequest;
import com.mastermartini.backend.dto.UsuarioResponse;
import com.mastermartini.backend.dto.LoginResponse;

// Servicio de usuarios
import com.mastermartini.backend.service.UsuarioService;

// Dependencias de Spring
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Validación de datos
import jakarta.validation.Valid;

// Listas
import java.util.List;

// Controlador REST para administrar usuarios
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    // Servicio encargado de la lógica de usuarios
    private final UsuarioService usuarioService;

    // Constructor con inyección de dependencias
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registra un usuario nuevo
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrarUsuario(
            @Valid @RequestBody RegistroUsuarioRequest request) {

        Usuario usuario = usuarioService.registrarUsuario(
                request.getNombre(),
                request.getCorreo(),
                request.getTelefono(),
                request.getPassword(),
                request.getFoto(),
                request.getIntereses()
        );

        // Convertimos la entidad en una respuesta segura
        UsuarioResponse respuesta = convertirRespuesta(usuario);

        return ResponseEntity.ok(respuesta);
    }

    // Permite iniciar sesión y obtener un token JWT
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> iniciarSesion(
            @Valid @RequestBody LoginRequest request) {

        // Validamos las credenciales y generamos el token
        String token = usuarioService.iniciarSesion(
                request.getCorreo(),
                request.getPassword()
        );

        // Buscamos los datos públicos del usuario
        Usuario usuario = usuarioService.buscarPorCorreo(
                request.getCorreo()
        );

        // Convertimos los datos del usuario en un DTO
        UsuarioResponse usuarioResponse = convertirRespuesta(usuario);

        // Construimos la respuesta con el token y el perfil
        LoginResponse respuesta = new LoginResponse(
                token,
                "Bearer",
                usuarioResponse
        );

        return ResponseEntity.ok(respuesta);
    }

    // Obtiene todos los usuarios registrados
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {

        List<UsuarioResponse> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(this::convertirRespuesta)
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    // Convierte una entidad Usuario en un DTO
    private UsuarioResponse convertirRespuesta(Usuario usuario) {

        return new UsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getFoto()
        );
    }
}
