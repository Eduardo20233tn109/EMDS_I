package com.example.SGR.sgr.usuariosP.controllerU;

import com.example.SGR.sgr.usuariosP.modelU.Usuario;
import com.example.SGR.sgr.usuariosP.utilsU.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Registrar un usuario
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario usuario) {
        usuario.setStatus(true); // Por defecto, el usuario está habilitado
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    // Consultar todos los usuarios
    @GetMapping
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

    // Editar un usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setRol(usuarioActualizado.getRol());
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



}
