package principal.sistematurnos.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import principal.sistematurnos.model.Turno;
import principal.sistematurnos.repository.TurnoRepository;

@Component
public class TurnoListener {

    private final TurnoRepository turnoRepository;

    public TurnoListener(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @RabbitListener(queues = "turnos.queue")
    public void recibirTurno(Turno turno) {
        System.out.println("📥 Turno recibido desde RabbitMQ: " + turno);

        // Guardar el turno en la base de datos
        turnoRepository.save(turno);
    }
}
