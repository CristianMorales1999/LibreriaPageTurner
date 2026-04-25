package com.pageturner.model;
import java.time.LocalDateTime;

public class Venta {

    private int id;
    private LocalDateTime fecha;
    private int cantidad;
    private Cliente cliente;
    private Libro libro;

    public Venta(int id, int cantidad, Cliente cliente, Libro libro) {

        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad inválida");
        if (cliente == null || libro == null) throw new IllegalArgumentException("Datos inválidos");
        if (!libro.estaDisponible() || libro.getStock() < cantidad)
            throw new IllegalStateException("No hay stock suficiente");

        this.id = id;
        this.fecha = LocalDateTime.now();
        this.cantidad = cantidad;
        this.cliente = cliente;
        this.libro = libro;

        // Lógica automática
        libro.disminuirStock(cantidad);
        libro.agregarVenta(this);
        cliente.agregarVenta(this);
    }

    public double calcularTotal() {
        return cantidad * libro.getPrecio();
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public int getCantidad() { return cantidad; }
    public Cliente getCliente() { return cliente; }
    public Libro getLibro() { return libro; }
}