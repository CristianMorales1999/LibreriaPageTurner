package com.pageturner.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class MainController {

    @FXML
    private StackPane contenedor;

    @FXML private Button btnDashboard;
    @FXML private Button btnLibros;
    @FXML private Button btnClientes;
    @FXML private Button btnVentas;
    @FXML private Button btnReservas;

    @FXML
    public void initialize() {
        irDashboard(); // Vista inicial
    }

    private void cargarVista(String ruta) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource(ruta));
            contenedor.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void activarBoton(Button botonActivo) {
        btnDashboard.getStyleClass().remove("sidebar-button-active");
        btnLibros.getStyleClass().remove("sidebar-button-active");
        btnClientes.getStyleClass().remove("sidebar-button-active");
        btnVentas.getStyleClass().remove("sidebar-button-active");
        btnReservas.getStyleClass().remove("sidebar-button-active");

        botonActivo.getStyleClass().add("sidebar-button-active");
    }

    @FXML
    private void irDashboard() {
        cargarVista("/view/dashboard.fxml");
        activarBoton(btnDashboard);
    }

    @FXML
    private void irLibros() {
        cargarVista("/view/libros.fxml");
        activarBoton(btnLibros);
    }

    @FXML
    private void irClientes() {
        cargarVista("/view/clientes.fxml");
        activarBoton(btnClientes);
    }

    @FXML
    private void irVentas() {
        cargarVista("/view/ventas.fxml");
        activarBoton(btnVentas);
    }

    @FXML
    private void irReservas() {
        cargarVista("/view/reservas.fxml");
        activarBoton(btnReservas);
    }
}