package principal.sistematurnos.structure;



import principal.sistematurnos.model.Historial;

import java.util.LinkedList;
import java.util.List;

public class HistorialLista{

    private final List<Historial> historial;

    public HistorialLista() {

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

    public List<Historial> getTodos() {
        return historial;
    }
}