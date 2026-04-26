package com.pageturner.dao;

import com.pageturner.model.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    // 🔹 GUARDAR LIBRO
    public void guardar(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, precio, stock) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getISBN());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getStock());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 LISTAR LIBROS
    public List<Libro> listar() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );

                lista.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    // 🔹 LISTAR SOLO LIBROS CON STOCK
    public List<Libro> listarDisponibles() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE stock > 0";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );

                lista.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    // 🔹 LISTAR SOLO LIBROS SIN STOCK
    public List<Libro> listarSinStock() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE stock = 0";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("isbn"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );

                lista.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    // 🔹 ACTUALIZAR DATOS (titulo, autor, precio)
    public void actualizar(Libro libro) {
        String sql = "UPDATE libros SET titulo=?, autor=?, precio=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setDouble(3, libro.getPrecio());
            stmt.setInt(4, libro.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 ACTUALIZAR SOLO STOCK
    public void actualizarStock(Libro libro) {
        String sql = "UPDATE libros SET stock=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, libro.getStock());
            stmt.setInt(2, libro.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔹 ELIMINAR LIBRO
    public void eliminar(int id) {
        String sql = "DELETE FROM libros WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int totalLibros() {
        String sql = "SELECT COUNT(*) FROM libros";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int obtenerStockPorId(int id) {
        String sql = "SELECT stock FROM libros WHERE id = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("stock");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}