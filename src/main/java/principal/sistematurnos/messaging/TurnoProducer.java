package principal.sistematurnos.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import principal.sistematurnos.model.Turno;
import principal.sistematurnos.service.simulador.rabbit.RabbitMQConfig;

@Component
public class TurnoProducer {

    private final RabbitTemplate rabbitTemplate;

    public TurnoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarTurnoCreado(Turno turno) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, turno);
    }
}
