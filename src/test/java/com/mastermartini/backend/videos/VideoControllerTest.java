package com.mastermartini.backend.videos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import tools.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@org.springframework.security.test.context.support.WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VideoControllerTest {
  @Autowired MockMvc mvc;
  @Autowired ObjectMapper json;
  @Autowired VideoRepository repository;
  private final String ruta = "/api/videos";

  private String datos(String titulo) throws Exception {
    return json.writeValueAsString(
        Map.of(
            "titulo",
            titulo,
            "categoria",
            "Pastelería",
            "duracion",
            "30 minutos",
            "descripcion",
            "Contenido de prueba",
            "detalle",
            "Preparación paso a paso",
            "videoUrl",
            "https://example.com/video.mp4"));
  }

  @BeforeEach
  void limpiar() {
    repository.deleteAll();
  }

  @Test
  void cincoOperacionesCrudPersisten() throws Exception {
    mvc.perform(get(ruta))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));
    var respuesta =
        mvc.perform(
                post(ruta)

                    .contentType(MediaType.APPLICATION_JSON)
                    .content(datos("Nuevo contenido")))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andReturn();
    long id = json.readTree(respuesta.getResponse().getContentAsString()).get("id").asLong();
    mvc.perform(get(ruta + "/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Nuevo contenido"));
    mvc.perform(get(ruta))
        .andExpect(jsonPath("$[0].id").value(id));
    mvc.perform(
            put(ruta + "/" + id)

                .contentType(MediaType.APPLICATION_JSON)
                .content(datos("Editado")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.titulo").value("Editado"));
    Assertions.assertEquals("Editado", repository.findById((int)id).orElseThrow().getTitulo());
    mvc.perform(delete(ruta + "/" + id))
        .andExpect(status().isNoContent());
    Assertions.assertFalse(repository.existsById((int)id));
  }

  @Test
  void identificadoresInexistentesDevuelven404() throws Exception {
    mvc.perform(get(ruta + "/999999"))
        .andExpect(status().isNotFound());
    mvc.perform(
            put(ruta + "/999999")

                .contentType(MediaType.APPLICATION_JSON)
                .content(datos("Titulo")))
        .andExpect(status().isNotFound());
    mvc.perform(delete(ruta + "/999999"))
        .andExpect(status().isNotFound());
  }

  @Test
  void validaObligatoriosLongitudYVideoHttps() throws Exception {
    mvc.perform(
            post(ruta)

                .contentType(MediaType.APPLICATION_JSON)
                .content(datos(" ")))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errores.titulo").exists());
    mvc.perform(
            post(ruta)

                .contentType(MediaType.APPLICATION_JSON)
                .content(datos("a".repeat(161))))
        .andExpect(status().isBadRequest());
    mvc.perform(
            post(ruta)

                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    datos("Titulo")
                        .replace("https://example.com/video.mp4", "javascript:alert(1)")))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errores.videoUrl").exists());
    Assertions.assertEquals(0, repository.count());
  }

  @Test
  void rechazaJsonYTokenIncorrectos() throws Exception {
    mvc.perform(
            post(ruta)

                .contentType(MediaType.APPLICATION_JSON)
                .content("{mal"))
        .andExpect(status().isBadRequest());
    mvc.perform(get(ruta).with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous())).andExpect(status().isUnauthorized());
    mvc.perform(get(ruta).header("Authorization", "Bearer token-invalido")).andExpect(status().isUnauthorized());
  }
}
