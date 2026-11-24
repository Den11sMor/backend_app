package com.mastermartini.backend.service;

import com.mastermartini.backend.entity.Contenido;
import com.mastermartini.backend.entity.Favorito;
import com.mastermartini.backend.entity.Usuario;
import com.mastermartini.backend.repository.ContenidoRepository;
import com.mastermartini.backend.repository.FavoritoRepository;
import com.mastermartini.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoritoServiceTest {
    @Mock FavoritoRepository favoritoRepository;
    @Mock UsuarioRepository usuarioRepository;
    @Mock ContenidoRepository contenidoRepository;
    FavoritoService service;

    @BeforeEach void setUp() { service = new FavoritoService(favoritoRepository, usuarioRepository, contenidoRepository); }

    @Test void agregarFavoritoExistente() {
        Usuario u = new Usuario(); u.setIdUsuario(1L);
        Contenido c = new Contenido(); c.setId(2);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        when(contenidoRepository.findById(2)).thenReturn(Optional.of(c));
        when(favoritoRepository.existsByUsuario_IdUsuarioAndContenido_Id(1L, 2)).thenReturn(false);
        Favorito favorito = new Favorito(u, c);
        when(favoritoRepository.save(any(Favorito.class))).thenReturn(favorito);

        assertSame(favorito, service.agregarFavorito(1L, 2));
    }

    @Test void agregarFavoritoUsuarioInexistente() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.agregarFavorito(1L, 2));
    }

    @Test void agregarFavoritoContenidoInexistente() {
        Usuario u = new Usuario(); u.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        when(contenidoRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.agregarFavorito(1L, 2));
    }

    @Test void rechazaFavoritoDuplicado() {
        Usuario u = new Usuario(); u.setIdUsuario(1L);
        Contenido c = new Contenido(); c.setId(2);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));
        when(contenidoRepository.findById(2)).thenReturn(Optional.of(c));
        when(favoritoRepository.existsByUsuario_IdUsuarioAndContenido_Id(1L, 2)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> service.agregarFavorito(1L, 2));
        verify(favoritoRepository, never()).save(any());
    }

    @Test void listarFavoritos() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(favoritoRepository.findByUsuario_IdUsuario(1L)).thenReturn(List.of(new Favorito()));
        assertEquals(1, service.listarFavoritos(1L).size());
    }

    @Test void listarFavoritosUsuarioInexistente() {
        when(usuarioRepository.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.listarFavoritos(1L));
    }

    @Test void eliminarFavoritoExistente() {
        when(favoritoRepository.existsByUsuario_IdUsuarioAndContenido_Id(1L, 2)).thenReturn(true);
        service.eliminarFavorito(1L, 2);
        verify(favoritoRepository).deleteByUsuario_IdUsuarioAndContenido_Id(1L, 2);
    }

    @Test void eliminarFavoritoInexistente() {
        when(favoritoRepository.existsByUsuario_IdUsuarioAndContenido_Id(1L, 2)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.eliminarFavorito(1L, 2));
    }
}
