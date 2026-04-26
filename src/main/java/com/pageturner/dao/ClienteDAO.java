package com.pageturner.dao;

import com.pageturner.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // ✅ GUARDAR CLIENTE
    public void guardar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, dni, correo, celular) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDni());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getCelular());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ LISTAR CLIENTES
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("celular")
                );

                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ ACTUALIZAR CLIENTE
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, dni=?, correo=?, celular=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDni());
            stmt.setString(3, cliente.getCorreo());
            stmt.setString(4, cliente.getCelular());
            stmt.setInt(5, cliente.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}