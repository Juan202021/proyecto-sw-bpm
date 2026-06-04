import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component("agendarInstalacionDelegate")
public class AgendarInstalacionDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        LocalDateTime fechaInstalacion = calcularFechaInstalacion(LocalTime.of(10, 0));

        execution.setVariable("fechaInstalacion", fechaInstalacion.toString());
        execution.setVariable("estadoInstalacion", "AGENDADA");
        execution.setVariable("mensajeInstalacion", "Instalación agendada automáticamente para " + fechaInstalacion);
    }

    private LocalDateTime calcularFechaInstalacion(LocalTime hora) {
        LocalDate fecha = LocalDate.now().plusDays(2);

        while (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1);
        }

        return LocalDateTime.of(fecha, hora);
    }
}
