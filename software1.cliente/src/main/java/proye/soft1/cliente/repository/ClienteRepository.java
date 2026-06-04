package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proye.soft1.cliente.model.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByDocumento(String documento);
}
