package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Usuario;
import com.example.SGR.sgr.utils.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Registrar un usuario
    public ResponseEntity<?> registrarUsuario(Usuario usuario) {
        Map<String, Object> response = new HashMap<>();

        if (usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
            response.put("mensaje", "El correo electrónico ya está registrado.");
            return ResponseEntity.badRequest().body(response);
        }

        if (usuarioRepository.existsByTelefono(usuario.getTelefono())) {
            response.put("mensaje", "El número de teléfono ya está registrado.");
            return ResponseEntity.badRequest().body(response);
        }

        usuario.setStatus(true);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        response.put("mensaje", "Usuario registrado correctamente.");
        response.put("usuario", usuarioGuardado);

        return ResponseEntity.ok(response);
    }

    // Consultar todos los usuarios
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    // Consultar un usuario por ID
    public ResponseEntity<?> obtenerUsuarioPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Usuario no encontrado."));
        }
    }

    // Editar un usuario
    public ResponseEntity<?> editarUsuario(Long id, Usuario usuarioActualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setContrasena(usuarioActualizado.getContrasena());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setStatus(usuarioActualizado.getStatus());

            Usuario usuarioGuardado = usuarioRepository.save(usuario);
            return ResponseEntity.ok(Map.of("mensaje", "Usuario actualizado correctamente.", "usuario", usuarioGuardado));
        } else {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Usuario no encontrado."));
        }
    }

    // Cambiar el estado de un usuario
    public ResponseEntity<?> cambiarEstado(Long id, Boolean estado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setStatus(estado);
            usuarioRepository.save(usuario);
            return ResponseEntity.ok(Map.of("mensaje", "Estado del usuario actualizado correctamente."));
        } else {
            return ResponseEntity.status(404).body(Map.of("mensaje", "Usuario no encontrado."));
        }
    }

    // Iniciar sesión
    public ResponseEntity<?> iniciarSesion(Usuario usuario) {
        Map<String, Object> response = new HashMap<>();

        if (usuario.getCorreoElectronico() == null || usuario.getContrasena() == null) {
            response.put("mensaje", "Correo o contraseña no proporcionados.");
            return ResponseEntity.badRequest().body(response);
        }

        return usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())
                .filter(u -> u.getContrasena().equals(usuario.getContrasena()))
                .map(u -> {
                    response.put("mensaje", "Sesión iniciada correctamente.");
                    response.put("rol", u.getRol());
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("mensaje", "Credenciales inválidas.");
                    return ResponseEntity.status(401).body(response);
                });
    }
}
