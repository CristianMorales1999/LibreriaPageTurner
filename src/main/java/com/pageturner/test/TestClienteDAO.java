package com.pageturner.test;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.model.Cliente;

public class TestClienteDAO {
    public static void main(String[] args) {
        ClienteDAO dao = new ClienteDAO();

        // Crear
        Cliente c = new Cliente("Cristian Morales", "12345678", "cris@mail.com", "999888777");
        dao.guardar(c);

        // Listar
        dao.listar().forEach(cliente ->
                System.out.println(cliente.getNombre())
        );
    }
}