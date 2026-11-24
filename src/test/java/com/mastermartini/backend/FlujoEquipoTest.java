package com.mastermartini.backend;

import com.mastermartini.backend.entity.Interes;
import com.mastermartini.backend.repository.*;
import com.mastermartini.backend.service.InteresService;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Recorre los módulos de ambos integrantes usando JWT real y las relaciones de la base de pruebas.
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test")
class FlujoEquipoTest {
 @Autowired MockMvc mvc; @Autowired ObjectMapper json;
 @Autowired UsuarioRepository usuarios; @Autowired InteresRepository intereses;
 @Autowired UsuarioInteresRepository relaciones; @Autowired FavoritoRepository favoritos;
 @Autowired ContenidoRepository contenidos;
 @Autowired com.mastermartini.backend.cursos.CursoRepository cursos;
 @Autowired com.mastermartini.backend.recetas.RecetaRepository recetas;
 @Autowired com.mastermartini.backend.videos.VideoRepository videos;
 @Autowired InteresService interesService;
 @Autowired com.mastermartini.backend.service.UsuarioService usuarioService;
 @Autowired org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
 @BeforeEach void preparar() {
  favoritos.deleteAll();relaciones.deleteAll();cursos.deleteAll();recetas.deleteAll();videos.deleteAll();
  contenidos.deleteAll();usuarios.deleteAll();intereses.deleteAll();
  intereses.save(new Interes("Cocina","Técnicas e ingredientes"));
 }
 private String datos(String correo,String interes) throws Exception {
  return json.writeValueAsString(Map.of("nombre","Luis Prueba","correo",correo,"telefono","912345678","password","Martini2026","intereses",java.util.List.of(interes)));
 }
 private long registrar(String correo) throws Exception {
  var r=mvc.perform(post("/api/usuarios/registro").contentType(MediaType.APPLICATION_JSON).content(datos(correo,"Cocina")))
   .andExpect(status().isOk()).andExpect(jsonPath("$.passwordHash").doesNotExist()).andReturn();
  return json.readTree(r.getResponse().getContentAsString()).get("idUsuario").asLong();
 }
 private String token(String correo) throws Exception {
  var r=mvc.perform(post("/api/usuarios/login").contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(Map.of("correo",correo,"password","Martini2026"))))
   .andExpect(status().isOk()).andExpect(jsonPath("$.tipo").value("Bearer")).andReturn();
  String token=json.readTree(r.getResponse().getContentAsString()).get("token").asText();assertEquals(3,token.split("\\.").length);return token;
 }
 @Test void registroLoginCatalogoYFavoritosCompartenDatos() throws Exception {
  mvc.perform(get("/api/intereses")).andExpect(status().isOk()).andExpect(jsonPath("$[0].nombre").value("Cocina"));
  long usuario=registrar("luis@test.cl");String jwt=token("luis@test.cl");
  assertEquals(1,relaciones.count());assertNotEquals("Martini2026",usuarios.findById(usuario).orElseThrow().getPasswordHash());
  var datos=Map.of("titulo","Curso JWT","categoria","Principiante","duracion","2 horas","descripcion","Curso de prueba","detalle","Unidad 1: medidas");
  var r=mvc.perform(post("/api/cursos").header("Authorization","Bearer "+jwt).contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(datos)))
   .andExpect(status().isCreated()).andReturn();
  int id=json.readTree(r.getResponse().getContentAsString()).get("id").asInt();
  mvc.perform(get("/api/contenidos/"+id).header("Authorization","Bearer "+jwt)).andExpect(status().isOk()).andExpect(jsonPath("$.tipo").value("Curso"));
  for(String ruta:java.util.List.of("/api/contenidos","/api/contenidos/filtrar?tipo=Curso","/api/contenidos/filtrar?categoria=Principiante","/api/contenidos/filtrar?tipo=Curso&categoria=Principiante","/api/contenidos/filtrar","/api/contenidos/buscar?titulo=JWT"))
   mvc.perform(get(ruta).header("Authorization","Bearer "+jwt)).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id));
  mvc.perform(post("/api/favoritos/"+usuario).header("Authorization","Bearer "+jwt).contentType(MediaType.APPLICATION_JSON).content("{\"idContenido\":"+id+"}"))
   .andExpect(status().isCreated());
  mvc.perform(get("/api/favoritos/"+usuario).header("Authorization","Bearer "+jwt)).andExpect(status().isOk()).andExpect(jsonPath("$[0].idContenido").value(id));
  mvc.perform(post("/api/favoritos/"+usuario).header("Authorization","Bearer "+jwt).contentType(MediaType.APPLICATION_JSON).content("{\"idContenido\":"+id+"}"))
   .andExpect(status().isBadRequest());
  mvc.perform(delete("/api/favoritos/"+usuario+"/"+id).header("Authorization","Bearer "+jwt)).andExpect(status().isNoContent());
  mvc.perform(delete("/api/favoritos/"+usuario+"/"+id).header("Authorization","Bearer "+jwt)).andExpect(status().isBadRequest());
  mvc.perform(post("/api/favoritos/"+usuario).header("Authorization","Bearer "+jwt).contentType(MediaType.APPLICATION_JSON).content("{\"idContenido\":"+id+"}"))
   .andExpect(status().isCreated());
  mvc.perform(delete("/api/cursos/"+id).header("Authorization","Bearer "+jwt)).andExpect(status().isNoContent());
  assertFalse(contenidos.existsById(id));assertEquals(0,favoritos.count());
 }
 @Test void rechazaDuplicadosDatosInvalidosYCredencialesIncorrectas() throws Exception {
  registrar("luis@test.cl");
  mvc.perform(post("/api/usuarios/registro").contentType(MediaType.APPLICATION_JSON).content(datos("luis@test.cl","Cocina"))).andExpect(status().isConflict());
  mvc.perform(post("/api/usuarios/registro").contentType(MediaType.APPLICATION_JSON).content(datos("invalido","Cocina"))).andExpect(status().isBadRequest());
  for(String correo:java.util.List.of("luis@test.cl","noexiste@test.cl"))
   mvc.perform(post("/api/usuarios/login").contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsString(Map.of("correo",correo,"password","incorrecta")))).andExpect(status().isUnauthorized());
  var usuario=usuarios.findByCorreo("luis@test.cl").orElseThrow();usuario.setActivo(false);usuarios.save(usuario);
  mvc.perform(post("/api/usuarios/login").contentType(MediaType.APPLICATION_JSON).content("{\"correo\":\"luis@test.cl\",\"password\":\"Martini2026\"}")).andExpect(status().isUnauthorized());
 }
 @Test void interesesInexistentesRevierteElRegistroYElTokenEsObligatorio() throws Exception {
  mvc.perform(post("/api/usuarios/registro").contentType(MediaType.APPLICATION_JSON).content(datos("fallo@test.cl","No existe"))).andExpect(status().isBadRequest());
  assertTrue(usuarios.findByCorreo("fallo@test.cl").isEmpty());
  mvc.perform(get("/api/cursos")).andExpect(status().isUnauthorized());
  mvc.perform(get("/api/recetas").header("Authorization","Bearer invalido")).andExpect(status().isUnauthorized());
  assertEquals("Cocina",interesService.buscarPorNombre("Cocina").getNombre());
  assertThrows(IllegalArgumentException.class,()->interesService.buscarPorNombre("No existe"));
 }
 @Test void otroUsuarioNoPuedeLeerNiModificarFavoritosAjenos() throws Exception {
  long luis=registrar("luis@test.cl");registrar("otro@test.cl");String jwt=token("otro@test.cl");
  mvc.perform(get("/api/favoritos/"+luis).header("Authorization","Bearer "+jwt)).andExpect(status().isForbidden());
  mvc.perform(post("/api/favoritos/"+luis).header("Authorization","Bearer "+jwt).contentType(MediaType.APPLICATION_JSON).content("{\"idContenido\":1}")).andExpect(status().isForbidden());
  mvc.perform(delete("/api/favoritos/"+luis+"/1").header("Authorization","Bearer "+jwt)).andExpect(status().isForbidden());
 }
 @Test void usuariosYContenidosPreviosSiguenDisponibles() throws Exception {
  // Comprueba que la integración no rompe las entidades y el catálogo general del integrante 1.
  var previo=new com.mastermartini.backend.entity.Usuario("Usuario Previo","previo@test.cl","912345678",passwordEncoder.encode("Martini2026"));
  usuarios.saveAndFlush(previo);String jwt=token("previo@test.cl");
  assertNotNull(usuarios.findByCorreo("previo@test.cl").orElseThrow().getFechaRegistro());
  mvc.perform(get("/api/usuarios").header("Authorization","Bearer "+jwt)).andExpect(status().isOk())
   .andExpect(jsonPath("$[0].correo").value("previo@test.cl")).andExpect(jsonPath("$[0].passwordHash").doesNotExist());
  var general=contenidos.saveAndFlush(new com.mastermartini.backend.entity.Contenido(null,"Guía general","Contenido","General","10 min","Lectura de apoyo","Guía del equipo"));
  mvc.perform(get("/api/contenidos/"+general.getId()).header("Authorization","Bearer "+jwt)).andExpect(status().isOk())
   .andExpect(jsonPath("$.tipo").value("Contenido")).andExpect(jsonPath("$.titulo").value("Guía general"));
  assertThrows(IllegalArgumentException.class,()->usuarioService.registrarUsuario("Prueba"," ","912345678","Martini2026",null,null));
  assertThrows(IllegalArgumentException.class,()->usuarioService.buscarPorCorreo("ausente@test.cl"));
 }
}
