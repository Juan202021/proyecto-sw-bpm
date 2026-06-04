package com.proyecto.gasCorocora.service;

import com.proyecto.gasCorocora.model.Reporte;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidarCaudal implements JavaDelegate {
    private final Double minCaudal = 0.1;
    private final Double maxCaudal = 5000.0;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Reporte> datosPorDireccion = (List<Reporte>) execution.getVariable("datosPorDireccion");
        List<Reporte> datosIrregulares;
        datosIrregulares = validarCaudal(datosPorDireccion);
        if (datosIrregulares.isEmpty()){
            execution.setVariable("datosCaudalOK", true);
        }
        else {
            execution.setVariable("datosCaudalOK", false);
            execution.setVariable("datosCaudalIrregulares", datosIrregulares);
        }
        System.out.println("\n#ValidarCaudal");
    }

    public List<Reporte> validarCaudal(List<Reporte> datosPorDireccion){
        List<Reporte> valoresIrregulares = new ArrayList<>();
        if (datosPorDireccion != null) {
            datosPorDireccion.forEach(
                    (reporte) -> {
                        if (reporte.getCaudal() < minCaudal || reporte.getCaudal() > maxCaudal) {
                            valoresIrregulares.add(reporte);
                        }
                    }
            );
        }
        return valoresIrregulares;
    }
}
