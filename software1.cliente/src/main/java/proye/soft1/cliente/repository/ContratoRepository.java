package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proye.soft1.cliente.model.Contrato;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    List<Contrato> findByClienteId(Long clienteId);
    List<Contrato> findByClienteDocumento(String documento);
}
