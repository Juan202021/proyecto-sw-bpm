package com.proyecto.gasCorocora.service;

import com.proyecto.gasCorocora.model.Reporte;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidarTemperatura implements JavaDelegate {
    private final Double minTemperatura = 0.1;
    private final Double maxTemperatura = 5000.0;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Reporte> datosPorDireccion = (List<Reporte>) execution.getVariable("datosPorDireccion");
        List<Reporte> datosIrregulares;
        datosIrregulares = validarTemperatura(datosPorDireccion);
        if (datosIrregulares.isEmpty()){
            execution.setVariable("datosTemperaturaOK", true);
        }
        else {
            execution.setVariable("datosTemperaturaOK", false);
            execution.setVariable("datosTemperaturaIrregulares", datosIrregulares);
        }
        System.out.println("\n#ValidarTemperatura");
    }

    public List<Reporte> validarTemperatura(List<Reporte> datosPorDireccion){
        List<Reporte> valoresIrregulares = new ArrayList<>();
        if (datosPorDireccion != null) {
            datosPorDireccion.forEach(
                    (reporte) -> {
                        if (reporte.getTemperatura() < minTemperatura || reporte.getTemperatura() > maxTemperatura) {
                            valoresIrregulares.add(reporte);
                        }
                    }
            );
        }
        return valoresIrregulares;
    }
}
