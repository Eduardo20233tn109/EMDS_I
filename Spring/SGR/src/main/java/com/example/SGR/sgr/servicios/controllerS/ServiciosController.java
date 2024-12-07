package com.example.SGR.sgr.servicios.controllerS;

import com.example.SGR.sgr.categoriasServicios.modelCS.CategoriaServicio;
import com.example.SGR.sgr.categoriasServicios.utilsCS.CategoriaServicioRepository;
import com.example.SGR.sgr.servicios.modelS.Servicio;
import com.example.SGR.sgr.servicios.serviceS.ServicioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//DENEGAR REGISTROS IGUALES
@RestController
@RequestMapping("/api/servicios")
public class ServiciosController {

    private final ServicioService servicioService;
    private final CategoriaServicioRepository categoriaServicioRepository;

    public ServiciosController(ServicioService servicioService, CategoriaServicioRepository categoriaServicioRepository) {
        this.servicioService = servicioService;
        this.categoriaServicioRepository = categoriaServicioRepository;
    }

    // Registrar un nuevo servicio
    @PostMapping("/registrar")
    public ResponseEntity<Servicio> registrarServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.registrarServicio(servicio);
        return ResponseEntity.ok(nuevoServicio);
    }


    // Consultar todos los servicios
    @GetMapping("/consultar")
    public ResponseEntity<List<Servicio>> consultarServicios() {
        return ResponseEntity.ok(servicioService.consultarServicios());
    }

    // Consultar servicios activos
    @GetMapping("/activos")
    public ResponseEntity<List<Servicio>> consultarServiciosActivos() {
        return ResponseEntity.ok(servicioService.consultarServiciosActivos());
    }

    // Actualizar un servicio
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarServicio(@PathVariable Long id, @RequestBody Servicio servicio) {
        return servicioService.actualizarServicio(id, servicio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cambiar el estado de un servicio
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Servicio> cambiarEstadoServicio(@PathVariable Long id, @RequestParam Boolean estado) {
        return servicioService.cambiarEstadoServicio(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
