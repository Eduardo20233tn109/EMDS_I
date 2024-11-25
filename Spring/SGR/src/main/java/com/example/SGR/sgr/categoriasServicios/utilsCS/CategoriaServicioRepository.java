package com.example.SGR.sgr.categoriasServicios.utilsCS;

import com.example.SGR.sgr.categoriasServicios.modelCS.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
}

