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
        irLibros(); // Vista inicial
    }

    private void cargarVista(String ruta) {
        try {
            Node vista = FXMLLoader.load(getClass().getResource(ruta));
            contenedor.getChildren().setAll(vista);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setActive(Button botonActivo) {

        if (btnDashboard != null) btnDashboard.getStyleClass().remove("active");
        if (btnLibros != null) btnLibros.getStyleClass().remove("active");
        if (btnClientes != null) btnClientes.getStyleClass().remove("active");
        if (btnVentas != null) btnVentas.getStyleClass().remove("active");
        if (btnReservas != null) btnReservas.getStyleClass().remove("active");

        if (botonActivo != null) {
            botonActivo.getStyleClass().add("active");
        }
    }

    @FXML
    private void irDashboard() {
        cargarVista("/view/dashboard.fxml");
        setActive(btnDashboard);
    }

    @FXML
    private void irLibros() {
        cargarVista("/view/libros.fxml");
        setActive(btnLibros);
    }

    @FXML
    private void irClientes() {
        cargarVista("/view/clientes.fxml");
        setActive(btnClientes);
    }

    @FXML
    private void irVentas() {
        cargarVista("/view/ventas.fxml");
        setActive(btnVentas);
    }

    @FXML
    private void irReservas() {
        cargarVista("/view/reservas.fxml");
        setActive(btnReservas);
    }
}