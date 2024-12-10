package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Reservacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservacionController {

    private final ReservacionService reservacionService;

    public ReservacionController(ReservacionService reservacionService) {
        this.reservacionService = reservacionService;
    }

    // Registrar una nueva reservación
    @PostMapping("/registrar")
    public ResponseEntity<Reservacion> registrarReservacion(@RequestBody Reservacion reservacion) {
        return ResponseEntity.ok(reservacionService.registrarReservacion(reservacion));
    }

    // Consultar todas las reservaciones
    @GetMapping("/consultar")
    public ResponseEntity<List<Reservacion>> consultarReservaciones() {
        return ResponseEntity.ok(reservacionService.consultarReservaciones());
    }

    // Consultar reservaciones activas
    @GetMapping("/activas")
    public ResponseEntity<List<Reservacion>> consultarReservacionesActivas() {
        return ResponseEntity.ok(reservacionService.consultarReservacionesActivas());
    }

    // Actualizar una reservación
    @PutMapping("/{id}")
    public ResponseEntity<Reservacion> actualizarReservacion(@PathVariable Long id, @RequestBody Reservacion reservacion) {
        return reservacionService.actualizarReservacion(id, reservacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cambiar el estado de una reservación
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Reservacion> cambiarEstadoReservacion(@PathVariable Long id, @RequestParam Boolean estado) {
        return reservacionService.cambiarEstadoReservacion(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
