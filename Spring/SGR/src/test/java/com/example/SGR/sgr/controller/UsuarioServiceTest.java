package com.example.SGR.sgr.controller;

import com.example.SGR.sgr.model.Usuario;
import com.example.SGR.sgr.utils.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testRegistrarUsuarioExitoso() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidos("Pérez");
        usuario.setCorreoElectronico("juanperez@email.com");
        usuario.setTelefono("1234567890");
        usuario.setContrasena("password123");
        usuario.setRol("ADMIN");
        usuario.setStatus(true);

        Mockito.when(usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())).thenReturn(false);
        Mockito.when(usuarioRepository.existsByTelefono(usuario.getTelefono())).thenReturn(false);
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioService.registrarUsuario(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Usuario registrado correctamente."));
    }

    @Test
    void testRegistrarUsuarioCorreoDuplicado() {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("juanperez@email.com");

        Mockito.when(usuarioRepository.existsByCorreoElectronico(usuario.getCorreoElectronico())).thenReturn(true);

        ResponseEntity<?> response = usuarioService.registrarUsuario(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("El correo electrónico ya está registrado."));
    }
    @Test
    void testRegistrarUsuarioTelefonoDuplicado() {
        Usuario usuario = new Usuario();
        usuario.setTelefono("1234567890");

        Mockito.when(usuarioRepository.existsByTelefono(usuario.getTelefono())).thenReturn(true);

        ResponseEntity<?> response = usuarioService.registrarUsuario(usuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("El número de teléfono ya está registrado."));
    }
    @Test
    void testObtenerUsuarioPorIdExitoso() {
        // Suponiendo que ya tienes un usuario con ID 1 en la base de datos
        Long id = 1L;

        // Configurar un mock o asegurarse de que el usuario con ID 1 exista en la base de datos de prueba
        Usuario usuarioEsperado = new Usuario();
        usuarioEsperado.setId(id);
        usuarioEsperado.setNombre("Juan");
        usuarioEsperado.setApellidos("Perez");
        usuarioEsperado.setCorreoElectronico("juan.perez@correo.com");
        usuarioEsperado.setTelefono("1234567890");
        usuarioEsperado.setContrasena("1234");
        usuarioEsperado.setRol("ADMIN");
        usuarioEsperado.setStatus(true);

        // Asegúrate de que el mock o base de datos de prueba tenga este usuario
        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioEsperado));

        // Llamar al método para obtener el usuario por ID
        ResponseEntity<?> response = usuarioService.obtenerUsuarioPorId(id);

        // Asegurarse de que la respuesta contenga el usuario esperado
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(usuarioEsperado, response.getBody());
    }

    @Test
    void testObtenerUsuarioPorIdNoEncontrado() {
        Long id = 1L;

        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioService.obtenerUsuarioPorId(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Usuario no encontrado."));
    }
    @Test
    void testActualizarUsuario() {
        Long id = 1L;
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        usuarioExistente.setNombre("Juan");
        usuarioExistente.setApellidos("Pérez");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Juan Carlos");
        usuarioActualizado.setApellidos("Pérez Gómez");

        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuarioActualizado);

        ResponseEntity<?> response = usuarioService.editarUsuario(id, usuarioActualizado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Usuario actualizado correctamente."));
    }
    @Test
    void testCambiarEstadoDeUsuario() {
        Long id = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setStatus(true);

        Mockito.when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioService.cambiarEstado(id, false);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Estado del usuario actualizado correctamente."));
    }
    @Test
    void testIniciarSesionExitoso() {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("juanperez@email.com");
        usuario.setContrasena("password123");

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setCorreoElectronico("juanperez@email.com");
        usuarioExistente.setContrasena("password123");

        Mockito.when(usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())).thenReturn(Optional.of(usuarioExistente));

        ResponseEntity<?> response = usuarioService.iniciarSesion(usuario);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Sesión iniciada correctamente."));
    }
    @Test
    void testIniciarSesionCredencialesInvalidas() {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("juanperez@email.com");
        usuario.setContrasena("wrongpassword");

        Mockito.when(usuarioRepository.findByCorreoElectronico(usuario.getCorreoElectronico())).thenReturn(Optional.empty());

        ResponseEntity<?> response = usuarioService.iniciarSesion(usuario);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Credenciales inválidas."));
    }

}
