package com.example.SGR.sgr.usuariosP.utilsU;

import com.example.SGR.sgr.usuariosP.modelU.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByTelefono(String telefono); // Verifica si existe un teléfono en la BD
}
