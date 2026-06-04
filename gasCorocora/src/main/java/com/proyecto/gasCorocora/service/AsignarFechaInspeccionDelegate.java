import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component("asignarFechaInspeccionDelegate")
public class AsignarFechaInspeccionDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        LocalDateTime fechaInspeccion = calcularSiguienteDiaHabilALas(LocalTime.of(9, 0));

        execution.setVariable("fechaInspeccion", fechaInspeccion.toString());
        execution.setVariable("estadoInspeccion", "AGENDADA");
        execution.setVariable("mensajeInspeccion", "Inspección agendada automáticamente para " + fechaInspeccion);
    }

    private LocalDateTime calcularSiguienteDiaHabilALas(LocalTime hora) {
        LocalDate fecha = LocalDate.now().plusDays(1);

        while (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1);
        }

        return LocalDateTime.of(fecha, hora);
    }
}
