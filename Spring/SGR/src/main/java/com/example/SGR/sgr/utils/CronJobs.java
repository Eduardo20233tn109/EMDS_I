package com.example.SGR.sgr.utils;

import com.example.SGR.sgr.controller.ServicioService;
import com.example.SGR.sgr.model.Servicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronJobs {
    private static final Logger logger = LoggerFactory.getLogger(CronJobs.class);

    private final ServicioService servicioService;

    public CronJobs(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    // Cron job que corre cada minuto
    @Scheduled(cron = "0 * * * * ?") // Cada minuto
    public void logServiciosActivos() {
        logger.info("Iniciando tarea programada: Registrar servicios activos.");

        // Usar el nuevo método obtenerServiciosActivos para obtener la lista de servicios activos
        servicioService.obtenerServiciosActivos().forEach(servicio ->
                logger.info("Servicio activo: ID={} Nombre={}", servicio.getId(), servicio.getNombre())
        );

        logger.info("Tarea programada completada.");
    }
}
