package principal.sistematurnos.service;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import principal.sistematurnos.model.Turno;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import umg.principal.sistematurno.config.RabbitMQConfig;

@Service
public class TurnoProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public TurnoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void enviarTurno(Turno turno) {
        try {
            String mensaje = objectMapper.writeValueAsString(turno);
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, mensaje);
            System.out.println("📤 Mensaje enviado a RabbitMQ: " + mensaje);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
