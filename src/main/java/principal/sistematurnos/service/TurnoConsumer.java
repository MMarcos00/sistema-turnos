package principal.sistematurnos.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import principal.sistematurnos.model.Turno;

@Service
public class TurnoConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @RabbitListener(queues = "turno_nuevo")
    public void recibirMensaje(String mensajeJson) {
        try {
            Turno turno = objectMapper.readValue(mensajeJson, Turno.class);
            System.out.println("📥 Turno recibido:");
            System.out.println("🆔 ID: " + turno.getId());
            System.out.println("📝 Descripción: " + turno.getDescripcion());
            System.out.println("📅 Fecha programada: " + turno.getFechaProgramada());
            System.out.println("✅ Completado: " + turno.isCompletada());
        } catch (Exception e) {
            System.err.println("❌ Error al deserializar mensaje: " + e.getMessage());
        }
    }
}
