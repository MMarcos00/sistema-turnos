package principal.sistematurnos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import principal.sistematurnos.model.Turno;
import principal.sistematurnos.service.ServiceTurno;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private ServiceTurno service;

    @GetMapping("/crear")
    public Turno crearTurnoDesdeNavegador() {
        Turno t = new Turno();
        t.setDescripcion("Turno creado por Marcos desde navegador");
        t.setCompletada(false);
        t.setFechaProgramada(LocalDate.now().plusDays(1));
        return service.crearTurno(t);
    }

    @GetMapping
    public List<Turno> listar() {
        return service.listarTurnos();
    }

    @GetMapping("/atender")
    public String atender() {
        service.atenderTurno();
        return "✅ Turno atendido (si había)";
    }

    @GetMapping("/estado")
    public String estado() {
        service.verEstado();
        return "📊 Estado mostrado en consola";
    }

    @GetMapping("/historial")
    public Object historial() {
        return service.obtenerHistorialSimulado();
    }
}
