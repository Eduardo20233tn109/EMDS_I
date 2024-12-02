package com.example.SGR.sgr.reservaciones.serviceR;

import com.example.SGR.sgr.reservaciones.modelR.Reservacion;
import com.example.SGR.sgr.reservaciones.utilsR.ReservacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservacionService {

    private final ReservacionRepository reservacionRepository;

    public ReservacionService(ReservacionRepository reservacionRepository) {
        this.reservacionRepository = reservacionRepository;
    }

    // Registrar una nueva reservación
    public Reservacion registrarReservacion(Reservacion reservacion) {
        return reservacionRepository.save(reservacion);
    }

    // Consultar todas las reservaciones
    public List<Reservacion> consultarReservaciones() {
        return reservacionRepository.findAll();
    }

    // Consultar reservaciones activas
    public List<Reservacion> consultarReservacionesActivas() {
        return reservacionRepository.findByStatus(true);
    }

    // Actualizar una reservación
    public Optional<Reservacion> actualizarReservacion(Long id, Reservacion reservacionActualizada) {
        return reservacionRepository.findById(id).map(reservacion -> {
            reservacion.setNombre(reservacionActualizada.getNombre());
            reservacion.setDescripcion(reservacionActualizada.getDescripcion());
            reservacion.setServicio(reservacionActualizada.getServicio());
            reservacion.setUsuario(reservacionActualizada.getUsuario());
            return reservacionRepository.save(reservacion);
        });
    }

    // Cambiar el estado de una reservación
    public Optional<Reservacion> cambiarEstadoReservacion(Long id, Boolean nuevoEstado) {
        return reservacionRepository.findById(id).map(reservacion -> {
            reservacion.setStatus(nuevoEstado);
            return reservacionRepository.save(reservacion);
        });
    }
}
