
package com.mastermartini.backend.controller;

import com.mastermartini.backend.dto.FavoritoRequest;
import com.mastermartini.backend.dto.FavoritoResponse;
import com.mastermartini.backend.entity.Favorito;
import com.mastermartini.backend.service.FavoritoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "*")
public class FavoritoController {

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    // Agregar contenido a favoritos
    @PostMapping("/{idUsuario}")
    public ResponseEntity<FavoritoResponse> agregarFavorito(
            @PathVariable Long idUsuario,
            @Valid @RequestBody FavoritoRequest request
    ) {

        Favorito favorito = favoritoService.agregarFavorito(
                idUsuario,
                request.getIdContenido()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new FavoritoResponse(favorito));
    }

    // Listar favoritos de un usuario
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<FavoritoResponse>> listarFavoritos(
            @PathVariable Long idUsuario
    ) {

        List<FavoritoResponse> respuesta = favoritoService
                .listarFavoritos(idUsuario)
                .stream()
                .map(FavoritoResponse::new)
                .toList();

        return ResponseEntity.ok(respuesta);
    }

    // Eliminar un favorito
    @DeleteMapping("/{idUsuario}/{idContenido}")
    public ResponseEntity<Void> eliminarFavorito(
            @PathVariable Long idUsuario,
            @PathVariable Integer idContenido
    ) {

        favoritoService.eliminarFavorito(idUsuario, idContenido);

        return ResponseEntity.noContent().build();
    }
}
