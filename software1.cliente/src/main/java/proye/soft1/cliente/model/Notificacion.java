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
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String tipo; // factura generada, mora, posible suspensión, suspensión realizada, pago confirmado, reconexión en proceso, reconexión realizada
    private String mensaje;
    private LocalDateTime fecha;
    
    // Additional details
    private Long idContrato;
    private Double montoDeuda;
    private Integer diasMora;
    private String fechaLimitePago;
    private String estadoSuspension;
}
