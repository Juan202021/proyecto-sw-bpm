package proye.soft1.cliente.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
