package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.utils.CategoriaServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiciosService {

    private final CategoriaServicioRepository categoriaServicioRepository;

    public CategoriaServiciosService(CategoriaServicioRepository categoriaServicioRepository) {
        this.categoriaServicioRepository = categoriaServicioRepository;
    }

    // Registrar una nueva categoría con validación
    public CategoriaServicio registrarCategoria(CategoriaServicio categoria) {
        validarCategoria(categoria); // Valida los campos
        if (categoriaServicioRepository.existsByNombre(categoria.getNombre())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre.");
        }
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

    // Actualizar una categoría con validación
    public CategoriaServicio actualizarCategoria(Long id, CategoriaServicio categoriaActualizada) {
        validarCategoria(categoriaActualizada); // Valida los campos
        CategoriaServicio categoriaExistente = categoriaServicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + id + " no existe."));
        categoriaExistente.setNombre(categoriaActualizada.getNombre());
        categoriaExistente.setDescripcion(categoriaActualizada.getDescripcion());
        categoriaExistente.setStatus(categoriaActualizada.getStatus());
        return categoriaServicioRepository.save(categoriaExistente);
    }

    // Cambiar el estado de una categoría
    public CategoriaServicio cambiarEstadoCategoria(Long id, Boolean nuevoEstado) {
        CategoriaServicio categoria = categoriaServicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + id + " no existe."));
        categoria.setStatus(nuevoEstado);
        return categoriaServicioRepository.save(categoria);
    }

    // Obtener una categoría por ID
    public CategoriaServicio obtenerCategoriaPorId(Long id) {
        return categoriaServicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con ID " + id + " no existe."));
    }

    // Validación de campos de la categoría
    private void validarCategoria(CategoriaServicio categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        }
        if (categoria.getNombre().length() > 50) {
            throw new IllegalArgumentException("El nombre no puede superar los 50 caracteres.");
        }
        if (categoria.getDescripcion() != null && categoria.getDescripcion().length() > 200) {
            throw new IllegalArgumentException("La descripción no puede superar los 200 caracteres.");
        }
    }
}
