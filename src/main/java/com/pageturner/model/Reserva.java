package com.pageturner.model;
import java.time.LocalDateTime;

public class Reserva {

    private int id;
    private LocalDateTime fecha;
    private EstadoReserva estado;
    private Cliente cliente;
    private Libro libro;

    public Reserva(int id, Cliente cliente, Libro libro) {

        if (cliente == null || libro == null)
            throw new IllegalArgumentException("Datos inválidos");

        if (libro.estaDisponible())
            throw new IllegalStateException("No se puede reservar un libro con stock disponible");

        this.id = id;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoReserva.PENDIENTE;
        this.cliente = cliente;
        this.libro = libro;

        libro.agregarReserva(this);
        cliente.agregarReserva(this);
    }

    public boolean esActiva() {
        return estado == EstadoReserva.PENDIENTE || estado == EstadoReserva.HABILITADA;
    }

    public void cambiarEstado(EstadoReserva nuevoEstado) {
        if (nuevoEstado == null) throw new IllegalArgumentException("Estado inválido");
        this.estado = nuevoEstado;
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public EstadoReserva getEstado() { return estado; }
}