package com.pageturner.controller;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.VentaDAO;
import com.pageturner.model.*;
import com.pageturner.service.VentaService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class VentaController {

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<Libro> cbLibro;
    @FXML private TextField txtCantidad;

    @FXML private TableView<Venta> tablaVentas;
    @FXML private TableColumn<Venta, Integer> colId;
    @FXML private TableColumn<Venta, String> colCliente;
    @FXML private TableColumn<Venta, String> colLibro;
    @FXML private TableColumn<Venta, Integer> colCantidad;
    @FXML private TableColumn<Venta, String> colFecha;
    @FXML private TableColumn<Venta, Double> colTotal;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final LibroDAO libroDAO = new LibroDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final VentaService ventaService = new VentaService();

    private final ObservableList<Venta> listaVentas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tablaVentas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        cargarCombos();
        configurarTabla();
        cargarVentas();
    }

    private void cargarCombos() {
        cbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listar()));
        cbLibro.setItems(FXCollections.observableArrayList(libroDAO.listarDisponibles()));

        // Mostrar nombres en lugar de objetos
        cbCliente.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty ? "" : c.getNombre());
            }
        });

        cbCliente.setButtonCell(new ListCell<>() {
            protected void updateItem(Cliente c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty ? "" : c.getNombre());
            }
        });

        cbLibro.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Libro l, boolean empty) {
                super.updateItem(l, empty);
                setText(empty ? "" : l.getTitulo());
            }
        });

        cbLibro.setButtonCell(new ListCell<>() {
            protected void updateItem(Libro l, boolean empty) {
                super.updateItem(l, empty);
                setText(empty ? "" : l.getTitulo());
            }
        });
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        colCliente.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCliente().getNombre()
                )
        );

        colLibro.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getLibro().getTitulo()
                )
        );

        colFecha.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getFecha().toString()
                )
        );

        colTotal.setCellValueFactory(data ->
                new javafx.beans.property.SimpleDoubleProperty(
                        data.getValue().calcularTotal()
                ).asObject()
        );
    }

    private void cargarVentas() {
        listaVentas.setAll(ventaDAO.listar());
        tablaVentas.setItems(listaVentas);
    }

    @FXML
    private void registrarVenta() {
        try {
            Cliente cliente = cbCliente.getValue();
            Libro libro = cbLibro.getValue();
            int cantidad = Integer.parseInt(txtCantidad.getText());


            ventaService.registrarVenta(cliente, libro, cantidad);

            limpiarFormulario();
            cargarVentas();
            cargarCombos(); // IMPORTANTE

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void limpiarFormulario() {
        cbCliente.setValue(null);
        cbLibro.setValue(null);
        txtCantidad.clear();
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.show();
    }
}