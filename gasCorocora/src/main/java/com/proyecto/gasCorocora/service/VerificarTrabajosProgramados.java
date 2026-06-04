package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

public class VerificarTrabajosProgramados implements JavaDelegate {
    private final Random random = new Random();
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean hayTrabajosProgramados = random.nextBoolean();
        if (hayTrabajosProgramados){
            execution.setVariable("hayTrabajosProgramados", true);
            System.out.println("hayTrabajosProgramados = true");
        }
        else {
            execution.setVariable("hayTrabajosProgramados", false);
            System.out.println("hayTrabajosProgramados = false");
        }
    }
}
