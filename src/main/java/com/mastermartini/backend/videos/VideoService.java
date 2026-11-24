package com.mastermartini.backend.videos;

import com.mastermartini.backend.shared.NoEncontradoException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// El servicio reúne las operaciones del módulo. @Transactional mantiene cada escritura en una
// transacción.
@Service
@Transactional
public class VideoService {
  private final VideoRepository repository;
  private final com.mastermartini.backend.repository.FavoritoRepository favoritos;

  public VideoService(VideoRepository repository, com.mastermartini.backend.repository.FavoritoRepository favoritos) {
    this.repository = repository;
    this.favoritos = favoritos;
  }

  @Transactional(readOnly = true)
  public List<VideoDTO> listar() {
    return repository.findAll(org.springframework.data.domain.Sort.by("id")).stream()
        .map(VideoDTO::desde)
        .toList();
  }

  @Transactional(readOnly = true)
  public VideoDTO consultar(Long id) {
    return VideoDTO.desde(buscar(id));
  }

  // Centraliza el caso de ID inexistente: consultar, editar y eliminar devuelven el mismo 404.
  private Integer idLocal(Long id) {
    if(id == null || id < 1 || id > Integer.MAX_VALUE) throw new NoEncontradoException("Video no encontrado");
    return id.intValue();
  }

  private Video buscar(Long id) {
    return repository
        .findById(idLocal(id))
        .orElseThrow(() -> new NoEncontradoException("Video no encontrado"));
  }

  public VideoDTO crear(VideoDTO datos) {
    var item = new Video();
    datos.aplicar(item);
    return VideoDTO.desde(repository.save(item));
  }

  public VideoDTO actualizar(Long id, VideoDTO datos) {
    var item = buscar(id);
    datos.aplicar(item);
    return VideoDTO.desde(repository.save(item));
  }

  public void eliminar(Long id) {
    var item = buscar(id);
    favoritos.deleteByContenido_Id(item.getId());
    repository.delete(item);
  }
}
