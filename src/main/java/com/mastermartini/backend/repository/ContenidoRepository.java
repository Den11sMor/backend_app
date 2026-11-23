
package com.mastermartini.backend.repository;

import com.mastermartini.backend.entity.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Integer> {

    List<Contenido> findByTipoIgnoreCase(String tipo);

    List<Contenido> findByCategoriaIgnoreCase(String categoria);

    List<Contenido> findByTituloContainingIgnoreCase(String titulo);

    List<Contenido> findByTipoIgnoreCaseAndCategoriaIgnoreCase(
            String tipo,
            String categoria
    );
}
