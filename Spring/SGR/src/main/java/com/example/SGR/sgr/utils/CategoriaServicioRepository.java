package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.model.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
}

