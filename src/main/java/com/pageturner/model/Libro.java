package com.pageturner.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String ISBN;
    private double precio;
    private int stock;

    private List<Venta> ventas;
    private List<Reserva> reservas;

    private void inicializarListas() {
        this.ventas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }
    private void validarDatos(String titulo, String autor, String ISBN, double precio, int stock) {

        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Título obligatorio");

        if (autor == null || autor.isBlank())
            throw new IllegalArgumentException("Autor obligatorio");

        if (ISBN == null || ISBN.isBlank())
            throw new IllegalArgumentException("ISBN obligatorio");

        if (precio <= 0)
            throw new IllegalArgumentException("Precio debe ser mayor a 0");

        if (stock < 0)
            throw new IllegalArgumentException("Stock no puede ser negativo");
    }

    public Libro(int id, String titulo, String autor, String ISBN, double precio, int stock) {
        validarDatos(titulo, autor, ISBN, precio, stock);
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.precio = precio;
        this.stock = stock;
        inicializarListas();
    }
    public Libro(String titulo, String autor, String ISBN, double precio, int stock) {
        validarDatos(titulo, autor, ISBN, precio, stock);
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.precio = precio;
        this.stock = stock;
        inicializarListas();
    }

    public void disminuirStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad inválida");
        if (cantidad > stock) throw new IllegalStateException("Stock insuficiente");
        stock -= cantidad;
    }

    public void aumentarStock(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad inválida");
        stock += cantidad;
    }

    public boolean estaDisponible() {
        return stock > 0;
    }

    public double calcularVentasTotales() {
        return ventas.stream().mapToDouble(Venta::calcularTotal).sum();
    }

    public int calcularTotalReservas() {
        return reservas.size();
    }

    public void agregarVenta(Venta venta) {
        if (venta == null) throw new IllegalArgumentException("Venta nula");
        ventas.add(venta);
    }

    public void agregarReserva(Reserva reserva) {
        if (reserva == null) throw new IllegalArgumentException("Reserva nula");
        reservas.add(reserva);
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getISBN() { return ISBN; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    public List<Venta> getVentas() {
        return Collections.unmodifiableList(ventas);
    }

    public List<Reserva> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    // Setters controlados
    public void setPrecio(double precio) {
        if (precio <= 0) throw new IllegalArgumentException("Precio inválido");
        this.precio = precio;
    }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty())
            throw new IllegalArgumentException("Título inválido");
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isEmpty())
            throw new IllegalArgumentException("Autor inválido");
        this.autor = autor;
    }
    public void actualizarDatos(String titulo, String autor, double precio) {
        if (titulo == null || titulo.isEmpty())
            throw new IllegalArgumentException("Título inválido");

        if (precio <= 0)
            throw new IllegalArgumentException("Precio inválido");

        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }
}
