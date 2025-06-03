package principal.sistematurnos.structure;


import principal.sistematurnos.model.Servicio;

import java.util.ArrayList;
import java.util.List;

public class ServiciosArbol {

    public static class Nodo {
        private Servicio servicio;
        private List<Nodo> subcategorias;

        public Nodo(Servicio servicio) {
            this.servicio = servicio;
            this.subcategorias = new ArrayList<>();
        }

        public void agregarSubcategoria(Nodo nodo) {
            subcategorias.add(nodo);
        }

        public Servicio getServicio() {
            return servicio;
        }

        public List<Nodo> getSubcategorias() {
            return subcategorias;
        }
    }

    private Nodo raiz;

    public ServiciosArbol(Servicio servicioRaiz) {
        this.raiz = new Nodo(servicioRaiz);
    }

    public Nodo getRaiz() {
        return raiz;
    }
}
