package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Reservacion;
import com.example.SGR.sgr.model.Servicio;
import com.example.SGR.sgr.utils.ReservacionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReservacionServiceTest {

    @Mock
    private ReservacionRepository reservacionRepository;

    @InjectMocks
    private ReservacionService reservacionService;

    public ReservacionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegistrarReservacion() {
        Servicio servicioMock = new Servicio();
        servicioMock.setId(1L);

        Reservacion reservacion = new Reservacion("Reserva Test", "Descripción válida", true);
        reservacion.setServicio(servicioMock);

        when(reservacionRepository.save(any(Reservacion.class))).thenReturn(reservacion);

        ResponseEntity<?> response = reservacionService.registrarReservacion(reservacion);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
        assertEquals(reservacion, response.getBody());
    }

    @Test
    public void testConsultarReservaciones() {
        // Mockear la lista de reservaciones
        List<Reservacion> reservaciones = Arrays.asList(
                new Reservacion("Reservacion1", "Descripcion1", true),
                new Reservacion("Reservacion2", "Descripcion2", false)
        );
        when(reservacionRepository.findAll()).thenReturn(reservaciones);

        // Llamar al método bajo prueba
        ResponseEntity<List<Reservacion>> response = reservacionService.consultarReservaciones();

        // Verificar la respuesta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservaciones, response.getBody());
    }

    @Test
    public void testConsultarReservacionesActivas() {
        // Mockear la lista de reservaciones activas
        List<Reservacion> reservacionesActivas = Arrays.asList(
                new Reservacion("Reservacion1", "Descripcion1", true)
        );
        when(reservacionRepository.findByStatus(true)).thenReturn(reservacionesActivas);

        // Llamar al método bajo prueba
        ResponseEntity<List<Reservacion>> response = reservacionService.consultarReservacionesActivas();

        // Verificar la respuesta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservacionesActivas, response.getBody());
    }

    @Test
    public void testObtenerReservacionPorId_Encontrada() {
        // Mockear una reservación existente
        Reservacion reservacion = new Reservacion("Nombre", "Descripción", true);
        when(reservacionRepository.findById(1L)).thenReturn(Optional.of(reservacion));

        // Llamar al método bajo prueba
        ResponseEntity<?> response = reservacionService.obtenerReservacionPorId(1L);

        // Verificar la respuesta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservacion, response.getBody());
    }

    @Test
    public void testObtenerReservacionPorId_NoEncontrada() {
        // Mockear una reservación no existente
        when(reservacionRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al método bajo prueba
        ResponseEntity<?> response = reservacionService.obtenerReservacionPorId(1L);

        // Verificar la respuesta
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Reservación no encontrada con ID: 1", response.getBody());
    }

    @Test
    void testActualizarReservacion() {
        Long reservacionId = 1L;
        Servicio servicioMock = new Servicio();
        servicioMock.setId(1L);

        Reservacion reservacionExistente = new Reservacion("Reserva Original", "Descripción original", true);
        reservacionExistente.setId(reservacionId);
        reservacionExistente.setServicio(servicioMock);

        Reservacion reservacionActualizada = new Reservacion("Reserva Actualizada", "Descripción actualizada", false);
        reservacionActualizada.setServicio(servicioMock);

        when(reservacionRepository.findById(reservacionId)).thenReturn(Optional.of(reservacionExistente));
        when(reservacionRepository.save(any(Reservacion.class))).thenReturn(reservacionActualizada);

        ResponseEntity<?> response = reservacionService.actualizarReservacion(reservacionId, reservacionActualizada);

        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertEquals(reservacionActualizada.getNombre(), ((Reservacion) response.getBody()).getNombre());
        assertEquals(reservacionActualizada.getDescripcion(), ((Reservacion) response.getBody()).getDescripcion());
        assertEquals(reservacionActualizada.getStatus(), ((Reservacion) response.getBody()).getStatus());
    }


    @Test
    public void testCambiarEstadoReservacion() {
        // Mockear una reservación existente
        Reservacion reservacion = new Reservacion("Nombre", "Descripción", true);
        when(reservacionRepository.findById(1L)).thenReturn(Optional.of(reservacion));

        // Llamar al método bajo prueba
        ResponseEntity<?> response = reservacionService.cambiarEstadoReservacion(1L, false);

        // Verificar la respuesta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Estado de la reservación actualizado a Inactivo", response.getBody());

        // Verificar que el estado fue actualizado
        ArgumentCaptor<Reservacion> captor = ArgumentCaptor.forClass(Reservacion.class);
        verify(reservacionRepository).save(captor.capture());
        assertFalse(captor.getValue().getStatus());
    }
}
