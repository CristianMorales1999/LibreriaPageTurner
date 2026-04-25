package com.pageturner.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cliente {

    private int id;
    private String nombre;
    private String dni;
    private String correo;
    private String celular;

    private List<Venta> ventas;
    private List<Reserva> reservas;

    public Cliente(int id, String nombre, String dni, String correo, String celular) {
        this.id = id;
        this.nombre = nombre;
        this.dni = dni;
        this.correo = correo;
        this.celular = celular;
        this.ventas = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    public double calcularTotalCompras() {
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
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getCorreo() { return correo; }
    public String getCelular() { return celular; }

    public List<Venta> getVentas() {
        return Collections.unmodifiableList(ventas);
    }

    public List<Reserva> getReservas() {
        return Collections.unmodifiableList(reservas);
    }

    // Setters controlados
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty())
            throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        if (correo == null || correo.isEmpty())
            throw new IllegalArgumentException("Correo inválido");
        this.correo = correo;
    }

    public void setCelular(String celular) {
        if (celular == null || celular.isEmpty())
            throw new IllegalArgumentException("Celular inválido");
        this.celular = celular;
    }
    
}