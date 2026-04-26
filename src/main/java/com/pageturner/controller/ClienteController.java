package com.pageturner.controller;

import com.pageturner.dao.ClienteDAO;
import com.pageturner.dao.VentaDAO;
import com.pageturner.dao.ReservaDAO;
import com.pageturner.model.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {

    // 🔹 Tabla
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colDni;
    @FXML private TableColumn<Cliente, String> colCorreo;
    @FXML private TableColumn<Cliente, String> colCelular;

    // 🔹 Formulario
    @FXML private VBox formulario;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDni;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtCelular;

    // 🔹 Métricas
    @FXML private Label lblCompras;
    @FXML private Label lblReservas;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final VentaDAO ventaDAO = new VentaDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();

    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabla();
        cargarClientes();

        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        mostrarMetricas(newSel);
                    }
                }
        );
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
    }

    private void cargarClientes() {
        listaClientes.setAll(clienteDAO.listar());
        tablaClientes.setItems(listaClientes);
    }

    @FXML
    private void mostrarFormulario() {
        formulario.setVisible(!formulario.isVisible());
    }

    @FXML
    private void guardarCliente() {
        try {
            Cliente cliente = new Cliente(
                    txtNombre.getText(),
                    txtDni.getText(),
                    txtCorreo.getText(),
                    txtCelular.getText()
            );

            clienteDAO.guardar(cliente);

            limpiarFormulario();
            cargarClientes();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarMetricas(Cliente cliente) {

        int compras = ventaDAO.contarVentasPorCliente(cliente.getId());
        int reservas = reservaDAO.contarReservasPorCliente(cliente.getId());

        lblCompras.setText("Compras: " + compras);
        lblReservas.setText("Reservas: " + reservas);
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtDni.clear();
        txtCorreo.clear();
        txtCelular.clear();
    }
    
    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Datos inválidos");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}