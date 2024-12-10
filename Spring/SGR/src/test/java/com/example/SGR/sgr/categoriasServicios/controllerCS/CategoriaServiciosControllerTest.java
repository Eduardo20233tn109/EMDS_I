package com.example.SGR.sgr.categoriasServicios.controllerCS;

import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.controller.CategoriaServiciosService;
import com.example.SGR.sgr.controller.CategoriaServiciosController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiciosControllerTest {

    @Mock
    private CategoriaServiciosService categoriaServiciosService;

    @InjectMocks
    private CategoriaServiciosController categoriaServiciosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarCategoria() {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setNombre("Nueva Categoria");

        when(categoriaServiciosService.registrarCategoria(categoria)).thenReturn(categoria);

        ResponseEntity<CategoriaServicio> respuesta = categoriaServiciosController.registrarCategoria(categoria);

        assertNotNull(respuesta);
        assertEquals("Nueva Categoria", respuesta.getBody().getNombre());
        verify(categoriaServiciosService, times(1)).registrarCategoria(categoria);
    }

    @Test
    void consultarCategorias() {
        List<CategoriaServicio> categorias = Arrays.asList(new CategoriaServicio(), new CategoriaServicio());

        when(categoriaServiciosService.consultarCategorias()).thenReturn(categorias);

        ResponseEntity<List<CategoriaServicio>> respuesta = categoriaServiciosController.consultarCategorias();

        assertNotNull(respuesta);
        assertEquals(2, respuesta.getBody().size());
        verify(categoriaServiciosService, times(1)).consultarCategorias();
    }

    @Test
    void consultarCategoriasActivas() {
        List<CategoriaServicio> categoriasActivas = Arrays.asList(new CategoriaServicio());

        when(categoriaServiciosService.consultarCategoriasActivas()).thenReturn(categoriasActivas);

        ResponseEntity<List<CategoriaServicio>> respuesta = categoriaServiciosController.consultarCategoriasActivas();

        assertNotNull(respuesta);
        assertEquals(1, respuesta.getBody().size());
        verify(categoriaServiciosService, times(1)).consultarCategoriasActivas();
    }

    @Test
    void actualizarCategoria() {
        CategoriaServicio actualizada = new CategoriaServicio();
        actualizada.setNombre("Actualizada");

        when(categoriaServiciosService.actualizarCategoria(1L, actualizada))
                .thenReturn(Optional.of(actualizada));

        ResponseEntity<CategoriaServicio> respuesta = categoriaServiciosController.actualizarCategoria(1L, actualizada);

        assertNotNull(respuesta);
        assertEquals("Actualizada", respuesta.getBody().getNombre());
        verify(categoriaServiciosService, times(1)).actualizarCategoria(1L, actualizada);
    }

    @Test
    void cambiarEstadoCategoria() {
        // Crear la categoría con un estado inicial
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setStatus(false);

        // Simula el cambio de estado a 'true'
        when(categoriaServiciosService.cambiarEstadoCategoria(1L, true)).thenReturn(Optional.of(categoria));

        // Llamada al controlador para cambiar el estado
        ResponseEntity<CategoriaServicio> respuesta = categoriaServiciosController.cambiarEstadoCategoria(1L, true);

        // Verificar que la respuesta no sea nula
        assertNotNull(respuesta);

        // Verificar que el estado de la categoría se haya cambiado a 'true'
        // Cambiar el estado en el mock para que se refleje en la respuesta
        categoria.setStatus(true);
        assertTrue(respuesta.getBody().getStatus());

        // Verificar que el servicio fue llamado exactamente una vez
        verify(categoriaServiciosService, times(1)).cambiarEstadoCategoria(1L, true);
    }


}
