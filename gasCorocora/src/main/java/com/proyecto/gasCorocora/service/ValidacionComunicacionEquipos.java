package com.proyecto.gasCorocora.service;

import com.proyecto.gasCorocora.model.Reporte;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;

public class ValidacionComunicacionEquipos implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        List<Reporte> datosPorDireccion = (List<Reporte>) execution.getVariable("datosPorDireccion");
        List<Reporte> datosIrregulares;
        datosIrregulares = validarComunicacionEquipos(datosPorDireccion);
        if (datosIrregulares.isEmpty()){
            execution.setVariable("datosComunicacionEquiposOK", true);
        }
        else {
            execution.setVariable("datosComunicacionEquiposOK", false);
            execution.setVariable("datosComunicacionEquiposIrregulares", datosIrregulares);
        }
        System.out.println("\n#ValidacionComunicacionEquipos");
    }

    public List<Reporte> validarComunicacionEquipos(List<Reporte> datosPorDireccion){
        List<Reporte> valoresIrregulares = new ArrayList<>();
        if (datosPorDireccion != null) {
            datosPorDireccion.forEach(
                    (reporte) -> {
                        if (Objects.equals(reporte.isComunicacionEquiposMedicionActiva(), false)) {
                            valoresIrregulares.add(reporte);
                        }
                    }
            );
        }
        return valoresIrregulares;
    }
}
