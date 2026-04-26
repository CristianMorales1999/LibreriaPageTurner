package com.pageturner.test;

import com.pageturner.dao.ConexionDB;
import java.sql.Connection;

public class TestConexion {

    public static void main(String[] args) {
        try (Connection conn = ConexionDB.getConnection()) {
            System.out.println("✅ Conexión exitosa a la BD");
        } catch (Exception e) {
            System.out.println("❌ Error de conexión");
            e.printStackTrace();
        }
    }
}