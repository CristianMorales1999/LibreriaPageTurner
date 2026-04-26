package com.pageturner.controller;

import com.pageturner.dao.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.*;

public class DashboardController {

    @FXML private Label lblVentas;
    @FXML private Label lblReservas;
    @FXML private Label lblIngresos;
    @FXML private Label lblLibros;

    @FXML private LineChart<String, Number> graficoVentas;

    private final VentaDAO ventaDAO = new VentaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final LibroDAO libroDAO = new LibroDAO();

    @FXML
    public void initialize() {
        cargarDatos();
        cargarGrafico();
    }

    private void cargarDatos() {
        lblVentas.setText(String.valueOf(ventaDAO.totalVentas()));
        lblReservas.setText(String.valueOf(reservaDAO.totalReservas()));
        lblIngresos.setText(String.format("S/ %.2f", ventaDAO.totalIngresos()));
        lblLibros.setText(String.valueOf(libroDAO.totalLibros()));
    }

    private void cargarGrafico() {

        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ventas");

        // 🔹 Datos simples (puedes mejorar luego)
        serie.getData().add(new XYChart.Data<>("Ventas", ventaDAO.totalVentas()));
        serie.getData().add(new XYChart.Data<>("Reservas", reservaDAO.totalReservas()));

        graficoVentas.getData().add(serie);
    }
}