package com.mastermartini.backend.repository;

// Entidad que representa la tabla intereses.
import com.mastermartini.backend.entity.Interes;

// Herramientas de Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Interes.
 *
 * Permite consultar y administrar los intereses
 * disponibles en Master Martini.
 */
@Repository
public interface InteresRepository extends JpaRepository<Interes, Long> {

    // Busca un interés por su nombre.
    Interes findByNombre(String nombre);

    // Comprueba si un interés ya existe.
    boolean existsByNombre(String nombre);
}
