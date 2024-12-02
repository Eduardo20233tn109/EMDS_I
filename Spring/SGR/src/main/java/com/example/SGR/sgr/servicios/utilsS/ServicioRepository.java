package com.example.SGR.sgr.servicios.utilsS;

import com.example.SGR.sgr.servicios.modelS.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByStatus(Boolean status);// Consultar servicios activos

}

