package principal.sistematurnos.service.simulador.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import principal.sistematurnos.model.Turno;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "turnos.queue")
    public void recibirTurno(Turno turno) {
        System.out.println("Turno recibido desde RabbitMQ: " + turno.getId());
    }
}
