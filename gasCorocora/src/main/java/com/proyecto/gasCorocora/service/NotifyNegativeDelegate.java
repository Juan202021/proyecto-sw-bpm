package com.example.demo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("notifyNegative")
public class NotifyNegativeDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(NotifyNegativeDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Notificando al cliente sobre negativa para process {}", execution.getProcessInstanceId());
        execution.setVariable("notifiedNegative", true);
    }
}
