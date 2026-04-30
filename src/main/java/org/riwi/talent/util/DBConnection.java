package org.riwi.talent.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://aws-1-us-east-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.zcatmruqjhxhxedrromd";
    private static final String PASSWORD = "xpqretz1297**";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Conexión exitosa a Supabase");
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar: " + e.getMessage());
        }
    }
}