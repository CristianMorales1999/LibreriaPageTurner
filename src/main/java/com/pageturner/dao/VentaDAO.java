package com.pageturner.dao;

import com.pageturner.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VentaDAO {

    // 🔹 GUARDAR VENTA
    public void guardar(Venta venta) {
        String sql = "INSERT INTO ventas (fecha, cantidad, cliente_id, libro_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(venta.getFecha()));
            stmt.setInt(2, venta.getCantidad());
            stmt.setInt(3, venta.getCliente().getId());
            stmt.setInt(4, venta.getLibro().getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 LISTAR VENTAS
    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();

        String sql = """
                SELECT v.*, 
                       c.id AS c_id, c.nombre, c.dni, c.correo, c.celular,
                       l.id AS l_id, l.titulo, l.autor, l.isbn, l.precio, l.stock
                FROM ventas v
                JOIN clientes c ON v.cliente_id = c.id
                JOIN libros l ON v.libro_id = l.id
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

                // 🔹 reconstruir venta (SIN lógica)
                Venta venta = new Venta(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha").toLocalDateTime(),
                        rs.getInt("cantidad"),
                        cliente,
                        libro
                );

                lista.add(venta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // 🔹 TOTAL EJEMPLARES VENDIDOS
    public int totalVentas() {
        String sql = "SELECT SUM(cantidad) FROM ventas";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔹 INGRESOS TOTALES
    public double totalIngresos() {
        String sql = """
        SELECT SUM(v.cantidad * l.precio)
        FROM ventas v
        JOIN libros l ON v.libro_id = l.id
    """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getDouble(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarVentasPorLibro(int libroId) {
        String sql = """
                    SELECT SUM(cantidad) AS total_ejemplares
                    FROM ventas
                    WHERE libro_id = ?;
                    """;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, libroId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double totalIngresosPorLibro(int libroId) {
        String sql = """
                SELECT SUM(v.cantidad * l.precio) AS total_ingresos
                FROM ventas v
                JOIN libros l ON v.libro_id = l.id
                WHERE v.libro_id = ?
                """;
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, libroId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int contarVentasPorCliente(int clienteId) {
        String sql = "SELECT SUM(cantidad) FROM ventas WHERE cliente_id = ?";
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

    public Map<String, Integer> ventasPorMes() {
        Map<String, Integer> datos = new LinkedHashMap<>();

        String sql = """
            SELECT DATE_FORMAT(fecha, '%Y-%m') AS mes,
                   SUM(cantidad) AS total
            FROM ventas
            GROUP BY mes
            ORDER BY mes
        """;

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                datos.put(rs.getString("mes"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }
}