package com.mastermartini.backend.service;

import com.mastermartini.backend.entity.Interes;
import com.mastermartini.backend.entity.Usuario;
import com.mastermartini.backend.exception.CorreoDuplicadoException;
import com.mastermartini.backend.repository.InteresRepository;
import com.mastermartini.backend.repository.UsuarioInteresRepository;
import com.mastermartini.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {
    @Mock UsuarioRepository usuarioRepository;
    @Mock InteresRepository interesRepository;
    @Mock UsuarioInteresRepository usuarioInteresRepository;
    @Mock PasswordEncoder passwordEncoder;
    @Mock JwtService jwtService;

    UsuarioService service;

    @BeforeEach
    void setUp() {
        service = new UsuarioService(usuarioRepository, interesRepository, usuarioInteresRepository, passwordEncoder, jwtService);
    }

    @Test void registraUsuarioNormalizaCorreoYGuardaIntereses() {
        when(usuarioRepository.findCorreoNormalizado("ana@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secreto")).thenReturn("hash");
        Usuario guardado = new Usuario("Ana", "ana@test.com", "123", "hash");
        guardado.setIdUsuario(7L);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);
        Interes interes = new Interes("Recetas", "Cocina");
        interes.setIdInteres(3L);
        when(interesRepository.findByNombre("Recetas")).thenReturn(interes);

        Usuario resultado = service.registrarUsuario("Ana", "  ANA@TEST.COM ", "123", "secreto", null, List.of("Recetas"));

        assertSame(guardado, resultado);
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioCaptor.capture());
        assertEquals("ana@test.com", usuarioCaptor.getValue().getCorreo());
        assertTrue(usuarioCaptor.getValue().getActivo());
        verify(usuarioInteresRepository).saveAll(argThat(relaciones -> {
            int count = 0;
            for (Object ignored : relaciones) count++;
            return count == 1;
        }));
    }

    @Test void rechazaCorreoVacio() {
        assertThrows(IllegalArgumentException.class, () -> service.registrarUsuario("Ana", "  ", "123", "secreto", null, null));
        verifyNoInteractions(passwordEncoder, usuarioRepository);
    }

    @Test void rechazaCorreoDuplicado() {
        when(usuarioRepository.findCorreoNormalizado("ana@test.com")).thenReturn(Optional.of(new Usuario()));
        assertThrows(CorreoDuplicadoException.class, () -> service.registrarUsuario("Ana", "ANA@TEST.COM", "123", "secreto", null, null));
    }

    @Test void rechazaInteresInexistente() {
        when(usuarioRepository.findCorreoNormalizado("ana@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("secreto")).thenReturn("hash");
        Usuario guardado = new Usuario("Ana", "ana@test.com", "123", "hash");
        guardado.setIdUsuario(7L);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);
        when(interesRepository.findByNombre("X")).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.registrarUsuario("Ana", "ana@test.com", "123", "secreto", null, List.of("X")));
        assertTrue(ex.getMessage().contains("El interés no existe"));
    }

    @Test void iniciarSesionRetornaToken() {
        Usuario u = new Usuario("Ana", "ana@test.com", "123", "hash");
        u.setActivo(true);
        when(usuarioRepository.findByCorreo("ana@test.com")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("secreto", "hash")).thenReturn(true);
        when(jwtService.generarToken("ana@test.com")).thenReturn("token");
        assertEquals("token", service.iniciarSesion(" ANA@TEST.COM ", "secreto"));
    }

    @Test void iniciarSesionRechazaUsuarioInexistente() {
        when(usuarioRepository.findByCorreo("ana@test.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.iniciarSesion("ana@test.com", "secreto"));
    }

    @Test void iniciarSesionRechazaCuentaDesactivada() {
        Usuario u = new Usuario(); u.setActivo(false); u.setPasswordHash("hash");
        when(usuarioRepository.findByCorreo("ana@test.com")).thenReturn(Optional.of(u));
        assertThrows(IllegalArgumentException.class, () -> service.iniciarSesion("ana@test.com", "secreto"));
    }

    @Test void iniciarSesionRechazaPasswordIncorrecto() {
        Usuario u = new Usuario(); u.setActivo(true); u.setPasswordHash("hash");
        when(usuarioRepository.findByCorreo("ana@test.com")).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("mala", "hash")).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.iniciarSesion("ana@test.com", "mala"));
    }

    @Test void buscarPorCorreoYListarUsuarios() {
        Usuario u = new Usuario(); u.setCorreo("ana@test.com");
        when(usuarioRepository.findByCorreo("ana@test.com")).thenReturn(Optional.of(u));
        when(usuarioRepository.findAll()).thenReturn(List.of(u));
        assertSame(u, service.buscarPorCorreo(" ANA@TEST.COM "));
        assertEquals(1, service.listarUsuarios().size());
    }

    @Test void buscarPorCorreoInexistenteLanzaError() {
        when(usuarioRepository.findByCorreo("nadie@test.com")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorCorreo("nadie@test.com"));
    }
}
