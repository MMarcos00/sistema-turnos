package principal.sistematurnos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import principal.sistematurnos.model.Turno;
import principal.sistematurnos.model.Historial;
import principal.sistematurnos.repository.TurnoRepository;
import principal.sistematurnos.structure.ColaTurnos;
import principal.sistematurnos.structure.HistorialLista;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceTurno {

    private final ColaTurnos cola;
    private final HistorialLista historial;
    private final TurnoRepository turnoRepository;
    private final TurnoProducer turnoProducer;

    @Autowired
    public ServiceTurno(TurnoRepository turnoRepository, TurnoProducer turnoProducer) {
        this.cola = new ColaTurnos();
        this.historial = new HistorialLista();
        this.turnoRepository = turnoRepository;
        this.turnoProducer = turnoProducer;
    }

    // POST /tareas
    public Turno crearTurno(Turno turno) {
        cola.agregarTurno(turno); // estructura de datos
        Turno guardado = turnoRepository.save(turno); // persistencia
        turnoProducer.enviarTurno(guardado); // 👉 enviar mensaje a RabbitMQ
        return guardado;
    }

    // GET /tareas
    public List<Turno> listarTurnos() {
        return turnoRepository.findAll();
    }

    // PUT /tareas/{id}
    public Turno actualizarTurno(Long id, Turno nuevoTurno) {
        Optional<Turno> op = turnoRepository.findById(id);
        if (op.isPresent()) {
            Turno existente = op.get();
            existente.setDescripcion(nuevoTurno.getDescripcion());
            existente.setCompletada(nuevoTurno.isCompletada());
            existente.setFechaProgramada(nuevoTurno.getFechaProgramada());
            return turnoRepository.save(existente);
        } else {
            throw new RuntimeException("Turno no encontrado con id: " + id);
        }
    }

    // DELETE /tareas/{id}
    public void eliminarTurno(Long id) {
        turnoRepository.deleteById(id);
    }

    // Simular atención
    public void atenderTurno() {
        Turno turno = cola.obtenerSiguienteTurno();
        if (turno != null) {
            Historial h = new Historial();
            h.setTurno(turno);
            h.setFechaAtencion(LocalDateTime.now());
            historial.agregarRegistro(h);
        }
    }

    public void verEstado() {
        System.out.println("Turnos en espera: " + cola.tamaño());
        System.out.println("Historial de atención: " + historial.tamaño());
    }

    public List<Historial> obtenerHistorialSimulado() {
        return historial.getTodos();
    }
}
