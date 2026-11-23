
package com.mastermartini.backend.repository;

import com.mastermartini.backend.entity.Favorito;
import com.mastermartini.backend.entity.FavoritoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, FavoritoId> {

    List<Favorito> findByUsuario_IdUsuario(Long idUsuario);

    boolean existsByUsuario_IdUsuarioAndContenido_Id(
            Long idUsuario,
            Integer idContenido
    );

    void deleteByUsuario_IdUsuarioAndContenido_Id(
            Long idUsuario,
            Integer idContenido
    );
}
