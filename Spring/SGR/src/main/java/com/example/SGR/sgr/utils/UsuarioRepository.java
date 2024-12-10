package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoElectronico(String correoElectronico);
    boolean existsByCorreoElectronico(String correoElectronico);
    boolean existsByTelefono(String telefono); // Verifica si existe un teléfono en la BD

    // Consultas personalizadas adicionales
    List<Usuario> findByStatus(Boolean status); // Usuarios activos/inactivos
    List<Usuario> findByNombreContainingIgnoreCase(String nombre); // Búsqueda parcial por nombre
    List<Usuario> findByRol(String rol); // Usuarios por rol
    List<Usuario> findByStatusAndRol(Boolean status, String rol); // Activos por rol
    boolean existsByNombreOrTelefono(String nombre, String telefono); // Verifica duplicados por nombre o teléfono
    List<Usuario> findAllByOrderByNombreAsc(); // Orden ascendente por nombre
    List<Usuario> findAllByOrderByNombreDesc(); // Orden descendente por nombre
    Optional<Usuario> findByNombreAndCorreoElectronico(String nombre, String correoElectronico); // Por nombre y correo
    List<Usuario> findByCorreoElectronicoStartingWith(String prefix); // Correo con prefijo específico
}
