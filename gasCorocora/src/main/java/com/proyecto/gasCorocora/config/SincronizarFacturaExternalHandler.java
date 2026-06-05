package com.proyecto.gasCorocora.config;

import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SincronizarFacturaExternalHandler {

    private static final Logger log = LoggerFactory.getLogger(SincronizarFacturaExternalHandler.class);
    private static final String WORKER_ID = "factura-worker";
    private static final String TOPIC = "sincronizar-factura";

    private final ExternalTaskService externalTaskService;

    public SincronizarFacturaExternalHandler(ExternalTaskService externalTaskService) {
        this.externalTaskService = externalTaskService;
    }

    @Scheduled(fixedDelay = 3000)
    public void completarTareasExternas() {
        List<LockedExternalTask> tareas = externalTaskService.fetchAndLock(10, WORKER_ID)
                .topic(TOPIC, 60_000L)
                .execute();

        for (LockedExternalTask tarea : tareas) {
            log.info("Completando tarea externa {} del proceso {}", tarea.getId(), tarea.getProcessInstanceId());
            externalTaskService.complete(tarea.getId(), WORKER_ID);
        }
    }
}
