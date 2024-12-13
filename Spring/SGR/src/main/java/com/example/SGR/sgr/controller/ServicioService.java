package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.model.Servicio;
import com.example.SGR.sgr.utils.CategoriaServicioRepository;
import com.example.SGR.sgr.utils.ServicioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final CategoriaServicioRepository categoriaServicioRepository;

    public ServicioService(ServicioRepository servicioRepository, CategoriaServicioRepository categoriaServicioRepository) {
        this.servicioRepository = servicioRepository;
        this.categoriaServicioRepository = categoriaServicioRepository;
    }

    // Método adicional en ServicioService para devolver directamente la lista de servicios activos
    public List<Servicio> obtenerServiciosActivos() {
        return servicioRepository.findByStatus(true);
    }

    // Consultar un servicio por ID
    public ResponseEntity<?> consultarServicioPorId(Long id) {
        Optional<Servicio> servicio = servicioRepository.findById(id);

        if (!servicio.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado con ID: " + id);
        }

        return ResponseEntity.ok(servicio.get());
    }

    // Registrar un nuevo servicio
    public ResponseEntity<?> registrarServicio(Servicio servicio) {
        // Validar que el nombre del servicio no esté duplicado
        if (servicioRepository.existsByNombre(servicio.getNombre())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un servicio con el nombre '" + servicio.getNombre() + "'.");
        }

        // Validar que se asocie una categoría existente
        if (servicio.getCategoria() == null || servicio.getCategoria().getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La categoría es requerida para registrar un servicio.");
        }

        // Validar que la categoría exista
        Optional<CategoriaServicio> categoria = categoriaServicioRepository.findById(servicio.getCategoria().getId());
        if (!categoria.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría no encontrada con ID: " + servicio.getCategoria().getId());
        }

        servicio.setCategoria(categoria.get());
        Servicio nuevoServicio = servicioRepository.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoServicio);
    }

    // Consultar todos los servicios
    public ResponseEntity<?> consultarServicios() {
        List<Servicio> servicios = servicioRepository.findAll();
        return ResponseEntity.ok(servicios);
    }

    // Consultar servicios activos
    public ResponseEntity<?> consultarServiciosActivos() {
        List<Servicio> serviciosActivos = servicioRepository.findByStatus(true);
        return ResponseEntity.ok(serviciosActivos);
    }

    // Actualizar un servicio
    public ResponseEntity<?> actualizarServicio(Long id, Servicio servicioActualizado) {
        Optional<Servicio> servicioExistente = servicioRepository.findById(id);

        if (!servicioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado con ID: " + id);
        }

        Servicio servicio = servicioExistente.get();
        servicio.setNombre(servicioActualizado.getNombre());
        servicio.setDescripcion(servicioActualizado.getDescripcion());

        // Validar la categoría actualizada
        if (servicioActualizado.getCategoria() != null && servicioActualizado.getCategoria().getId() != null) {
            Optional<CategoriaServicio> categoria = categoriaServicioRepository.findById(servicioActualizado.getCategoria().getId());
            if (!categoria.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Categoría no encontrada con ID: " + servicioActualizado.getCategoria().getId());
            }
            servicio.setCategoria(categoria.get());
        }

        servicio.setStatus(servicioActualizado.getStatus());
        Servicio servicioActualizadoFinal = servicioRepository.save(servicio);

        return ResponseEntity.ok(servicioActualizadoFinal);
    }

    // Cambiar el estado de un servicio
    public ResponseEntity<?> cambiarEstadoServicio(Long id, Boolean nuevoEstado) {
        Optional<Servicio> servicioExistente = servicioRepository.findById(id);

        if (!servicioExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Servicio no encontrado con ID: " + id);
        }

        Servicio servicio = servicioExistente.get();
        servicio.setStatus(nuevoEstado);
        servicioRepository.save(servicio);

        return ResponseEntity.ok("Estado del servicio actualizado a " + (nuevoEstado ? "Activo" : "Inactivo"));
    }
}
