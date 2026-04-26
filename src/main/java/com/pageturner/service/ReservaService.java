package com.pageturner.service;

import com.pageturner.dao.ReservaDAO;
import com.pageturner.model.*;

public class ReservaService {

    private final ReservaDAO reservaDAO;

    public ReservaService() {
        this.reservaDAO = new ReservaDAO();
    }

    // 🔥 REGISTRAR RESERVA
    public void registrarReserva(Cliente cliente, Libro libro) {

        // 1. Validaciones básicas
        if (cliente == null || libro == null) {
            throw new IllegalArgumentException("Cliente o libro inválido");
        }

        // 2. Regla de negocio clave
        if (libro.estaDisponible()) {
            throw new IllegalStateException("No se puede reservar un libro con stock disponible");
        }

        // 3. Crear objeto (lógica interna)
        Reserva reserva = new Reserva(cliente, libro);

        // 4. Guardar en BD
        reservaDAO.guardar(reserva);
    }

    // 🔥 CAMBIAR ESTADO DE RESERVA
    public void cambiarEstadoReserva(Reserva reserva, EstadoReserva nuevoEstado) {

        if (reserva == null || nuevoEstado == null) {
            throw new IllegalArgumentException("Datos inválidos");
        }

        // 1. Cambiar en objeto
        reserva.cambiarEstado(nuevoEstado);

        // 2. Persistir en BD
        reservaDAO.actualizarEstado(reserva.getId(), nuevoEstado);
    }
}