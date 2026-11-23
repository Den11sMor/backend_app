
package com.mastermartini.backend.service;

import com.mastermartini.backend.entity.Contenido;
import com.mastermartini.backend.entity.Favorito;
import com.mastermartini.backend.entity.Usuario;
import com.mastermartini.backend.repository.ContenidoRepository;
import com.mastermartini.backend.repository.FavoritoRepository;
import com.mastermartini.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoritoService {

    private final FavoritoRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ContenidoRepository contenidoRepository;

    public FavoritoService(
            FavoritoRepository favoritoRepository,
            UsuarioRepository usuarioRepository,
            ContenidoRepository contenidoRepository
    ) {
        this.favoritoRepository = favoritoRepository;
        this.usuarioRepository = usuarioRepository;
        this.contenidoRepository = contenidoRepository;
    }

    @Transactional
    public Favorito agregarFavorito(Long idUsuario, Integer idContenido) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new IllegalArgumentException("No se encontró el usuario."));

        Contenido contenido = contenidoRepository.findById(idContenido)
                .orElseThrow(() ->
                        new IllegalArgumentException("No se encontró el contenido."));

        boolean existe = favoritoRepository
                .existsByUsuario_IdUsuarioAndContenido_Id(idUsuario, idContenido);

        if (existe) {
            throw new IllegalArgumentException(
                    "Este contenido ya está guardado en favoritos."
            );
        }

        Favorito favorito = new Favorito(usuario, contenido);

        return favoritoRepository.save(favorito);
    }

    @Transactional(readOnly = true)
    public List<Favorito> listarFavoritos(Long idUsuario) {

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new IllegalArgumentException("No se encontró el usuario.");
        }

        return favoritoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Transactional
    public void eliminarFavorito(Long idUsuario, Integer idContenido) {

        boolean existe = favoritoRepository
                .existsByUsuario_IdUsuarioAndContenido_Id(idUsuario, idContenido);

        if (!existe) {
            throw new IllegalArgumentException(
                    "El contenido no está guardado en favoritos."
            );
        }

        favoritoRepository.deleteByUsuario_IdUsuarioAndContenido_Id(
                idUsuario,
                idContenido
        );
    }
}
