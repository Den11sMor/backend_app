package com.mastermartini.backend.recetas;

import com.mastermartini.backend.shared.NoEncontradoException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// El servicio reúne las operaciones del módulo. @Transactional mantiene cada escritura en una
// transacción.
@Service
@Transactional
public class RecetaService {
  private final RecetaRepository repository;
  private final com.mastermartini.backend.repository.FavoritoRepository favoritos;

  public RecetaService(RecetaRepository repository, com.mastermartini.backend.repository.FavoritoRepository favoritos) {
    this.repository = repository;
    this.favoritos = favoritos;
  }

  @Transactional(readOnly = true)
  public List<RecetaDTO> listar() {
    return repository.findAll(org.springframework.data.domain.Sort.by("id")).stream()
        .map(RecetaDTO::desde)
        .toList();
  }

  @Transactional(readOnly = true)
  public RecetaDTO consultar(Long id) {
    return RecetaDTO.desde(buscar(id));
  }

  // Centraliza el caso de ID inexistente: consultar, editar y eliminar devuelven el mismo 404.
  private Integer idLocal(Long id) {
    if(id == null || id < 1 || id > Integer.MAX_VALUE) throw new NoEncontradoException("Receta no encontrado");
    return id.intValue();
  }

  private Receta buscar(Long id) {
    return repository
        .findById(idLocal(id))
        .orElseThrow(() -> new NoEncontradoException("Receta no encontrado"));
  }

  public RecetaDTO crear(RecetaDTO datos) {
    var item = new Receta();
    datos.aplicar(item);
    return RecetaDTO.desde(repository.save(item));
  }

  public RecetaDTO actualizar(Long id, RecetaDTO datos) {
    var item = buscar(id);
    datos.aplicar(item);
    return RecetaDTO.desde(repository.save(item));
  }

  public void eliminar(Long id) {
    var item = buscar(id);
    favoritos.deleteByContenido_Id(item.getId());
    repository.delete(item);
  }
}
