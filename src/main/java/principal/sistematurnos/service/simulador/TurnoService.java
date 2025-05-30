package principal.sistematurnos.service.simulador;

import org.springframework.stereotype.Service;
import principal.sistematurnos.model.Turno;
import principal.sistematurnos.repository.TurnoRepository;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@Service
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final Queue<Turno> colaTurnos = new LinkedList<>();

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public Turno crearTurno(Turno turno) {
        Turno nuevoTurno = turnoRepository.save(turno);
        colaTurnos.add(nuevoTurno); // Agrega a la cola FIFO
        return nuevoTurno;
    }

    public Turno obtenerSiguienteTurno() {
        Turno turno = colaTurnos.poll(); // Atiende el siguiente turno (FIFO)
        return turno;
    }

    public void cancelarTurno(Long id) {
        Optional<Turno> turnoOptional = turnoRepository.findById(id);
        turnoOptional.ifPresent(turno -> {
            colaTurnos.remove(turno); // Remueve de la cola si está
            turnoRepository.delete(turno); // Elimina de la base
        });
    }

    public void guardarTurnoRecibido(Turno turno) {
        turnoRepository.save(turno);
    }

}
