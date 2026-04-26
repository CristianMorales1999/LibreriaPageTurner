package com.pageturner.dao;

import com.pageturner.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    // 🔹 GUARDAR RESERVA
    public void guardar(Reserva reserva) {
        String sql = "INSERT INTO reservas (fecha, estado, cliente_id, libro_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(reserva.getFecha()));
            stmt.setString(2, reserva.getEstado().name());
            stmt.setInt(3, reserva.getCliente().getId());
            stmt.setInt(4, reserva.getLibro().getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 LISTAR RESERVAS
    public List<Reserva> listar() {
        List<Reserva> lista = new ArrayList<>();

        String sql = """
                SELECT r.*, 
                       c.id AS c_id, c.nombre, c.dni, c.correo, c.celular,
                       l.id AS l_id, l.titulo, l.autor, l.isbn, l.precio, l.stock
                FROM reservas r
                JOIN clientes c ON r.cliente_id = c.id
                JOIN libros l ON r.libro_id = l.id
                """;

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                // 🔹 reconstruir cliente
                Cliente cliente = new Cliente(
                        rs.getInt("c_id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("celular")
                );

                // 🔹 reconstruir libro
                Libro libro = new Libro(
                        rs.getInt("l_id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );

                // 🔹 convertir estado (String → Enum)
                EstadoReserva estado = EstadoReserva.valueOf(rs.getString("estado").toUpperCase());

                // 🔹 reconstruir reserva (SIN lógica)
                Reserva reserva = new Reserva(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        estado,
                        cliente,
                        libro
                );

                lista.add(reserva);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 ACTUALIZAR ESTADO
    public void actualizarEstado(int id, EstadoReserva nuevoEstado) {
        String sql = "UPDATE reservas SET estado=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado.name());
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 TOTAL RESERVAS
    public int totalReservas() {
        String sql = "SELECT COUNT(*) FROM reservas";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int contarReservasPorCliente(int clienteId) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE cliente_id = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}