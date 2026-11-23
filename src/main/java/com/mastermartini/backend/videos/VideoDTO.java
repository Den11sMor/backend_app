package com.mastermartini.backend.videos;

import jakarta.validation.constraints.*;

// El DTO define el JSON de entrada y salida. Sus anotaciones validan antes de ejecutar el servicio.
public record VideoDTO(
    Long id,
    @NotBlank(message = "Campo obligatorio") @Size(max = 160) String titulo,
    @NotBlank(message = "Campo obligatorio") @Size(max = 100) String categoria,
    @NotBlank(message = "Campo obligatorio") @Size(max = 80) String duracion,
    @NotBlank(message = "Campo obligatorio") @Size(max = 1000) String descripcion,
    @NotBlank(message = "Campo obligatorio") @Size(max = 16000) String detalle,
    @NotBlank(message = "Ingresa el enlace del video")
        @Size(max = 2000)
        @Pattern(regexp = "^$|https://[^\\s]+", message = "Usa una dirección HTTPS válida")
        String videoUrl) {
  public static VideoDTO desde(Video item) {
    return new VideoDTO(
        item.getId().longValue(),
        item.getTitulo(),
        item.getCategoria(),
        item.getDuracion(),
        item.getDescripcion(),
        item.getDetalle(),
        item.getVideoUrl());
  }

  // Copia los campos editables; nunca cambia el ID de la entidad con el ID recibido en el JSON.
  public void aplicar(Video item) {
    item.setTitulo(titulo == null ? "" : titulo.trim());
    item.setCategoria(categoria == null ? "" : categoria.trim());
    item.setDuracion(duracion == null ? "" : duracion.trim());
    item.setDescripcion(descripcion == null ? "" : descripcion.trim());
    item.setDetalle(detalle == null ? "" : detalle.trim());
    item.setVideoUrl(videoUrl == null ? "" : videoUrl.trim());
  }
}
