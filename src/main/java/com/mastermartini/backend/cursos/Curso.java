package com.mastermartini.backend.cursos;
import jakarta.persistence.*;
import com.mastermartini.backend.entity.Contenido;
// Hereda los datos comunes del catálogo del equipo. Su tabla guarda la relación con el contenido.
@Entity @Table(name="cursos") @DiscriminatorValue("Curso")
public class Curso extends Contenido {
 public Curso() { setTipo("Curso"); }
}
