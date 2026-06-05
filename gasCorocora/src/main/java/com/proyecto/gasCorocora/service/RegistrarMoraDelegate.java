package com.proyecto.gasCorocora.service;

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
        String idContrato = String.valueOf(
            execution.getVariable("idContrato")
        );

        int diasMora = Integer.parseInt(
            String.valueOf(
                execution.getVariable("diasMora")
            )
        );

        int montoDeuda = Integer.parseInt(
            String.valueOf(
                execution.getVariable("montoDeuda")
            )
        );


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