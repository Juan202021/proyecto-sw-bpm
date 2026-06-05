package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("assignInspection")
public class AssignInspectionDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(AssignInspectionDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Asignando fecha de inspección for process {}", execution.getProcessInstanceId());
        execution.setVariable("inspectionAssigned", true);
    }
}
