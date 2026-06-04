package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proye.soft1.cliente.model.Factura;

import java.util.List;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByContratoId(Long contratoId);
}
