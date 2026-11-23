
package com.mastermartini.backend.service;

// Entidades del sistema
import com.mastermartini.backend.entity.Usuario;
import com.mastermartini.backend.entity.Interes;
import com.mastermartini.backend.entity.UsuarioInteres;

// Repositorios
import com.mastermartini.backend.repository.UsuarioRepository;
import com.mastermartini.backend.repository.InteresRepository;
import com.mastermartini.backend.repository.UsuarioInteresRepository;

// Spring y seguridad
import com.mastermartini.backend.exception.CorreoDuplicadoException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Herramientas Java
import java.util.List;
import java.util.ArrayList;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final InteresRepository interesRepository;
    private final UsuarioInteresRepository usuarioInteresRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // Constructor con inyección de dependencias
    public UsuarioService(
            UsuarioRepository usuarioRepository,
            InteresRepository interesRepository,
            UsuarioInteresRepository usuarioInteresRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.usuarioRepository = usuarioRepository;
        this.interesRepository = interesRepository;
        this.usuarioInteresRepository = usuarioInteresRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Registra un nuevo usuario
    @Transactional
    public Usuario registrarUsuario(
            String nombre,
            String correo,
            String telefono,
            String password,
            String foto,
            List<String> nombresIntereses) {

        // Normaliza el correo (sin espacios adicionales y en minúsculas)
        String correoNormalizado = correo != null ? correo.trim().toLowerCase() : "";

        // Verifica que el correo no esté registrado
        if (correoNormalizado.isBlank()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }

        if (usuarioRepository.findCorreoNormalizado(correoNormalizado).isPresent()) {
            throw new CorreoDuplicadoException(
                    "El correo electrónico ya está registrado."
            );
        }

        // Cifra la contraseña con BCrypt
        String passwordHash = passwordEncoder.encode(password);

        // Crea el usuario
        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setCorreo(correoNormalizado);
        usuario.setTelefono(telefono);
        usuario.setPasswordHash(passwordHash);
        usuario.setFoto(foto);
        usuario.setActivo(true);

        // Guarda el usuario en MySQL
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Prepara las relaciones con los intereses
        List<UsuarioInteres> relaciones = new ArrayList<>();

        if (nombresIntereses != null) {

            for (String nombreInteres : nombresIntereses) {

                // Busca el interés seleccionado
                Interes interes = interesRepository.findByNombre(nombreInteres);

                if (interes == null) {
                    throw new IllegalArgumentException(
                            "El interés no existe: " + nombreInteres
                    );
                }

                // Crea la relación usuario-interés
                UsuarioInteres relacion =
                        new UsuarioInteres(usuarioGuardado, interes);

                relaciones.add(relacion);
            }
        }

        // Guarda las relaciones
        usuarioInteresRepository.saveAll(relaciones);

        return usuarioGuardado;
    }

    // Busca un usuario por correo
    public Usuario buscarPorCorreo(String correo) {
        String correoNormalizado = correo != null ? correo.trim().toLowerCase() : "";

        return usuarioRepository.findByCorreo(correoNormalizado)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró un usuario con ese correo."
                ));
    }

    // Obtiene todos los usuarios
    public List<Usuario> listarUsuarios() {

        return usuarioRepository.findAll();
    }

    // Valida las credenciales y genera el token JWT
    public String iniciarSesion(String correo, String password) {
        String correoNormalizado = correo != null ? correo.trim().toLowerCase() : "";

        // Busca al usuario
        Usuario usuario = usuarioRepository.findByCorreo(correoNormalizado)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Correo o contraseña incorrectos."
                ));

        // Verifica que la cuenta esté activa
        if (usuario.getActivo() == null || !usuario.getActivo()) {
            throw new IllegalArgumentException(
                    "La cuenta de usuario está desactivada."
            );
        }

        // Comprueba la contraseña
        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new IllegalArgumentException(
                    "Correo o contraseña incorrectos."
            );
        }

        // Genera el token JWT
        return jwtService.generarToken(usuario.getCorreo());
    }
}
