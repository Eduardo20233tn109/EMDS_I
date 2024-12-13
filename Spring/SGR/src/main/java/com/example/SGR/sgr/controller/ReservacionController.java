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

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarReservacion(@RequestBody Reservacion reservacion) {
        return reservacionService.registrarReservacion(reservacion);
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<Reservacion>> consultarReservaciones() {
        return reservacionService.consultarReservaciones();
    }

    @GetMapping("/activas")
    public ResponseEntity<List<Reservacion>> consultarReservacionesActivas() {
        return reservacionService.consultarReservacionesActivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservacionPorId(@PathVariable Long id) {
        return reservacionService.obtenerReservacionPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarReservacion(@PathVariable Long id, @RequestBody Reservacion reservacion) {
        return reservacionService.actualizarReservacion(id, reservacion);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoReservacion(@PathVariable Long id, @RequestParam Boolean estado) {
        return reservacionService.cambiarEstadoReservacion(id, estado);
    }
}
