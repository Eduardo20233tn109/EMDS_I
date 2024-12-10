package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByStatus(Boolean status); // Consultar servicios activos
    Optional<Servicio> findByNombre(String nombre); // Buscar un servicio por nombre
    boolean existsByNombre(String nombre); // Verificar si ya existe un servicio por nombre
}
