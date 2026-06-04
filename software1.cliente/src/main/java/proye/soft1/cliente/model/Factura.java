package proye.soft1.cliente.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    private Double monto;
    private Double montoDeuda;
    private LocalDate fechaLimite;
    private String estado; // PENDIENTE, PAGADA, VENCIDA
    private Integer diasMora;
}
