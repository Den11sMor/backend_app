package com.mastermartini.backend.recetas;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data implementa guardar, buscar, listar y eliminar. No hace falta escribir SQL para este
// CRUD.
public interface RecetaRepository extends JpaRepository<Receta, Integer> {}
