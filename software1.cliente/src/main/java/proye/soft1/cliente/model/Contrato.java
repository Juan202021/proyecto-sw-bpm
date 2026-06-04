package proye.soft1.cliente.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String estadoServicio; // activo, en mora, en riesgo de suspensión, suspendido, en proceso de reconexión, reconectado, cancelado
    private String medidor;
    private String categoriaCliente;
    private String operador;
    private String observaciones;
}
