
package com.mastermartini.backend.service;

import com.mastermartini.backend.entity.Contenido;
import com.mastermartini.backend.repository.ContenidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContenidoService {

    private final ContenidoRepository contenidoRepository;

    public ContenidoService(ContenidoRepository contenidoRepository) {
        this.contenidoRepository = contenidoRepository;
    }

    public List<Contenido> listarContenidos() {
        return contenidoRepository.findAll();
    }

    public Contenido buscarPorId(Integer id) {
        return contenidoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("No se encontró el contenido."));
    }

    public List<Contenido> buscarPorTipo(String tipo) {
        return contenidoRepository.findByTipoIgnoreCase(tipo);
    }

    public List<Contenido> buscarPorCategoria(String categoria) {
        return contenidoRepository.findByCategoriaIgnoreCase(categoria);
    }

    public List<Contenido> buscarPorTitulo(String titulo) {
        return contenidoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Contenido> filtrar(String tipo, String categoria) {
        if (tipo != null && !tipo.isBlank()
                && categoria != null && !categoria.isBlank()) {
            return contenidoRepository
                    .findByTipoIgnoreCaseAndCategoriaIgnoreCase(tipo, categoria);
        }

        if (tipo != null && !tipo.isBlank()) {
            return buscarPorTipo(tipo);
        }

        if (categoria != null && !categoria.isBlank()) {
            return buscarPorCategoria(categoria);
        }

        return listarContenidos();
    }
}
