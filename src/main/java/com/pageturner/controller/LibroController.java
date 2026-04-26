package com.pageturner.controller;

import com.pageturner.dao.LibroDAO;
import com.pageturner.dao.VentaDAO;
import com.pageturner.model.Libro;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

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
    @FXML private Label lblStockDetalle;

    private final LibroDAO libroDAO = new LibroDAO();
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();

    private final VentaDAO ventaDAO = new VentaDAO();

    @FXML
    private VBox formularioBox;

    @FXML
    private void mostrarFormulario() {
        formularioBox.setVisible(true);
        formularioBox.setManaged(true);

        // 🔥 Animación
        formularioBox.setOpacity(0);

        FadeTransition ft = new FadeTransition(Duration.millis(300), formularioBox);
        ft.setToValue(1);
        ft.play();
    }

    @FXML
    private void ocultarFormulario() {

        FadeTransition ft = new FadeTransition(Duration.millis(200), formularioBox);
        ft.setToValue(0);

        ft.setOnFinished(e -> {
            formularioBox.setVisible(false);
            formularioBox.setManaged(false);
        });

        ft.play();
    }

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
            ocultarFormulario();

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
        int stock = libroDAO.obtenerStockPorId(libro.getId());

        lblVentas.setText(String.valueOf(ventas));
        lblIngresos.setText("S/ " + ingresos);
        lblStockDetalle.setText(String.valueOf(stock));
    }
}