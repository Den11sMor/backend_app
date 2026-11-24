package com.mastermartini.backend.service;

import com.mastermartini.backend.entity.Interes;
import com.mastermartini.backend.repository.InteresRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InteresServiceTest {
    @Mock InteresRepository repository;
    InteresService service;

    @BeforeEach void setUp() { service = new InteresService(repository); }

    @Test void listarIntereses() {
        when(repository.findAll()).thenReturn(List.of(new Interes()));
        assertEquals(1, service.listarIntereses().size());
    }

    @Test void buscarPorNombreExistente() {
        Interes interes = new Interes("Recetas", "Cocina");
        when(repository.findByNombre("Recetas")).thenReturn(interes);
        assertSame(interes, service.buscarPorNombre("Recetas"));
    }

    @Test void buscarPorNombreInexistente() {
        when(repository.findByNombre("Otro")).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> service.buscarPorNombre("Otro"));
    }
}
