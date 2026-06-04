package com.proyecto.gasCorocora.service;

import com.proyecto.gasCorocora.model.Reporte;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.*;


public class RegistrarFalla implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        boolean datosPresionOK, datosCaudalOK, datosTemperaturaOK, datosComunicacionEquiposOK;

        datosPresionOK = (boolean) execution.getVariable("datosPresionOK");
        datosCaudalOK = (boolean) execution.getVariable("datosCaudalOK");
        datosTemperaturaOK = (boolean) execution.getVariable("datosTemperaturaOK");
        datosComunicacionEquiposOK = (boolean) execution.getVariable("datosComunicacionEquiposOK");

        List<Reporte> datosPresionIrregulares = (List<Reporte>) execution.getVariable("datosPresionIrregulares");
        List<Reporte> datosCaudalIrregulares = (List<Reporte>) execution.getVariable("datosCaudalIrregulares");
        List<Reporte> datosTemperaturaIrregulares = (List<Reporte>) execution.getVariable("datosTemperaturaIrregulares");
        List<Reporte> datosComunicacionEquiposIrregulares = (List<Reporte>) execution.getVariable("datosComunicacionEquiposIrregulares");

        List<Reporte> datosCompactados = new ArrayList<>();
        datosCompactados = compactar(
                datosPresionIrregulares,
                datosCaudalIrregulares,
                datosTemperaturaIrregulares,
                datosComunicacionEquiposIrregulares,
                datosPresionOK,
                datosCaudalOK,
                datosTemperaturaOK,
                datosComunicacionEquiposOK
        );

        System.out.println("\n# RegistrarFalla");

        execution.setVariable("reportes", datosCompactados);
        System.out.println(datosCompactados);
//        RuntimeService runtimeService = execution.getProcessEngineServices()
//                .getRuntimeService();
//
//        runtimeService.createSignalEvent("nombreDeLaSeñal")
//                .setVariables(Map.of("reportes", reportes))
//                .send();
    }

    public List<Reporte> compactar(
            List<Reporte> datosPresionIrregulares,
            List<Reporte> datosCaudalIrregulares,
            List<Reporte> datosTemperaturaIrregulares,
            List<Reporte> datosComunicacionEquiposIrregulares,
            boolean datosPresionOK,
            boolean datosCaudalOK,
            boolean datosTemperaturaOK,
            boolean datosComunicacionEquiposOK)
    {
        List<Reporte> compactado = new ArrayList<>();
        Set<Reporte> set = new LinkedHashSet<>(compactado);
        if (Boolean.FALSE.equals(datosPresionOK)) set.addAll(datosPresionIrregulares);
        if (Boolean.FALSE.equals(datosCaudalOK)) set.addAll(datosCaudalIrregulares);
        if (Boolean.FALSE.equals(datosTemperaturaOK)) set.addAll(datosTemperaturaIrregulares);
        if (Boolean.FALSE.equals(datosComunicacionEquiposOK)) set.addAll(datosComunicacionEquiposIrregulares);

        compactado = new ArrayList<>(set);
        return compactado;
    }
}
