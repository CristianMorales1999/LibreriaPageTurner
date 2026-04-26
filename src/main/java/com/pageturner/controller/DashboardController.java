package com.pageturner.controller;

import com.pageturner.dao.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Label lblVentas;
    @FXML private Label lblReservas;
    @FXML private Label lblIngresos;

    private final VentaDAO ventaDAO = new VentaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();

    @FXML
    public void initialize() {
        cargarDatos();
    }

    private void cargarDatos() {
        int totalVentas = ventaDAO.totalVentas();
        int totalReservas = reservaDAO.totalReservas();
        double ingresos = ventaDAO.totalIngresos();

        lblVentas.setText(String.valueOf(totalVentas));
        lblReservas.setText(String.valueOf(totalReservas));
        lblIngresos.setText("S/ " + ingresos);
    }
}