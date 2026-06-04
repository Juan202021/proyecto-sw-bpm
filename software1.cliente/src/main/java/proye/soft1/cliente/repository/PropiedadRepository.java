package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proye.soft1.cliente.model.Propiedad;

import java.util.List;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
    List<Propiedad> findByClienteId(Long clienteId);
}
