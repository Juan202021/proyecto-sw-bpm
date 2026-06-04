package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class ConsultarHistorico implements JavaDelegate {
    private final Random random = new Random();
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean emergencia = random.nextBoolean();
        if (emergencia){
            execution.setVariable("anomalia", "emergencia");
            System.out.println("Anomalia = emergencia");
        }
        else {
            execution.setVariable("anomalia", "leve");
            System.out.println("Anomalia = leve");
        }
    }
}
