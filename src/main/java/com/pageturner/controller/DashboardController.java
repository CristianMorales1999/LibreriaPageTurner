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
    @FXML private LineChart<String, Number> graficoIngresos;
    @FXML private BarChart<String, Number> graficoTopLibros;

    private final VentaDAO ventaDAO = new VentaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final LibroDAO libroDAO = new LibroDAO();

    @FXML
    public void initialize() {
        cargarDatos();
        cargarGraficoVentas();
        cargarGraficoIngresos();
        cargarTopLibros();
    }

    private void cargarDatos() {
        lblVentas.setText(String.valueOf(ventaDAO.totalVentas()));
        lblReservas.setText(String.valueOf(reservaDAO.totalReservas()));
        lblIngresos.setText(String.format("S/ %.2f", ventaDAO.totalIngresos()));
        lblLibros.setText(String.valueOf(libroDAO.totalLibros()));
    }

    private void cargarGraficoVentas() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ventas");

        var datos = ventaDAO.ventasPorMes();

        for (var entry : datos.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        graficoVentas.getData().clear();
        graficoVentas.getData().add(serie);
    }

    private void cargarGraficoIngresos() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ingresos");

        var datos = ventaDAO.ingresosPorMes();

        for (var entry : datos.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        graficoIngresos.getData().clear();
        graficoIngresos.getData().add(serie);
    }

    private void cargarTopLibros() {
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Top Libros");

        var datos = ventaDAO.topLibrosVendidos();

        for (var entry : datos.entrySet()) {
            serie.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        graficoTopLibros.getData().clear();
        graficoTopLibros.getData().add(serie);
    }
}