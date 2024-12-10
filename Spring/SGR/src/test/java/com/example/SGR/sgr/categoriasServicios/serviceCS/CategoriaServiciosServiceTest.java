package com.example.SGR.sgr.categoriasServicios.serviceCS;
import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.controller.CategoriaServiciosService;
import com.example.SGR.sgr.utils.CategoriaServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoriaServiciosServiceTest {

    @Mock
    private CategoriaServicioRepository categoriaServicioRepository;

    @InjectMocks
    private CategoriaServiciosService categoriaServiciosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarCategoria() {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setNombre("Test Categoria");
        categoria.setDescripcion("Descripción de prueba");

        when(categoriaServicioRepository.save(categoria)).thenReturn(categoria);

        CategoriaServicio resultado = categoriaServiciosService.registrarCategoria(categoria);

        assertNotNull(resultado);
        assertEquals("Test Categoria", resultado.getNombre());
        verify(categoriaServicioRepository, times(1)).save(categoria);
    }

    @Test
    void consultarCategorias() {
        List<CategoriaServicio> categorias = Arrays.asList(
                new CategoriaServicio(),
                new CategoriaServicio()
        );

        when(categoriaServicioRepository.findAll()).thenReturn(categorias);

        List<CategoriaServicio> resultado = categoriaServiciosService.consultarCategorias();

        assertEquals(2, resultado.size());
        verify(categoriaServicioRepository, times(1)).findAll();
    }

    @Test
    void consultarCategoriasActivas() {
        List<CategoriaServicio> categoriasActivas = Arrays.asList(new CategoriaServicio());

        when(categoriaServicioRepository.findByStatus(true)).thenReturn(categoriasActivas);

        List<CategoriaServicio> resultado = categoriaServiciosService.consultarCategoriasActivas();

        assertEquals(1, resultado.size());
        verify(categoriaServicioRepository, times(1)).findByStatus(true);
    }

    @Test
    void actualizarCategoria() {
        CategoriaServicio existente = new CategoriaServicio();
        existente.setId(1L);
        existente.setNombre("Existente");

        CategoriaServicio actualizada = new CategoriaServicio();
        actualizada.setNombre("Actualizada");

        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriaServicioRepository.save(existente)).thenReturn(existente);

        Optional<CategoriaServicio> resultado = categoriaServiciosService.actualizarCategoria(1L, actualizada);

        assertTrue(resultado.isPresent());
        assertEquals("Actualizada", resultado.get().getNombre());
        verify(categoriaServicioRepository, times(1)).findById(1L);
        verify(categoriaServicioRepository, times(1)).save(existente);
    }

    @Test
    void cambiarEstadoCategoria() {
        CategoriaServicio existente = new CategoriaServicio();
        existente.setId(1L);
        existente.setStatus(true);

        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(categoriaServicioRepository.save(existente)).thenReturn(existente);

        Optional<CategoriaServicio> resultado = categoriaServiciosService.cambiarEstadoCategoria(1L, false);

        assertTrue(resultado.isPresent());
        assertFalse(resultado.get().getStatus());
        verify(categoriaServicioRepository, times(1)).findById(1L);
        verify(categoriaServicioRepository, times(1)).save(existente);
    }
}
