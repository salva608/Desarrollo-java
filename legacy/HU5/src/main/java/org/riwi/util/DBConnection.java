package org.riwi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * TASK 1: Gestión de conexiones y recursos (Legacy vs Modern)
 * 
 * Esta clase demuestra la evolución en la gestión de conexiones JDBC
 * desde Java 8 hacia versiones modernas (Java 17/21)
 */
public class DBConnection {
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/talent_hub_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    /**
     * MÉTODO MODERNO (Java 17/21)
     * Usando try-with-resources para gestión automática de recursos
     * 
     * @return Conexión a la base de datos
     * @throws SQLException si hay error en la conexión
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Registrar el driver (opcional en versiones modernas de JDBC)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL no encontrado", e);
        }
        
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    /*
     * =========================================================================
     * SINTAXIS LEGACY (Java 8 y anteriores)
     * =========================================================================
     * 
     * Antes de Java 7, la gestión de recursos se hacía manualmente usando
     * bloques finally para asegurar el cierre de conexiones.
     * 
     * EJEMPLO DE CÓDIGO LEGACY:
     * 
     * Connection conn = null;
     * PreparedStatement stmt = null;
     * ResultSet rs = null;
     * 
     * try {
     *     conn = DriverManager.getConnection(URL, USER, PASSWORD);
     *     stmt = conn.prepareStatement("SELECT * FROM empleados");
     *     rs = stmt.executeQuery();
     *     
     *     while (rs.next()) {
     *         // Procesar resultados
     *     }
     * } catch (SQLException e) {
     *     e.printStackTrace();
     * } finally {
     *     // IMPORTANTE: Cerrar recursos en orden inverso a su creación
     *     if (rs != null) {
     *         try {
     *             rs.close();
     *         } catch (SQLException e) {
     *             e.printStackTrace();
     *         }
     *     }
     *     if (stmt != null) {
     *         try {
     *             stmt.close();
     *         } catch (SQLException e) {
     *             e.printStackTrace();
     *         }
     *     }
     *     if (conn != null) {
     *         try {
     *             conn.close();
     *         } catch (SQLException e) {
     *             e.printStackTrace();
     *         }
     *     }
     * }
     * 
     * PROBLEMAS DEL ENFOQUE LEGACY:
     * 1. Código verboso y difícil de mantener
     * 2. Posibles Memory Leaks si olvidamos cerrar algún recurso
     * 3. Cada cierre puede lanzar una excepción, requiriendo try-catch anidados
     * 4. Si ocurre una excepción al cerrar un recurso, los siguientes no se cierran
     * 
     * =========================================================================
     */
    
    /*
     * =========================================================================
     * SINTAXIS MODERNA (Java 17/21) - Try-with-resources
     * =========================================================================
     * 
     * Desde Java 7, el try-with-resources gestiona automáticamente el cierre
     * de recursos que implementan AutoCloseable.
     * 
     * EJEMPLO MODERNO:
     * 
     * try (Connection conn = DBConnection.getConnection();
     *      PreparedStatement stmt = conn.prepareStatement("SELECT * FROM empleados");
     *      ResultSet rs = stmt.executeQuery()) {
     *      
     *     while (rs.next()) {
     *         // Procesar resultados
     *     }
     *     
     * } catch (SQLException e) {
     *     e.printStackTrace();
     * }
     * 
     * VENTAJAS DEL ENFOQUE MODERNO:
     * 
     * 1. PREVENCIÓN DE MEMORY LEAKS:
     *    - Los recursos se cierran AUTOMÁTICAMENTE al salir del bloque try
     *    - Incluso si ocurre una excepción, los recursos se cierran
     *    - No hay riesgo de olvidar cerrar un recurso
     * 
     * 2. ORDEN DE CIERRE GARANTIZADO:
     *    - Los recursos se cierran en ORDEN INVERSO a su declaración
     *    - Primero rs, luego stmt, finalmente conn
     * 
     * 3. MANEJO DE EXCEPCIONES MEJORADO:
     *    - Si ocurre una excepción al cerrar un recurso, se registra como "suprimida"
     *    - La excepción original no se pierde
     *    - Se pueden recuperar con getSuppressed()
     * 
     * 4. CÓDIGO MÁS LIMPIO Y LEGIBLE:
     *    - Menos líneas de código
     *    - Intención más clara
     *    - Menor probabilidad de errores
     * 
     * 5. GESTIÓN DE MEMORIA EFICIENTE:
     *    - El Garbage Collector puede liberar memoria más rápidamente
     *    - Reduce la presión sobre el pool de conexiones
     *    - Mejora el rendimiento general de la aplicación
     * 
     * =========================================================================
     */
    
    /**
     * Método de prueba para verificar la conexión
     */
    public static void testConnection() {
        // EJEMPLO PRÁCTICO DE TRY-WITH-RESOURCES
        // La conexión se cierra automáticamente al finalizar el bloque
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Conexión exitosa a la base de datos");
                System.out.println("  Database: " + conn.getCatalog());
                System.out.println("  Driver: " + conn.getMetaData().getDriverName());
            }
        } catch (SQLException e) {
            System.err.println("✗ Error al conectar: " + e.getMessage());
        }
        // ¡No necesitamos cerrar manualmente la conexión!
        // Try-with-resources lo hace por nosotros, previniendo memory leaks
    }
}
