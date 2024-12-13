package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Reservacion;
import com.example.SGR.sgr.utils.ReservacionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservacionService {

    private final ReservacionRepository reservacionRepository;

    public ReservacionService(ReservacionRepository reservacionRepository) {
        this.reservacionRepository = reservacionRepository;
    }

    public ResponseEntity<?> registrarReservacion(Reservacion reservacion) {
        try {
            validarReservacion(reservacion);
            Reservacion nuevaReservacion = reservacionRepository.save(reservacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReservacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<List<Reservacion>> consultarReservaciones() {
        return ResponseEntity.ok(reservacionRepository.findAll());
    }

    public ResponseEntity<List<Reservacion>> consultarReservacionesActivas() {
        return ResponseEntity.ok(reservacionRepository.findByStatus(true));
    }

    public ResponseEntity<?> obtenerReservacionPorId(Long id) {
        Optional<Reservacion> reservacion = reservacionRepository.findById(id);
        if (reservacion.isPresent()) {
            return ResponseEntity.ok(reservacion.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservación no encontrada con ID: " + id);
        }
    }

    public ResponseEntity<?> actualizarReservacion(Long id, Reservacion reservacionActualizada) {
        try {
            validarReservacion(reservacionActualizada);
            Optional<Reservacion> reservacionExistente = reservacionRepository.findById(id);

            if (!reservacionExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservación no encontrada con ID: " + id);
            }

            Reservacion reservacion = reservacionExistente.get();
            reservacion.setNombre(reservacionActualizada.getNombre());
            reservacion.setDescripcion(reservacionActualizada.getDescripcion());
            reservacion.setServicio(reservacionActualizada.getServicio());
            reservacion.setStatus(reservacionActualizada.getStatus());
            reservacionRepository.save(reservacion);

            return ResponseEntity.ok(reservacion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> cambiarEstadoReservacion(Long id, Boolean nuevoEstado) {
        Optional<Reservacion> reservacionExistente = reservacionRepository.findById(id);

        if (!reservacionExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservación no encontrada con ID: " + id);
        }

        Reservacion reservacion = reservacionExistente.get();
        reservacion.setStatus(nuevoEstado);
        reservacionRepository.save(reservacion);

        return ResponseEntity.ok("Estado de la reservación actualizado a " + (nuevoEstado ? "Activo" : "Inactivo"));
    }

    // Validar los campos de una reservación
    private void validarReservacion(Reservacion reservacion) {
        if (reservacion.getNombre() == null || reservacion.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la reservación es obligatorio.");
        }
        if (reservacion.getDescripcion() != null && reservacion.getDescripcion().length() > 200) {
            throw new IllegalArgumentException("La descripción no puede superar los 200 caracteres.");
        }
        if (reservacion.getServicio() == null) {
            throw new IllegalArgumentException("El servicio asociado a la reservación es obligatorio.");
        }
    }
}
