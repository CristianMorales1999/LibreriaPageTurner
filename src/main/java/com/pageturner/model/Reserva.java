package com.pageturner.model;
import java.time.LocalDateTime;

public class Reserva {

    private int id;
    private LocalDateTime fecha;
    private EstadoReserva estado;
    private Cliente cliente;
    private Libro libro;

    private void validarDatos(Cliente cliente, Libro libro) {

        if (cliente == null)
            throw new IllegalArgumentException("Cliente obligatorio");

        if (libro == null)
            throw new IllegalArgumentException("Libro obligatorio");

        if (libro.estaDisponible())
            throw new IllegalStateException("No se puede reservar con stock disponible");
    }

    public Reserva(int id, LocalDateTime fecha, EstadoReserva estado, Cliente cliente, Libro libro) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.cliente = cliente;
        this.libro = libro;
    }
    public Reserva(Cliente cliente, Libro libro) {

        validarDatos(cliente, libro);
        this.fecha = LocalDateTime.now();
        this.estado = EstadoReserva.PENDIENTE;
        this.cliente = cliente;
        this.libro = libro;

        // Lógica de negocio
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
    public Cliente getCliente() { return cliente; }
    public Libro getLibro() { return libro; }
}