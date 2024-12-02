package com.example.SGR.sgr.categoriasServicios.controllerCS;

import com.example.SGR.sgr.categoriasServicios.modelCS.CategoriaServicio;
import com.example.SGR.sgr.categoriasServicios.serviceCS.CategoriaServiciosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaServiciosController {

    private final CategoriaServiciosService categoriaServiciosService;

    public CategoriaServiciosController(CategoriaServiciosService categoriaServiciosService) {
        this.categoriaServiciosService = categoriaServiciosService;
    }

    // Registrar una nueva categoría
    @PostMapping("/registrar")
    public ResponseEntity<CategoriaServicio> registrarCategoria(@RequestBody CategoriaServicio categoria) {
        return ResponseEntity.ok(categoriaServiciosService.registrarCategoria(categoria));
    }

    // Consultar todas las categorías
    @GetMapping("/consultar")
    public ResponseEntity<List<CategoriaServicio>> consultarCategorias() {
        return ResponseEntity.ok(categoriaServiciosService.consultarCategorias());
    }

    // Consultar categorías activas
    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaServicio>> consultarCategoriasActivas() {
        return ResponseEntity.ok(categoriaServiciosService.consultarCategoriasActivas());
    }

    // Actualizar una categoría
    @PutMapping("{id}")
    public ResponseEntity<CategoriaServicio> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaServicio categoria) {
        return categoriaServiciosService.actualizarCategoria(id, categoria)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Cambiar el estado de una categoría
    @PatchMapping("/{id}/estado")
    public ResponseEntity<CategoriaServicio> cambiarEstadoCategoria(@PathVariable Long id, @RequestParam Boolean estado) {
        return categoriaServiciosService.cambiarEstadoCategoria(id, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
