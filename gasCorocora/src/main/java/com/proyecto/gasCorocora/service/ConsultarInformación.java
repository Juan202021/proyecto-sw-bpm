package com.proyecto.gasCorocora.service;

import com.proyecto.gasCorocora.model.Reporte;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.*;


public class ConsultarInformación implements JavaDelegate {
    private final int cantLimiteDatos = 100;
    private final Double minPresion = -10.0;
    private final Double maxPresion = 500.0;
    private final Double minCaudal = -10.0;
    private final Double maxCaudal = 6050.0;
    private final Double minTemp = -10.0;
    private final Double maxTemp = 50.0;
    private final Random random = new Random();
    private final List<Reporte> datosPorDireccion = new ArrayList<>();
    public static final String[] variables = {"presion", "caudal", "temperatura", "comunicacionEquiposMedicion"};
    public static final String[] direcciones = {
            "Calle 12 #45-67",
            "Carrera 8 #23-19",
            "Avenida Central #101-44",
            "Calle 5 #18-90",
            "Carrera 14 #76-21",
            "Transversal 9 #33-12",
            "Calle 40 #22-55",
            "Avenida Libertad #88-30",
            "Carrera 19 #11-73",
            "Calle 67 #54-10",
            "Diagonal 15 #90-18",
            "Calle 72 #16-80",
            "Carrera 25 #40-11",
            "Avenida Norte #19-66",
            "Calle 3 #7-42",
            "Carrera 31 #58-77",
            "Calle 81 #20-15",
            "Avenida Sur #99-45",
            "Carrera 4 #13-29",
            "Calle 55 #70-08",
            "Transversal 21 #34-90",
            "Calle 10 #50-60",
            "Carrera 6 #44-32",
            "Avenida Los Pinos #27-18",
            "Calle 28 #14-93",
            "Carrera 17 #89-11",
            "Calle 96 #30-27",
            "Avenida del Río #65-40",
            "Carrera 42 #17-22",
            "Calle 48 #80-14",
            "Diagonal 8 #12-64",
            "Calle 33 #25-51",
            "Carrera 11 #39-20",
            "Avenida Primavera #74-88",
            "Calle 90 #13-71",
            "Carrera 29 #48-17",
            "Calle 60 #21-95",
            "Avenida Esperanza #50-28",
            "Carrera 7 #15-39",
            "Calle 16 #63-84",
            "Transversal 3 #41-50",
            "Calle 85 #27-19",
            "Carrera 35 #92-61",
            "Avenida Horizonte #18-77",
            "Calle 23 #56-33",
            "Carrera 13 #20-09",
            "Calle 70 #49-58",
            "Avenida El Lago #100-16",
            "Carrera 2 #28-72",
            "Calle 58 #11-43",
            "Diagonal 19 #32-25",
            "Calle 44 #67-81",
            "Carrera 27 #10-55",
            "Avenida del Sol #39-90",
            "Calle 78 #24-13",
            "Carrera 5 #59-42",
            "Calle 31 #83-74",
            "Avenida Las Flores #14-65",
            "Carrera 21 #36-08",
            "Calle 99 #52-47",
            "Transversal 11 #26-91",
            "Calle 8 #71-20",
            "Carrera 16 #43-57",
            "Avenida Santa María #85-30",
            "Calle 52 #19-11",
            "Carrera 9 #64-82",
            "Calle 27 #45-73",
            "Avenida Colombia #33-24",
            "Carrera 38 #12-99",
            "Calle 74 #57-15",
            "Diagonal 6 #29-61",
            "Calle 39 #81-48",
            "Carrera 24 #18-05",
            "Avenida del Parque #97-37",
            "Calle 14 #53-88",
            "Carrera 32 #69-10",
            "Calle 88 #22-49",
            "Avenida Los Andes #41-16",
            "Carrera 10 #35-94",
            "Calle 63 #78-31",
            "Transversal 18 #20-62",
            "Calle 2 #46-27",
            "Carrera 15 #87-75",
            "Avenida El Bosque #54-19",
            "Calle 36 #60-44",
            "Carrera 28 #13-58",
            "Calle 94 #47-80",
            "Avenida Las Palmas #31-07",
            "Carrera 1 #72-56",
            "Calle 50 #17-34",
            "Diagonal 22 #38-92",
            "Calle 65 #84-12",
            "Carrera 20 #26-73",
            "Avenida San Martín #49-29",
            "Calle 18 #55-61",
            "Carrera 33 #91-40",
            "Calle 82 #23-14",
            "Avenida Central Oeste #68-57",
            "Carrera 12 #37-89",
            "Calle 46 #79-25"
    };

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        getDatos();
        execution.setVariable("datosPorDireccion", datosPorDireccion);
        System.out.println("\n######################################");
        System.out.println("# ConsultarInformación");
    }

    public void getDatos(){
        for (int i = 0; i < cantLimiteDatos; i++){
            Reporte reporte = new Reporte();
            reporte.setPresion(random.nextDouble(minPresion,maxPresion));
            reporte.setCaudal(random.nextDouble(minCaudal,maxCaudal));
            reporte.setTemperatura(random.nextDouble(minTemp,maxTemp));
            reporte.setComunicacionEquiposMedicionActiva(random.nextBoolean());
            reporte.setDomicilio(direcciones[i]);
            datosPorDireccion.add(reporte);
        }
    }
}
