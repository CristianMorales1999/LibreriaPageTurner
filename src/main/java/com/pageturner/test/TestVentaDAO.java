package com.pageturner.test;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.VentaDAO;
import com.pageturner.model.Cliente;
import com.pageturner.model.Libro;
import com.pageturner.model.Venta;

public class TestVentaDAO {
    public static void main(String[] args) {

        VentaDAO ventaDAO = new VentaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        LibroDAO libroDAO = new LibroDAO();

        Cliente cliente = clienteDAO.listar().get(0);
        Libro libro = libroDAO.listar().get(0);

        Venta venta = new Venta(1, cliente, libro);

        ventaDAO.guardar(venta);
        libroDAO.actualizarStock(libro);

        ventaDAO.listar().forEach(v ->
                System.out.println(v.getCliente().getNombre() + " compró " + v.getLibro().getTitulo())
        );
    }
}
