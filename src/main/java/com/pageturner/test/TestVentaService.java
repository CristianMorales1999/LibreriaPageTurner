package com.pageturner.test;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.dao.LibroDAO;
import com.pageturner.model.Cliente;
import com.pageturner.model.Libro;
import com.pageturner.service.VentaService;

public class TestVentaService {
    public static void main(String[] args) {

        ClienteDAO clienteDAO = new ClienteDAO();
        LibroDAO libroDAO = new LibroDAO();

        Cliente cliente = clienteDAO.listar().get(0);
        Libro libro = libroDAO.listar().get(0);

        VentaService service = new VentaService();

        service.registrarVenta(cliente, libro, 1);

        System.out.println("Venta registrada correctamente");
    }
}