package com.example.SGR.sgr.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.model.Servicio;
import com.example.SGR.sgr.utils.CategoriaServicioRepository;
import com.example.SGR.sgr.utils.ServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ServicioServiceTest {

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private CategoriaServicioRepository categoriaServicioRepository;

    @InjectMocks
    private ServicioService servicioService;

    private Servicio servicio;
    private CategoriaServicio categoria;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        categoria = new CategoriaServicio();
        categoria.setId(1L);
        categoria.setNombre("Categoria A");

        servicio = new Servicio("Servicio 1", "Descripcion de servicio", true);
        servicio.setCategoria(categoria);
    }

    @Test
    public void testRegistrarServicioExitoso() {
        // Configuración del mock
        when(servicioRepository.existsByNombre(servicio.getNombre())).thenReturn(false);
        when(categoriaServicioRepository.findById(servicio.getCategoria().getId())).thenReturn(Optional.of(categoria));
        when(servicioRepository.save(servicio)).thenReturn(servicio);

        ResponseEntity<?> response = servicioService.registrarServicio(servicio);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Servicio 1", ((Servicio) response.getBody()).getNombre());
    }

    @Test
    public void testRegistrarServicioConNombreDuplicado() {
        // Configuración del mock
        when(servicioRepository.existsByNombre(servicio.getNombre())).thenReturn(true);

        ResponseEntity<?> response = servicioService.registrarServicio(servicio);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Ya existe un servicio con el nombre"));
    }

    @Test
    public void testRegistrarServicioSinCategoriaValida() {
        servicio.setCategoria(null); // Sin categoría

        ResponseEntity<?> response = servicioService.registrarServicio(servicio);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("La categoría es requerida"));
    }

    @Test
    public void testConsultarServicioPorIdExistente() {
        // Configuración del mock
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));

        ResponseEntity<?> response = servicioService.consultarServicioPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Servicio 1", ((Servicio) response.getBody()).getNombre());
    }

    @Test
    public void testConsultarServicioPorIdNoExistente() {
        // Configuración del mock
        when(servicioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = servicioService.consultarServicioPorId(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Servicio no encontrado con ID"));
    }

    @Test
    public void testCambiarEstadoServicioExitoso() {
        // Configuración del mock
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(servicioRepository.save(servicio)).thenReturn(servicio);

        ResponseEntity<?> response = servicioService.cambiarEstadoServicio(1L, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Estado del servicio actualizado"));
        assertFalse(servicio.getStatus());
    }

    @Test
    public void testCambiarEstadoServicioNoExistente() {
        // Configuración del mock
        when(servicioRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = servicioService.cambiarEstadoServicio(1L, false);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Servicio no encontrado con ID"));
    }
}
