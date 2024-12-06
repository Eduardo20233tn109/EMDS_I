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

    public Optional<CategoriaServicio> actualizarCategoria(Long id, CategoriaServicio categoriaActualizada) {
        return categoriaServicioRepository.findById(id).map(categoriaExistente -> {
            categoriaExistente.setNombre(categoriaActualizada.getNombre());
            categoriaExistente.setDescripcion(categoriaActualizada.getDescripcion());
            categoriaExistente.setStatus(categoriaActualizada.getStatus());
            return categoriaServicioRepository.save(categoriaExistente);
        });
    }


    // Cambiar el estado de una categoría
    public Optional<CategoriaServicio> cambiarEstadoCategoria(Long id, Boolean nuevoEstado) {
        return categoriaServicioRepository.findById(id).map(categoria -> {
            categoria.setStatus(nuevoEstado);
            return categoriaServicioRepository.save(categoria);
        });
    }
    public Optional<CategoriaServicio> obtenerCategoriaPorId(Long id) {
        return categoriaServicioRepository.findById(id); // Busca en la base de datos por ID
    }


}
