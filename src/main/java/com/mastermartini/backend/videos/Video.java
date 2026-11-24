package com.mastermartini.backend.videos;
import jakarta.persistence.*;
import com.mastermartini.backend.entity.Contenido;
// Hereda los datos comunes del catálogo del equipo. Su tabla guarda la relación con el contenido.
@Entity @Table(name="videos") @DiscriminatorValue("Video")
public class Video extends Contenido {
 public Video() { setTipo("Video"); }
}
