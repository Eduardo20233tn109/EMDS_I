package com.example.SGR.sgr.servicios.serviceS;

import com.example.SGR.sgr.categoriasServicios.modelCS.CategoriaServicio;
import com.example.SGR.sgr.categoriasServicios.utilsCS.CategoriaServicioRepository;
import com.example.SGR.sgr.servicios.modelS.Servicio;
import com.example.SGR.sgr.servicios.utilsS.ServicioRepository;
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

    // Registrar un nuevo servicio
    public Servicio registrarServicio(Servicio servicio) {
        if (servicio.getCategoria() != null && servicio.getCategoria().getId() != null) {
            // Buscar la categoría en la base de datos
            CategoriaServicio categoria = categoriaServicioRepository.findById(servicio.getCategoria().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + servicio.getCategoria().getId()));
            // Asignar la categoría encontrada al servicio
            servicio.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("La categoría es requerida para registrar un servicio.");
        }

        // Guardar el servicio en la base de datos
        return servicioRepository.save(servicio);
    }



    // Consultar todos los servicios
    public List<Servicio> consultarServicios() {
        return servicioRepository.findAll();
    }

    // Consultar servicios activos
    public List<Servicio> consultarServiciosActivos() {
        return servicioRepository.findByStatus(true);
    }

    // Actualizar un servicio
    public Optional<Servicio> actualizarServicio(Long id, Servicio servicioActualizado) {
        return servicioRepository.findById(id).map(servicio -> {
            servicio.setNombre(servicioActualizado.getNombre());
            servicio.setDescripcion(servicioActualizado.getDescripcion());
            servicio.setCategoria(servicioActualizado.getCategoria());
            return servicioRepository.save(servicio);
        });
    }

    // Cambiar el estado de un servicio
    public Optional<Servicio> cambiarEstadoServicio(Long id, Boolean nuevoEstado) {
        return servicioRepository.findById(id).map(servicio -> {
            servicio.setStatus(nuevoEstado);
            return servicioRepository.save(servicio);
        });
    }
}
