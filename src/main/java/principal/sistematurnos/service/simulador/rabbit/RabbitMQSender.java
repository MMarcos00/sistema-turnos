package principal.sistematurnos.service.simulador.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import principal.sistematurnos.model.Turno;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarTurnoCreado(Turno turno) {
        rabbitTemplate.convertAndSend("turnos.exchange", "turno.creado", turno);
    }
}
