package com.example.SGR.sgr.servicios.controllerS;

import com.example.SGR.sgr.model.CategoriaServicio;
import com.example.SGR.sgr.model.Servicio;
import com.example.SGR.sgr.controller.ServicioService;
import com.example.SGR.sgr.utils.ServicioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiciosControllerTest {
    private final ServicioRepository servicioRepository = Mockito.mock(ServicioRepository.class);
    private final ServicioService servicioService = new ServicioService(servicioRepository);
    @Test
    void registrarServicio_exitoso() {
        // Crear una categoría de prueba
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setId(1L); // Asegúrate de asignar un ID válido
        categoria.setNombre("Categoría de Prueba");
        categoria.setDescripcion("Descripción de la categoría");
        categoria.setStatus(true);

        // Crear el servicio de prueba
        Servicio nuevoServicio = new Servicio();
        nuevoServicio.setNombre("Servicio de Prueba");
        nuevoServicio.setDescripcion("Descripción de prueba");
        nuevoServicio.setStatus(true);
        nuevoServicio.setCategoria(categoria);  // Asignar la categoría al servicio

        // Mock para el repositorio
        Mockito.when(servicioRepository.save(Mockito.any(Servicio.class))).thenReturn(nuevoServicio);

        // Llamada al servicio para registrar el servicio
        Servicio resultado = servicioService.registrarServicio(nuevoServicio);

        // Verificar que el resultado no sea nulo y que el nombre sea correcto
        assertNotNull(resultado);
        assertEquals("Servicio de Prueba", resultado.getNombre());
    }

    @Test
    void consultarServicios_todos() {
        List<Servicio> servicios = List.of(
                new Servicio("Servicio 1", "Descripción 1", true),
                new Servicio("Servicio 2", "Descripción 2", false)
        );

        Mockito.when(servicioRepository.findAll()).thenReturn(servicios);

        List<Servicio> resultado = servicioService.consultarServicios();

        assertEquals(2, resultado.size());
        assertEquals("Servicio 1", resultado.get(0).getNombre());
    }
    @Test
    void consultarServicios_activos() {
        List<Servicio> serviciosActivos = List.of(new Servicio("Servicio Activo", "Descripción", true));

        Mockito.when(servicioRepository.findByStatus(true)).thenReturn(serviciosActivos);

        List<Servicio> resultado = servicioService.consultarServiciosActivos();

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getStatus());
    }
    @Test
    void actualizarServicio_existente() {
        Servicio servicioExistente = new Servicio("Servicio Antiguo", "Descripción antigua", true);

        Mockito.when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicioExistente));
        Mockito.when(servicioRepository.save(Mockito.any(Servicio.class))).thenAnswer(i -> i.getArguments()[0]);

        Servicio actualizado = new Servicio("Servicio Actualizado", "Nueva descripción", true);

        Optional<Servicio> resultado = servicioService.actualizarServicio(1L, actualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Servicio Actualizado", resultado.get().getNombre());
    }
    @Test
    void cambiarEstadoServicio_inexistente() {
        Mockito.when(servicioRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Servicio> resultado = servicioService.cambiarEstadoServicio(999L, false);

        assertFalse(resultado.isPresent());
    }

}