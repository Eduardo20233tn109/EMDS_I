package com.example.SGR.sgr.usuariosP.serviceU;

import com.example.SGR.sgr.usuariosP.modelU.Usuario;
import com.example.SGR.sgr.usuariosP.utilsU.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Registrar un usuario
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setStatus(true); // Por defecto, el usuario está habilitado
        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Editar un usuario
    public Usuario editarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellidos(usuarioActualizado.getApellidos());
            usuario.setCorreoElectronico(usuarioActualizado.getCorreoElectronico());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setRol(usuarioActualizado.getRol());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Cambiar el estado del usuario
    public void cambiarEstado(Long id, Boolean estado) {
        usuarioRepository.findById(id).ifPresent(usuario -> {
            usuario.setStatus(estado);
            usuarioRepository.save(usuario);
        });
    }

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<String> iniciarSesion(@RequestParam String correoElectronico, @RequestParam String contrasena) {
        System.out.println("Correo: " + correoElectronico);  // Para verificar el correo
        if (correoElectronico == null || contrasena == null) {
            return ResponseEntity.status(400).body("Correo o contraseña no proporcionados");
        }

        return usuarioRepository.findByCorreoElectronico(correoElectronico)
                .filter(u -> u.getContrasena().equals(contrasena))
                .map(u -> ResponseEntity.ok("Sesión iniciada correctamente"))
                .orElse(ResponseEntity.status(401).body("Credenciales inválidas"));
    }

}
