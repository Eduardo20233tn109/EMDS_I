package com.example.SGR.sgr.usuariosP.controllerU;

import com.example.SGR.sgr.usuariosP.modelU.Usuario;
import com.example.SGR.sgr.usuariosP.utilsU.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        // Validar si el correo ya está registrado
        if (usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "El correo electrónico ya está registrado."));
        }
        // Validar si el número de teléfono ya está registrado
        if (usuarioRepository.existsByTelefono(usuario.getTelefono())) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "El número de teléfono ya está registrado."));
        }
        // Configurar el estado por defecto como habilitado
        usuario.setStatus(true);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuarioGuardado);
    }



    // Consultar todos los usuarios
    @GetMapping("/lista-usuarios")
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // Consultar un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("editar/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            // Actualizar los datos del usuario, incluyendo la contraseña
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setContrasena(usuarioActualizado.getContrasena()); // Actualiza la contraseña
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setStatus(usuarioActualizado.getStatus());
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }).orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/estado")
    public ResponseEntity<Usuario> cambiarEstado(@PathVariable Long id, @RequestParam Boolean estado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setStatus(estado);  // Cambiar el estado con el valor recibido
            usuarioRepository.save(usuario);  // Guardar el usuario con el nuevo estado
            return ResponseEntity.ok(usuario);  // Responder con el usuario actualizado
        }).orElse(ResponseEntity.notFound().build());  // Si no se encuentra el usuario, devolver 404
    }



/*
    @PostMapping("/iniciar-sesion")
    public ResponseEntity<String> iniciarSesion(@RequestBody Usuario usuario) {
        System.out.println("Correo: " + usuario.getCorreoElectronico());  // Para verificar el correo
        if (usuario.getCorreoElectronico() == null || usuario.getContrasena() == null) {
            return ResponseEntity.status(400).body("Correo o contraseña no proporcionados");
        }

        return usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())
                .filter(u -> u.getContrasena().equals(usuario.getContrasena()))
                .map(u -> ResponseEntity.ok("Sesión iniciada correctamente"))
                .orElse(ResponseEntity.status(401).body("Credenciales inválidas"));
    }

 */
@PostMapping("/iniciar-sesion")
public ResponseEntity<Map<String, Object>> iniciarSesion(@RequestBody Usuario usuario) {
    Map<String, Object> response = new HashMap<>();

    if (usuario.getCorreoElectronico() == null || usuario.getContrasena() == null) {
        response.put("mensaje", "Correo o contraseña no proporcionados");
        response.put("success", false);
        return ResponseEntity.status(400).body(response);
    }

    return usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())
            .filter(u -> u.getContrasena().equals(usuario.getContrasena()))
            .map(u -> {
                response.put("mensaje", "Sesión iniciada correctamente");
                response.put("success", true);
                response.put("rol", u.getRol()); // Asegúrate de devolver "rol"
                return ResponseEntity.ok(response);
            })
            .orElseGet(() -> {
                response.put("mensaje", "Credenciales inválidas");
                response.put("success", false);
                return ResponseEntity.status(401).body(response);
            });
}
}
