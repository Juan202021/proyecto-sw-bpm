package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("sendContract")
public class SendContractDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(SendContractDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Enviando contrato al cliente para process {}", execution.getProcessInstanceId());
        execution.setVariable("contractSent", true);
    }
}
