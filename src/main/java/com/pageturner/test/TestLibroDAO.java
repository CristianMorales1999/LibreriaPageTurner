package com.pageturner.test;

import com.pageturner.dao.LibroDAO;
import com.pageturner.model.Libro;

public class TestLibroDAO {
    public static void main(String[] args) {
        LibroDAO dao = new LibroDAO();

        // Crear libro
        Libro libro = new Libro("Clean Code", "Robert C. Martin", "123456", 50.0, 10);
        dao.guardar(libro);

        // Listar
        dao.listar().forEach(l ->
                System.out.println(l.getTitulo() + " - Stock: " + l.getStock())
        );
    }
}
