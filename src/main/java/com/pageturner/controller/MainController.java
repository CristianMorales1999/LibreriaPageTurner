package com.pageturner.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class MainController {

    @FXML
    private StackPane contenedor;

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

    @FXML
    private void irDashboard() {
        cargarVista("/view/dashboard.fxml"); // luego lo creamos
    }

    @FXML
    private void irLibros() {
        cargarVista("/view/libros.fxml");
    }

    @FXML
    private void irClientes() {
        cargarVista("/view/clientes.fxml");
    }

    @FXML
    private void irVentas() {
        cargarVista("/view/ventas.fxml"); // luego
    }

    @FXML
    private void irReservas() {
        cargarVista("/view/reservas.fxml"); // luego
    }
}