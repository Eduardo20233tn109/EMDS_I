package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.model.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
    List<Reservacion> findByStatus(Boolean status); // Para consultar reservaciones activas o inactivas


}

