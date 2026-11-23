package com.mastermartini.backend.recetas;
import jakarta.persistence.*;
import com.mastermartini.backend.entity.Contenido;
// Hereda los datos comunes del catálogo del equipo. Su tabla guarda la relación con el contenido.
@Entity @Table(name="recetas") @DiscriminatorValue("Receta")
public class Receta extends Contenido {
 public Receta() { setTipo("Receta"); }
}
