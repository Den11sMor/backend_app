
package com.mastermartini.backend.controller;

// Entidad y servicio.
import com.mastermartini.backend.entity.Interes;
import com.mastermartini.backend.service.InteresService;

// Dependencias de Spring.
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Listas.
import java.util.List;

// Controlador REST para consultar intereses.
@RestController
@RequestMapping("/api/intereses")
public class InteresController {

    // Servicio encargado de administrar los intereses.
    private final InteresService interesService;

    // Constructor con inyección de dependencias.
    public InteresController(InteresService interesService) {
        this.interesService = interesService;
    }

    // Obtiene todos los intereses disponibles.
    @GetMapping
    public ResponseEntity<List<Interes>> listarIntereses() {

        List<Interes> intereses = interesService.listarIntereses();

        return ResponseEntity.ok(intereses);
    }
}
