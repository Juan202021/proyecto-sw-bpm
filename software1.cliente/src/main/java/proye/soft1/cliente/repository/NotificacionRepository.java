package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import proye.soft1.cliente.model.Notificacion;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByClienteIdOrderByFechaDesc(Long clienteId);
}
