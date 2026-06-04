package proye.soft1.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import proye.soft1.cliente.model.Proceso;

import java.util.List;

public interface ProcesoRepository extends JpaRepository<Proceso, Long> {
    @Query("SELECT p FROM Proceso p WHERE p.propiedad.cliente.id = :clienteId")
    List<Proceso> findByClienteId(@Param("clienteId") Long clienteId);
}
