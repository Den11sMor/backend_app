package com.mastermartini.backend.config;
import com.mastermartini.backend.entity.Interes;
import com.mastermartini.backend.repository.InteresRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
// Son las opciones del formulario Android. No crea usuarios ni contraseñas de demostración.
@Component
public class InteresesIniciales implements CommandLineRunner {
 private final InteresRepository repository;
 public InteresesIniciales(InteresRepository repository) { this.repository=repository; }
 @Override public void run(String... args) {
  for(String nombre:java.util.List.of("Cocina","Recetas","Coctelería","Gastronomía")) {
   if(repository.findByNombre(nombre)==null) repository.save(new Interes(nombre,"Contenidos de "+nombre));
  }
 }
}
