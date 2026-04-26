package com.pageturner.test;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.ReservaDAO;
import com.pageturner.model.Cliente;
import com.pageturner.model.EstadoReserva;
import com.pageturner.model.Libro;
import com.pageturner.model.Reserva;

public class TestReservaDAO {
    public static void main(String[] args) {

        ReservaDAO reservaDAO = new ReservaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        LibroDAO libroDAO = new LibroDAO();

        Cliente cliente = clienteDAO.listar().get(0);
        Libro libro = libroDAO.listar().get(0);

        // Crear reserva (solo si no hay stock)
        Reserva reserva = new Reserva(cliente, libro);

        reservaDAO.guardar(reserva);

        // Listar
        reservaDAO.listar().forEach(r ->
                System.out.println(r.getCliente().getNombre() + " reservó " + r.getLibro().getTitulo())
        );

        // Cambiar estado
        reservaDAO.actualizarEstado(1, EstadoReserva.COMPLETADA);
    }
}
