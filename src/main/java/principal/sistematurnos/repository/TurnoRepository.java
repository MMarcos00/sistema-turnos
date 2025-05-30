package principal.sistematurnos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import principal.sistematurnos.model.Turno;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
}
