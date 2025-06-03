package principal.sistematurnos.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private boolean completada;
    private LocalDate fechaProgramada;
    private LocalDateTime fechaCreacion;
    private boolean atendido;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private principal.sistematurnos.model.Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private principal.sistematurnos.model.Servicio  servicio;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public LocalDate getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDate fechaProgramada) {
        this.fechaProgramada = LocalDate.from(fechaProgramada.atStartOfDay());
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public principal.sistematurnos.model.Cliente  getCliente() {
        return cliente;
    }

    public void setCliente(principal.sistematurnos.model.Cliente  cliente) {
        this.cliente = cliente;
    }

    public principal.sistematurnos.model.Servicio  getServicio() {
        return servicio;
    }

    public void setServicio(principal.sistematurnos.model.Servicio  servicio) {
        this.servicio = servicio;
    }
}

