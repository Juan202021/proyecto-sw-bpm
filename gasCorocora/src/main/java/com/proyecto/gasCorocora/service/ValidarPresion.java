package com.proyecto.gasCorocora.service;

import com.proyecto.gasCorocora.model.Reporte;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidarPresion implements JavaDelegate {
    private final Double minPresion = 2.0;
    private final Double maxPresion = 400.0;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Reporte> datosPorDireccion = (List<Reporte>) execution.getVariable("datosPorDireccion");
        List<Reporte> datosIrregulares;
        datosIrregulares = validarPresion(datosPorDireccion);
        if (datosIrregulares.isEmpty()){
            execution.setVariable("datosPresionOK", true);
        }
        else {
            execution.setVariable("datosPresionOK", false);
            execution.setVariable("datosPresionIrregulares", datosIrregulares);
        }
        System.out.println("\n#ValidarPresion");
    }

    public List<Reporte> validarPresion(List<Reporte> datosPorDireccion){
        List<Reporte> valoresIrregulares = new ArrayList<>();
        if (datosPorDireccion != null) {
            datosPorDireccion.forEach(
                    (reporte) -> {
                        if (reporte.getPresion() < minPresion || reporte.getPresion() > maxPresion) {
                            valoresIrregulares.add(reporte);
                        }
                    }
            );
        }
        return valoresIrregulares;
    }
}
