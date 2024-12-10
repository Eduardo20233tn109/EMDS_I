package com.example.SGR.sgr.reservaciones.controllerR;

import com.example.SGR.sgr.model.Reservacion;
import com.example.SGR.sgr.controller.ReservacionService;
import com.example.SGR.sgr.utils.ReservacionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservacionControllerTest {

    private final ReservacionRepository reservacionRepository = Mockito.mock(ReservacionRepository.class);
    private final ReservacionService reservacionService = new ReservacionService(reservacionRepository);
    @Test
    void registrarReservacion_exitosamente() {
        Reservacion nuevaReservacion = new Reservacion();
        nuevaReservacion.setNombre("Reservación de prueba");
        nuevaReservacion.setDescripcion("Descripción de prueba");
        nuevaReservacion.setStatus(true);

        Mockito.when(reservacionRepository.save(Mockito.any(Reservacion.class))).thenReturn(nuevaReservacion);

        Reservacion resultado = reservacionService.registrarReservacion(nuevaReservacion);

        assertNotNull(resultado);
        assertEquals("Reservación de prueba", resultado.getNombre());
        assertTrue(resultado.getStatus());
    }
    @Test
    void consultarReservaciones_todas() {
        List<Reservacion> reservaciones = List.of(
                new Reservacion("Reservación 1", "Descripción 1", true),
                new Reservacion("Reservación 2", "Descripción 2", false)
        );

        Mockito.when(reservacionRepository.findAll()).thenReturn(reservaciones);

        List<Reservacion> resultado = reservacionService.consultarReservaciones();

        assertEquals(2, resultado.size());
        assertEquals("Reservación 1", resultado.get(0).getNombre());
    }

    @Test
    void consultarReservaciones_activas() {
        List<Reservacion> activas = List.of(new Reservacion("Reservación Activa", "Descripción", true));

        Mockito.when(reservacionRepository.findByStatus(true)).thenReturn(activas);

        List<Reservacion> resultado = reservacionService.consultarReservacionesActivas();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getStatus());
    }

    @Test
    void actualizarReservacion_existente() {
        Reservacion reservacionExistente = new Reservacion("Reservación Antigua", "Descripción Antigua", true);

        Mockito.when(reservacionRepository.findById(1L)).thenReturn(Optional.of(reservacionExistente));
        Mockito.when(reservacionRepository.save(Mockito.any(Reservacion.class))).thenAnswer(i -> i.getArguments()[0]);

        Reservacion actualizada = new Reservacion("Reservación Actualizada", "Nueva Descripción", true);

        Optional<Reservacion> resultado = reservacionService.actualizarReservacion(1L, actualizada);

        assertTrue(resultado.isPresent());
        assertEquals("Reservación Actualizada", resultado.get().getNombre());
    }
    @Test
    void cambiarEstadoReservacion_inexistente() {
        Mockito.when(reservacionRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Reservacion> resultado = reservacionService.cambiarEstadoReservacion(999L, false);

        assertFalse(resultado.isPresent());
    }


}