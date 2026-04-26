package com.pageturner.controller;

import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.VentaDAO;
import com.pageturner.model.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;

public class LibroController {

    // 🔹 Tabla
    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn<Libro, Integer> colId;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, Double> colPrecio;
    @FXML private TableColumn<Libro, Integer> colStock;

    // 🔹 Formulario
    @FXML private VBox formulario;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtISBN;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;

    @FXML private Label lblVentas;
    @FXML private Label lblIngresos;

    private final LibroDAO libroDAO = new LibroDAO();
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    private final VentaDAO ventaDAO = new VentaDAO();

    @FXML
    public void initialize() {
        tablaLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        configurarTabla();
        cargarLibros();

        tablaLibros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        mostrarMetricas(newSelection);
                    }
                }
        );
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    private void cargarLibros() {
        listaLibros.setAll(libroDAO.listar());
        tablaLibros.setItems(listaLibros);
    }

    // 🔹 Mostrar formulario
    @FXML
    private void mostrarFormulario() {
        formulario.setVisible(!formulario.isVisible());
    }

    // 🔹 Guardar libro
    @FXML
    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String isbn = txtISBN.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Libro libro = new Libro(titulo, autor, isbn, precio, stock);

            libroDAO.guardar(libro);

            limpiarFormulario();
            cargarLibros();

        } catch (Exception e) {
            //Personalizar mensajes de error (FALTA)
            mostrarError(e.getMessage());
        }
    }

    private void limpiarFormulario() {
        txtTitulo.clear();
        txtAutor.clear();
        txtISBN.clear();
        txtPrecio.clear();
        txtStock.clear();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void mostrarMetricas(Libro libro) {

        int ventas = ventaDAO.contarVentasPorLibro(libro.getId());
        double ingresos = ventaDAO.totalIngresosPorLibro(libro.getId());

        lblVentas.setText("Ventas: " + ventas);
        lblIngresos.setText("Ingresos: S/ " + String.format("%.2f", ingresos));
    }
}