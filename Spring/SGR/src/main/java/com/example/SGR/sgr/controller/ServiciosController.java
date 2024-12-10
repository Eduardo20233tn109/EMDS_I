package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Servicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServiciosController {

    private final ServicioService servicioService;

    public ServiciosController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // Registrar un nuevo servicio
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarServicio(@RequestBody Servicio servicio) {
        return servicioService.registrarServicio(servicio);
    }

    // Consultar todos los servicios
    @GetMapping("/consultar")
    public ResponseEntity<?> consultarServicios() {
        return servicioService.consultarServicios();
    }

    // Consultar servicios activos
    @GetMapping("/activos")
    public ResponseEntity<?> consultarServiciosActivos() {
        return servicioService.consultarServiciosActivos();
    }

    // Actualizar un servicio
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(@PathVariable Long id, @RequestBody Servicio servicio) {
        return servicioService.actualizarServicio(id, servicio);
    }

    // Cambiar el estado de un servicio
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoServicio(@PathVariable Long id, @RequestParam Boolean estado) {
        return servicioService.cambiarEstadoServicio(id, estado);
    }
}
