package com.proyecto.gasCorocora.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class CalificarInformacion implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean datosPresionOK, datosCaudalOK, datosTemperaturaOK, datosComunicacionEquiposOK;
        datosPresionOK = (boolean) execution.getVariable("datosPresionOK");
        datosCaudalOK = (boolean) execution.getVariable("datosCaudalOK");
        datosTemperaturaOK = (boolean) execution.getVariable("datosTemperaturaOK");
        datosComunicacionEquiposOK = (boolean) execution.getVariable("datosComunicacionEquiposOK");

        System.out.println("\n# CalificarInformacion");

        if (Boolean.TRUE.equals(datosPresionOK) &&
            Boolean.TRUE.equals(datosCaudalOK) &&
            Boolean.TRUE.equals(datosTemperaturaOK) &&
            Boolean.TRUE.equals(datosComunicacionEquiposOK)){

            execution.setVariable("valoresNormales", true);
            System.out.println("valoresNormales = true");
        } else {
            execution.setVariable("valoresNormales", false);
            System.out.println("valoresNormales = false");
        }

    }
}
