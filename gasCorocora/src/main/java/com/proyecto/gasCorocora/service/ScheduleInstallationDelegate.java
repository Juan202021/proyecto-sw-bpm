package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("scheduleInstallation")
public class ScheduleInstallationDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(ScheduleInstallationDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Agendando instalación para process {}", execution.getProcessInstanceId());
        execution.setVariable("installationScheduled", true);
    }
}
