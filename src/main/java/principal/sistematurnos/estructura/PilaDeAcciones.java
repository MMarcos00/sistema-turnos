package principal.sistematurnos.estructura;

import java.util.Stack;

public class PilaDeAcciones {
    private final Stack<String> pila;

    public PilaDeAcciones() {
        this.pila = new Stack<>();
    }

    public void registrarAccion(String accion) {
        pila.push(accion);
    }

    public String deshacerUltimaAccion() {
        return pila.isEmpty() ? null : pila.pop();
    }

    public boolean estaVacia() {
        return pila.isEmpty();
    }

    public int tamaño() {
        return pila.size();
    }
}
