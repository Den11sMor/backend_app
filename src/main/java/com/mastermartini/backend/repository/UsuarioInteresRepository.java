package com.mastermartini.backend.repository;

// Entidad que representa la relación entre usuario e interés.
import com.mastermartini.backend.entity.UsuarioInteres;

// Identificador compuesto de la relación.
import com.mastermartini.backend.entity.UsuarioInteresId;

// Herramientas de Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de UsuarioInteres.
 *
 * Administra la tabla intermedia usuario_intereses.
 *
 * Su clave primaria está formada por:
 * - id_usuario
 * - id_interes
 */
@Repository
public interface UsuarioInteresRepository
        extends JpaRepository<UsuarioInteres, UsuarioInteresId> {

}
