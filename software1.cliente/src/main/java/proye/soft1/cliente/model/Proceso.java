package proye.soft1.cliente.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Proceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado; // PENDIENTE, EN_PROCESO, COMPLETADO

    @ManyToOne
    @JoinColumn(name = "propiedad_id")
    private Propiedad propiedad;
}
