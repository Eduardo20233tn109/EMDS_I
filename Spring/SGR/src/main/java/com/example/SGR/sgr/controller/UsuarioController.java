package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Usuario;
import com.example.SGR.sgr.controller.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registrar un usuario
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    // Consultar todos los usuarios
    @GetMapping("/lista-usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    // Consultar un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id);
    }

    // Editar un usuario
    @PutMapping("editar/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioService.editarUsuario(id, usuarioActualizado);
    }

    // Cambiar el estado de un usuario
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        return usuarioService.cambiarEstado(id, estado);
    }

    // Iniciar sesión
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario usuario) {
        return usuarioService.iniciarSesion(usuario);
    }
}
