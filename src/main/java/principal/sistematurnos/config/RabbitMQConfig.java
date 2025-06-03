package umg.principal.sistematurno.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "turno_nuevo";

    @Bean
    public Queue turnoQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}