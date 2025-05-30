package principal.sistematurnos.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import principal.sistematurnos.model.Turno;
import principal.sistematurnos.service.simulador.TurnoService;


@Component
public class TurnoConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TurnoConsumer.class);
    private final TurnoService turnoService;

    public TurnoConsumer(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @RabbitListener(queues = principal.sistematurnos.service.simulador.rabbit.RabbitMQConfig.QUEUE)
    public void recibirTurnoCreado(Turno turno) {
        logger.info("🟢 Evento recibido: Turno creado -> {}", turno);

        // Validación simple: verificar que tenga cliente y servicio asociados
        if (turno.getCliente() == null || turno.getServicio() == null) {
            logger.warn("⚠️ Turno recibido sin cliente o servicio. Se ignora.");
            return;
        }

        // Lógica adicional: registrar en la base de datos (si fuera necesario)
        try {
            turnoService.guardarTurnoRecibido(turno);
            logger.info("✅ Turno procesado y guardado correctamente.");
        } catch (Exception e) {
            logger.error("❌ Error al procesar el turno recibido: {}", e.getMessage());
        }
    }
}
