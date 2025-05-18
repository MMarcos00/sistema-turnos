package principal.sistematurnos.estructura;

import principal.sistematurnos.model.Turno;
import java.util.LinkedList;
import java.util.Queue;

public class ColaDeTurnos {
    private final Queue<Turno> cola;

    public ColaDeTurnos() {
        this.cola = new LinkedList<>();
    }

    public void agregarTurno(Turno turno) {
        cola.offer(turno);
    }

    public Turno obtenerSiguienteTurno() {
        return cola.poll();
    }

    public Turno verSiguienteTurno() {
        return cola.peek();
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }

    public int tamaño() {
        return cola.size();
    }
}
