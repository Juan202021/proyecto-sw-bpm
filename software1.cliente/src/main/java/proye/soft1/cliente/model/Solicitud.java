package proye.soft1.cliente.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoSolicitud; // revisión de factura, reporte de pago, solicitud de reconexión, reporte de error en medidor, actualización de datos, solicitud de soporte
    private Long idCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private Long idContrato;
    @Column(length = 1000)
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private String estado; // radicada, en revisión, aprobada, rechazada, finalizada, cancelada
    private String archivosAdjuntos;
    
    // Response fields
    @Column(length = 1000)
    private String respuesta;
    private LocalDateTime fechaRespuesta;
    private String funcionarioResponsable;
    @Column(length = 1000)
    private String observaciones;
}
