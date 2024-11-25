package com.example.SGR.sgr.servicios.utilsS;

import com.example.SGR.sgr.servicios.modelS.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}

