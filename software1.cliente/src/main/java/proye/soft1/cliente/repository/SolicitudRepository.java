package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proye.soft1.cliente.model.Solicitud;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByIdClienteOrderByFechaCreacionDesc(Long idCliente);
}
