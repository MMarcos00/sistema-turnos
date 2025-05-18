package principal.sistematurnos.estructura;

import principal.sistematurnos.model.Historial;
import java.util.LinkedList;
import java.util.List;

public class ListaHistorial {
    private final List<Historial> historial;

    public ListaHistorial() {
        this.historial = new LinkedList<>();
    }

    public void agregarRegistro(Historial h) {
        historial.add(h);
    }

    public List<Historial> obtenerHistorial() {
        return historial;
    }

    public int tamaño() {
        return historial.size();
    }
}
