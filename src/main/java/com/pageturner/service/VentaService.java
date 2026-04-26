package com.pageturner.service;

import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.VentaDAO;
import com.pageturner.model.Cliente;
import com.pageturner.model.Libro;
import com.pageturner.model.Venta;
import javafx.scene.control.Alert;

public class VentaService {

    private final VentaDAO ventaDAO;
    private final LibroDAO libroDAO;

    public VentaService() {
        this.ventaDAO = new VentaDAO();
        this.libroDAO = new LibroDAO();
    }

    // 🔥 FLUJO COMPLETO DE VENTA
    public void registrarVenta(Cliente cliente, Libro libro, int cantidad) {

        // 1. Validaciones básicas (extra seguridad)
        if (cliente == null || libro == null) {
            throw new IllegalArgumentException("Cliente o libro inválido");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        // 2. Crear objeto (aplica lógica de negocio)
        Venta venta = new Venta(cantidad, cliente, libro);

        // 3. Persistir venta
        ventaDAO.guardar(venta);

        // 4. Persistir nuevo stock
        libroDAO.actualizarStock(libro);
    }
}