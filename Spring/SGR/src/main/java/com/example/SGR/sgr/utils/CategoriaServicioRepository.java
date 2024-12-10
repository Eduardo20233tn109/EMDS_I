package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.model.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    List<CategoriaServicio> findByStatus(Boolean status); // Consultar categorías activas

}

