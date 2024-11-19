package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.model.Reservacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
}

