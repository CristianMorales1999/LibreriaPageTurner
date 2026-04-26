package com.pageturner.test;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.ReservaDAO;
import com.pageturner.model.*;
import com.pageturner.service.ReservaService;

public class TestReservaService {
    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();
        LibroDAO libroDAO = new LibroDAO();

        Cliente cliente = clienteDAO.listar().get(0);
        Libro libro = libroDAO.listar().get(0);

        ReservaService service = new ReservaService();

        // Registrar reserva
        service.registrarReserva(cliente, libro);

        System.out.println("Reserva registrada correctamente");


        ReservaDAO reservaDAO= new ReservaDAO();
        Reserva reserva= reservaDAO.listar().get(0);

        ReservaService reservaService=new ReservaService();
        reservaService.cambiarEstadoReserva(reserva,EstadoReserva.HABILITADA);

        System.out.println("Estado de reserva actualizado correctamente");
    }
}