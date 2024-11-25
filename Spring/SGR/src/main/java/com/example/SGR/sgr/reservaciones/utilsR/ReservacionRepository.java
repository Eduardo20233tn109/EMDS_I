package com.example.SGR.sgr.reservaciones.utilsR;

import com.example.SGR.sgr.reservaciones.modelR.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
}

