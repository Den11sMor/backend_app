
package com.mastermartini.backend.controller;

import com.mastermartini.backend.dto.ContenidoResponse;
import com.mastermartini.backend.entity.Contenido;
import com.mastermartini.backend.service.ContenidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
@CrossOrigin(origins = "*")
public class ContenidoController {

    private final ContenidoService contenidoService;

    public ContenidoController(ContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }

    // Listar todos los contenidos
    @GetMapping
    public ResponseEntity<List<ContenidoResponse>> listarContenidos() {

        List<ContenidoResponse> respuesta = contenidoService
                .listarContenidos()
                .stream()
                .map(ContenidoResponse::new)
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    // Buscar contenido por ID
    @GetMapping("/{id}")
    public ResponseEntity<ContenidoResponse> buscarPorId(
            @PathVariable Integer id
    ) {
        Contenido contenido = contenidoService.buscarPorId(id);

        return ResponseEntity.ok(new ContenidoResponse(contenido));
    }

    // Filtrar por tipo y categoría
    @GetMapping("/filtrar")
    public ResponseEntity<List<ContenidoResponse>> filtrar(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String categoria
    ) {

        List<ContenidoResponse> respuesta = contenidoService
                .filtrar(tipo, categoria)
                .stream()
                .map(ContenidoResponse::new)
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    // Buscar por título
    @GetMapping("/buscar")
    public ResponseEntity<List<ContenidoResponse>> buscarPorTitulo(
            @RequestParam String titulo
    ) {

        List<ContenidoResponse> respuesta = contenidoService
                .buscarPorTitulo(titulo)
                .stream()
                .map(ContenidoResponse::new)
                .toList();

        return ResponseEntity.ok(respuesta);
    }
}
