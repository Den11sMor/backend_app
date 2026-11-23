package com.mastermartini.backend.repository;

import com.mastermartini.backend.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

    @Query("""
            SELECT u
            FROM Usuario u
            WHERE LOWER(TRIM(u.correo)) = LOWER(TRIM(:correo))
            """)
    Optional<Usuario> findCorreoNormalizado(@Param("correo") String correo);
}