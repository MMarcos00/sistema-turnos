package principal.sistematurnos.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaAtencion;
    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private principal.sistematurnos.model.Cliente  cliente;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "turno_id")
    private Turno turno;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public principal.sistematurnos.model.Cliente  getCliente() {
        return cliente;
    }

    public void setCliente(principal.sistematurnos.model.Cliente  cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }
}