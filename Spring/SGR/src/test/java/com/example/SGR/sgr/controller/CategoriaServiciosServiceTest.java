package com.example.SGR.sgr.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.utils.CategoriaServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoriaServiciosServiceTest {

    private CategoriaServicioRepository categoriaServicioRepository;
    private CategoriaServiciosService categoriaServiciosService;

    @BeforeEach
    void setUp() {
        categoriaServicioRepository = mock(CategoriaServicioRepository.class);
        categoriaServiciosService = new CategoriaServiciosService(categoriaServicioRepository);
    }

    @Test
    void registrarCategoria_ValidData_Success() {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setNombre("Categoría Prueba");
        categoria.setDescripcion("Descripción de prueba");
        categoria.setStatus(true);

        when(categoriaServicioRepository.existsByNombre("Categoría Prueba")).thenReturn(false);
        when(categoriaServicioRepository.save(any(CategoriaServicio.class))).thenReturn(categoria);

        CategoriaServicio result = categoriaServiciosService.registrarCategoria(categoria);

        assertNotNull(result);
        assertEquals("Categoría Prueba", result.getNombre());
        verify(categoriaServicioRepository, times(1)).save(categoria);
    }

    @Test
    void registrarCategoria_DuplicateName_ThrowsException() {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setNombre("Categoría Duplicada");

        when(categoriaServicioRepository.existsByNombre("Categoría Duplicada")).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> categoriaServiciosService.registrarCategoria(categoria));
        verify(categoriaServicioRepository, never()).save(any(CategoriaServicio.class));
    }

    @Test
    void consultarCategorias_ReturnsAllCategories() {
        CategoriaServicio categoria1 = new CategoriaServicio();
        categoria1.setNombre("Categoría 1");
        CategoriaServicio categoria2 = new CategoriaServicio();
        categoria2.setNombre("Categoría 2");

        when(categoriaServicioRepository.findAll()).thenReturn(Arrays.asList(categoria1, categoria2));

        List<CategoriaServicio> result = categoriaServiciosService.consultarCategorias();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoriaServicioRepository, times(1)).findAll();
    }

    @Test
    void consultarCategoriasActivas_ReturnsActiveCategories() {
        CategoriaServicio categoria1 = new CategoriaServicio();
        categoria1.setNombre("Categoría Activa 1");
        categoria1.setStatus(true);
        CategoriaServicio categoria2 = new CategoriaServicio();
        categoria2.setNombre("Categoría Activa 2");
        categoria2.setStatus(true);

        when(categoriaServicioRepository.findByStatus(true)).thenReturn(Arrays.asList(categoria1, categoria2));

        List<CategoriaServicio> result = categoriaServiciosService.consultarCategoriasActivas();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(categoriaServicioRepository, times(1)).findByStatus(true);
    }

    @Test
    void actualizarCategoria_ValidIdAndData_Success() {
        CategoriaServicio categoriaExistente = new CategoriaServicio();
        categoriaExistente.setId(1L);
        categoriaExistente.setNombre("Categoría Original");
        categoriaExistente.setDescripcion("Descripción Original");
        categoriaExistente.setStatus(true);

        CategoriaServicio categoriaActualizada = new CategoriaServicio();
        categoriaActualizada.setNombre("Categoría Actualizada");
        categoriaActualizada.setDescripcion("Descripción Actualizada");
        categoriaActualizada.setStatus(false);

        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(categoriaExistente));
        when(categoriaServicioRepository.save(any(CategoriaServicio.class))).thenReturn(categoriaActualizada);

        CategoriaServicio result = categoriaServiciosService.actualizarCategoria(1L, categoriaActualizada);

        assertNotNull(result);
        assertEquals("Categoría Actualizada", result.getNombre());
        verify(categoriaServicioRepository, times(1)).save(categoriaExistente);
    }

    @Test
    void cambiarEstadoCategoria_ValidId_Success() {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setId(1L);
        categoria.setNombre("Categoría");
        categoria.setStatus(true);

        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaServicioRepository.save(any(CategoriaServicio.class))).thenReturn(categoria);

        CategoriaServicio result = categoriaServiciosService.cambiarEstadoCategoria(1L, false);

        assertNotNull(result);
        assertFalse(result.getStatus());
        verify(categoriaServicioRepository, times(1)).save(categoria);
    }

    @Test
    void obtenerCategoriaPorId_ValidId_Success() {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setId(1L);
        categoria.setNombre("Categoría");

        when(categoriaServicioRepository.findById(1L)).thenReturn(Optional.of(categoria));

        CategoriaServicio result = categoriaServiciosService.obtenerCategoriaPorId(1L);

        assertNotNull(result);
        assertEquals("Categoría", result.getNombre());
        verify(categoriaServicioRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerCategoriaPorId_InvalidId_ThrowsException() {
        when(categoriaServicioRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> categoriaServiciosService.obtenerCategoriaPorId(1L));
        verify(categoriaServicioRepository, times(1)).findById(1L);
    }
}
