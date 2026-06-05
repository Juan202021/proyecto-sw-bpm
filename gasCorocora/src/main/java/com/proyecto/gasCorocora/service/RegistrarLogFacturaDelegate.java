package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("registrarLogFactura")
public class RegistrarLogFacturaDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(RegistrarLogFacturaDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Factura generada para cliente {} (proceso {})",
                execution.getVariable("clienteId"),
                execution.getProcessInstanceId());
    }
}
