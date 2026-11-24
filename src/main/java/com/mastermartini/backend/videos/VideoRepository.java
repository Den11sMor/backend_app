package com.mastermartini.backend.videos;

import org.springframework.data.jpa.repository.JpaRepository;

// Spring Data implementa guardar, buscar, listar y eliminar. No hace falta escribir SQL para este
// CRUD.
public interface VideoRepository extends JpaRepository<Video, Integer> {}
