
package com.mastermartini.backend.service;

// Entidad de intereses.
import com.mastermartini.backend.entity.Interes;

// Repositorio para consultar los intereses en MySQL.
import com.mastermartini.backend.repository.InteresRepository;

// Dependencias de Spring.
import org.springframework.stereotype.Service;

// Herramienta para trabajar con listas.
import java.util.List;

/**
 * Servicio encargado de administrar los intereses.
 *
 * Permite obtener los intereses disponibles
 * para que el usuario pueda seleccionarlos.
 */
@Service
public class InteresService {

    // Repositorio encargado de acceder a la tabla intereses.
    private final InteresRepository interesRepository;

    /**
     * Constructor con inyección de dependencias.
     */
    public InteresService(InteresRepository interesRepository) {

        this.interesRepository = interesRepository;
    }

    /**
     * Obtiene todos los intereses registrados en MySQL.
     */
    public List<Interes> listarIntereses() {

        return interesRepository.findAll();
    }

    /**
     * Busca un interés por su nombre.
     */
    public Interes buscarPorNombre(String nombre) {

        Interes interes = interesRepository.findByNombre(nombre);

        if (interes == null) {
            throw new IllegalArgumentException(
                    "No se encontró el interés solicitado."
            );
        }

        return interes;
    }
}
