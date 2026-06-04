package com.example.demo.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("notifyAdecuaciones")
public class NotifyAdecuacionesDelegate implements JavaDelegate {

    private static final Logger log = LoggerFactory.getLogger(NotifyAdecuacionesDelegate.class);

    @Override
    public void execute(DelegateExecution execution) {
        log.info("Notificando al cliente sobre adecuaciones para process {}", execution.getProcessInstanceId());
        execution.setVariable("notifiedAdecuaciones", true);
    }
}
