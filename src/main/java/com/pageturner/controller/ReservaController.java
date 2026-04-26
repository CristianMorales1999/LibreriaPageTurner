package com.pageturner.controller;

import com.pageturner.dao.*;
import com.pageturner.model.*;
import com.pageturner.service.ReservaService;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReservaController {

    @FXML private ComboBox<Cliente> cbCliente;
    @FXML private ComboBox<Libro> cbLibro;

    @FXML private TableView<Reserva> tablaReservas;
    @FXML private TableColumn<Reserva, Integer> colId;
    @FXML private TableColumn<Reserva, String> colCliente;
    @FXML private TableColumn<Reserva, String> colLibro;
    @FXML private TableColumn<Reserva, String> colEstado;
    @FXML private TableColumn<Reserva, String> colFecha;

    @FXML private Button btnHabilitada;
    @FXML private Button btnCompletada;
    @FXML private Button btnCancelar;

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final LibroDAO libroDAO = new LibroDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final ReservaService reservaService = new ReservaService();

    private final ObservableList<Reserva> lista = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cargarCombos();
        configurarTabla();
        cargarReservas();

        tablaReservas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, nueva) -> actualizarBotones(nueva)
        );
    }

    private void actualizarBotones(Reserva r) {
        if (r == null) {
            btnHabilitada.setDisable(true);
            btnCompletada.setDisable(true);
            btnCancelar.setDisable(true);
            return;
        }

        switch (r.getEstado()) {
            case PENDIENTE -> {
                btnHabilitada.setDisable(false);
                btnCompletada.setDisable(true);
                btnCancelar.setDisable(false);
            }
            case HABILITADA -> {
                btnHabilitada.setDisable(true);
                btnCompletada.setDisable(false);
                btnCancelar.setDisable(false);
            }
            case COMPLETADA, CANCELADA -> {
                btnHabilitada.setDisable(true);
                btnCompletada.setDisable(true);
                btnCancelar.setDisable(true);
            }
        }
    }
    private void cargarCombos() {
        cbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listar()));

        // SOLO LIBROS SIN STOCK
        cbLibro.setItems(FXCollections.observableArrayList(
                libroDAO.listarSinStock()
        ));

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

        colCliente.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCliente().getNombre()));

        colLibro.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getLibro().getTitulo()));

        colEstado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getEstado().name()));

        colFecha.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getFecha().toString()));
    }

    private void cargarReservas() {
        lista.setAll(reservaDAO.listar());
        tablaReservas.setItems(lista);
    }

    @FXML
    private void registrarReserva() {
        try {
            reservaService.registrarReserva(
                    cbCliente.getValue(),
                    cbLibro.getValue()
            );

            limpiar();
            cargarReservas();
            cargarCombos();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private Reserva getSeleccionada() {
        return tablaReservas.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void marcarHabilitada() {
        cambiarEstado(EstadoReserva.HABILITADA);
    }

    @FXML
    private void marcarCompletada() {
        cambiarEstado(EstadoReserva.COMPLETADA);
    }

    @FXML
    private void cancelarReserva() {
        cambiarEstado(EstadoReserva.CANCELADA);
    }

    private void cambiarEstado(EstadoReserva estado) {
        Reserva r = getSeleccionada();
        if (r == null) {
            mostrarError("Seleccione una reserva");
            return;
        }

        try {
            reservaService.cambiarEstadoReserva(r, estado);
            cargarReservas();

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void limpiar() {
        cbCliente.setValue(null);
        cbLibro.setValue(null);
    }

    private void mostrarError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(msg);
        a.show();
    }
}