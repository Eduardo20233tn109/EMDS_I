package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.controller.CategoriaServiciosService;
import com.example.SGR.sgr.model.CategoriaServicio;
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

    @PostMapping("/registrar")
    public ResponseEntity<CategoriaServicio> registrarCategoria(@RequestBody CategoriaServicio categoria) {
        return ResponseEntity.ok(categoriaServiciosService.registrarCategoria(categoria));
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<CategoriaServicio>> consultarCategorias() {
        return ResponseEntity.ok(categoriaServiciosService.consultarCategorias());
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaServicio>> consultarCategoriasActivas() {
        return ResponseEntity.ok(categoriaServiciosService.consultarCategoriasActivas());
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<CategoriaServicio> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaServicio categoria) {
        return ResponseEntity.ok(categoriaServiciosService.actualizarCategoria(id, categoria));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<CategoriaServicio> cambiarEstadoCategoria(@PathVariable Long id, @RequestParam Boolean estado) {
        return ResponseEntity.ok(categoriaServiciosService.cambiarEstadoCategoria(id, estado));
    }

    @GetMapping("/editar/{id}")
    public ResponseEntity<CategoriaServicio> obtenerCategoriaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaServiciosService.obtenerCategoriaPorId(id));
    }
}
