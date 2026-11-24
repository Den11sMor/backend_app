package com.mastermartini.backend.service;

import com.mastermartini.backend.entity.Contenido;
import com.mastermartini.backend.repository.ContenidoRepository;
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
class ContenidoServiceTest {
    @Mock ContenidoRepository repository;
    ContenidoService service;

    @BeforeEach void setUp() { service = new ContenidoService(repository); }

    @Test void listarContenidos() {
        when(repository.findAll()).thenReturn(List.of(new Contenido()));
        assertEquals(1, service.listarContenidos().size());
    }

    @Test void buscarPorIdExistente() {
        Contenido c = new Contenido(); c.setId(1);
        when(repository.findById(1)).thenReturn(Optional.of(c));
        assertSame(c, service.buscarPorId(1));
    }

    @Test void buscarPorIdInexistente() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorId(99));
    }

    @Test void buscarPorTipo() {
        service.buscarPorTipo("receta");
        verify(repository).findByTipoIgnoreCase("receta");
    }

    @Test void buscarPorCategoria() {
        service.buscarPorCategoria("cocteles");
        verify(repository).findByCategoriaIgnoreCase("cocteles");
    }

    @Test void buscarPorTitulo() {
        service.buscarPorTitulo("martini");
        verify(repository).findByTituloContainingIgnoreCase("martini");
    }

    @Test void filtrarPorTipoYCategoria() {
        service.filtrar("video", "cocteles");
        verify(repository).findByTipoIgnoreCaseAndCategoriaIgnoreCase("video", "cocteles");
    }

    @Test void filtrarSoloTipo() {
        service.filtrar("video", " ");
        verify(repository).findByTipoIgnoreCase("video");
    }

    @Test void filtrarSoloCategoria() {
        service.filtrar(null, "cocteles");
        verify(repository).findByCategoriaIgnoreCase("cocteles");
    }

    @Test void filtrarSinCriterios() {
        service.filtrar(null, null);
        verify(repository).findAll();
    }
}
