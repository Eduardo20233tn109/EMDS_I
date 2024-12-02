package com.example.SGR.sgr.usuariosP.controllerU;

import com.example.SGR.sgr.usuariosP.modelU.Usuario;
import com.example.SGR.sgr.usuariosP.utilsU.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarUsuario_exitoso() {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("test@example.com");
        usuario.setTelefono("1234567890");
        usuario.setNombre("Nombre Prueba");

        when(usuarioRepository.existsByCorreoElectronico("test@example.com")).thenReturn(false);
        when(usuarioRepository.existsByTelefono("1234567890")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.registrarUsuario(usuario);

        assertEquals(200, response.getStatusCodeValue());
        Usuario usuarioGuardado = (Usuario) response.getBody();
        assertNotNull(usuarioGuardado);
        assertTrue(usuarioGuardado.getStatus());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void registrarUsuario_correoYaRegistrado() {
        Usuario usuario = new Usuario();
        usuario.setCorreoElectronico("test@example.com");

        when(usuarioRepository.existsByCorreoElectronico("test@example.com")).thenReturn(true);

        ResponseEntity<?> response = usuarioController.registrarUsuario(usuario);

        assertEquals(400, response.getStatusCodeValue());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("El correo electrónico ya está registrado.", body.get("mensaje"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void registrarUsuario_telefonoYaRegistrado() {
        Usuario usuario = new Usuario();
        usuario.setTelefono("1234567890");

        when(usuarioRepository.existsByTelefono("1234567890")).thenReturn(true);

        ResponseEntity<?> response = usuarioController.registrarUsuario(usuario);

        assertEquals(400, response.getStatusCodeValue());
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertEquals("El número de teléfono ya está registrado.", body.get("mensaje"));
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void obtenerUsuarios_exitoso() {
        List<Usuario> usuarios = List.of(new Usuario(), new Usuario());
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioController.obtenerUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }
    @Test
    void obtenerUsuario_existente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.obtenerUsuario(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerUsuario_inexistente() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.obtenerUsuario(999L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(usuarioRepository, times(1)).findById(999L);
    }
    @Test
    void editarUsuario_existente() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Nuevo Nombre");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        ResponseEntity<Usuario> response = usuarioController.editarUsuario(1L, usuarioActualizado);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Nuevo Nombre", response.getBody().getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void editarUsuario_inexistente() {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre("Nuevo Nombre");

        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.editarUsuario(999L, usuarioActualizado);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(usuarioRepository, never()).save(any());
    }
    @Test
    void cambiarEstado_existente() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setStatus(true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.cambiarEstado(1L, false);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().getStatus());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void cambiarEstado_inexistente() {
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioController.cambiarEstado(999L, false);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(usuarioRepository, never()).save(any());
    }


}
