package com.example.SGR.sgr.categoriasServicios.serviceCS;

import com.example.SGR.sgr.categoriasServicios.modelCS.CategoriaServicio;
import com.example.SGR.sgr.categoriasServicios.utilsCS.CategoriaServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiciosService {

    private final CategoriaServicioRepository categoriaServicioRepository;

    public CategoriaServiciosService(CategoriaServicioRepository categoriaServicioRepository) {
        this.categoriaServicioRepository = categoriaServicioRepository;
    }

    // Registrar una nueva categoría
    public CategoriaServicio registrarCategoria(CategoriaServicio categoria) {
        return categoriaServicioRepository.save(categoria);
    }

    // Consultar todas las categorías
    public List<CategoriaServicio> consultarCategorias() {
        return categoriaServicioRepository.findAll();
    }

    // Consultar categorías activas
    public List<CategoriaServicio> consultarCategoriasActivas() {
        return categoriaServicioRepository.findByStatus(true);
    }

    // Actualizar una categoría
    public Optional<CategoriaServicio> actualizarCategoria(Long id, CategoriaServicio categoriaActualizada) {
        return categoriaServicioRepository.findById(id).map(categoria -> {
            categoria.setNombre(categoriaActualizada.getNombre());
            categoria.setDescripcion(categoriaActualizada.getDescripcion());
            return categoriaServicioRepository.save(categoria);
        });
    }

    // Cambiar el estado de una categoría
    public Optional<CategoriaServicio> cambiarEstadoCategoria(Long id, Boolean nuevoEstado) {
        return categoriaServicioRepository.findById(id).map(categoria -> {
            categoria.setStatus(nuevoEstado);
            return categoriaServicioRepository.save(categoria);
        });
    }
}
