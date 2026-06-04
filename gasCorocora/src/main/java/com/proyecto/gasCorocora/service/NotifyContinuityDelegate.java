package com.example.demo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("notifyContinuity")
public class NotifyContinuityDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(NotifyContinuityDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Notificando al cliente continuidad para process {}", execution.getProcessInstanceId());
        execution.setVariable("notifiedContinuity", true);
    }
}
