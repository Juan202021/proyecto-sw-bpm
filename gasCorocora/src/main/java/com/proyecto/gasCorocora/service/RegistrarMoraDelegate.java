package com.example.demo;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.HashMap;

@Component("registrarMoraDelegate")
public class RegistrarMoraDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        // Obtener variables del formulario
        String idContrato = (String) execution.getVariable("idContrato");
        int diasMora = (int) execution.getVariable("diasMora");
        int montoDeuda = (int) execution.getVariable("montoDeuda");


        Map<String, Object> mora = new HashMap<>();
        mora.put("ID Contrato", idContrato);
        mora.put("Días Mora", diasMora);
        mora.put("Monto Deuda", montoDeuda);

        System.out.println("=== REGISTRO DE MORA ===");

        for (Map.Entry<String, Object> entry : mora.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        execution.setVariable("moraRegistrada", mora);
    }
}