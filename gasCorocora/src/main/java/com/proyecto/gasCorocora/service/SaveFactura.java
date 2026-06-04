package com.example.camunda_java_spring_boot;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Component("saveFactura")
public class SaveFactura implements JavaDelegate {

   @Override
   public void execute(DelegateExecution execution) throws Exception {
       // Obtener la información del modelo BPMN
       String clienteId = (String) execution.getVariable("clienteId");
       Number consumo = (Number) execution.getVariable("consumo");
       Number tarifa = (Number) execution.getVariable("tarifa");
       Object fecha = execution.getVariable("fecha");
       String tipoLectura = (String) execution.getVariable("tipoLectura");
       String categoriaCliente = (String) execution.getVariable("categoriaCliente");
       String resultado = (String) execution.getVariable("resultado");
       Number subsidio = (Number) execution.getVariable("subsidio");
       String alerta = (String) execution.getVariable("alerta");
       Number totalFactura = (Number) execution.getVariable("totalFactura");
       Number totalDescuento = (Number) execution.getVariable("totalDescuento");
       String respuestaFin = (String) execution.getVariable("respuestaFin");
       Object aprobarFactura = execution.getVariable("aprobarFactura");

       // Crear un mapa para almacenar la información de la factura
       Map<String, Object> factura = new HashMap<>();
       factura.put("clienteId", clienteId);
       factura.put("consumo", consumo);
       factura.put("tarifa", tarifa);
       factura.put("fecha", fecha);
       factura.put("tipoLectura", tipoLectura);
       factura.put("categoriaCliente", categoriaCliente);
       factura.put("resultado", resultado);
       factura.put("subsidio", subsidio);
       factura.put("alerta", alerta);
       factura.put("totalFactura", totalFactura);
       factura.put("totalDescuento", totalDescuento);
       factura.put("respuestaFin", respuestaFin);
       factura.put("aprobarFactura", aprobarFactura);

       // Recorrer el mapa e imprimir cada clave y valor
       for (Map.Entry<String, Object> entry : factura.entrySet()) {
           System.out.println(entry.getKey() + ": " + entry.getValue());
       }
   }
}
