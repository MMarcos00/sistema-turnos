package principal.sistematurnos.service.simulador;

import principal.sistematurnos.estructura.ColaDeTurnos;
import principal.sistematurnos.estructura.ListaHistorial;
import principal.sistematurnos.model.Turno;
import principal.sistematurnos.model.Historial;

import java.time.LocalDateTime;

public class SimuladorTurnosService {
    private final ColaDeTurnos cola;
    private final ListaHistorial historial;

    public SimuladorTurnosService() {
        this.cola = new ColaDeTurnos();
        this.historial = new ListaHistorial();
    }

    public void crearTurno(Turno turno) {
        cola.agregarTurno(turno);
    }

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
}
