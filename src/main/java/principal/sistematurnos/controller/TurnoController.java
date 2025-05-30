package principal.sistematurnos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.sistematurnos.model.Turno;
import principal.sistematurnos.service.simulador.TurnoService;


@RestController
@RequestMapping("/turno")
public class TurnoController {

    private final TurnoService turnoService;

    public TurnoController(TurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<Turno> crearTurno(@RequestBody Turno turno) {
        Turno nuevoTurno = turnoService.crearTurno(turno);
        return ResponseEntity.ok(nuevoTurno);
    }

    @GetMapping("/siguiente")
    public ResponseEntity<Turno> atenderSiguienteTurno() {
        Turno turno = turnoService.obtenerSiguienteTurno();
        if (turno != null) {
            return ResponseEntity.ok(turno);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarTurno(@PathVariable Long id) {
        turnoService.cancelarTurno(id);
        return ResponseEntity.noContent().build();
    }


}
