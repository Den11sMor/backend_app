
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
    private void verificarUsuario(Long idUsuario) {
        var autenticacion = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        var esperado = usuarioService.buscarPorCorreo(autenticacion.getName()).getIdUsuario();
        if(!esperado.equals(idUsuario)) throw new org.springframework.security.access.AccessDeniedException("No puedes acceder a los favoritos de otro usuario");
    }
    private final com.mastermartini.backend.service.UsuarioService usuarioService;

    private final FavoritoService favoritoService;

    public FavoritoController(FavoritoService favoritoService, com.mastermartini.backend.service.UsuarioService usuarioService) {
        this.favoritoService = favoritoService;
        this.usuarioService = usuarioService;
    }

    // Agregar contenido a favoritos
    @PostMapping("/{idUsuario}")
    public ResponseEntity<FavoritoResponse> agregarFavorito(
            @PathVariable Long idUsuario,
            @Valid @RequestBody FavoritoRequest request
    ) {

        verificarUsuario(idUsuario);
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

        verificarUsuario(idUsuario);
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

        verificarUsuario(idUsuario);
        favoritoService.eliminarFavorito(idUsuario, idContenido);

        return ResponseEntity.noContent().build();
    }
}
