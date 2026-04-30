package org.riwi.dao;

import org.riwi.model.Empleado;
import org.riwi.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK 2: Implementación física del DAO
 * 
 * SEGURIDAD: Utiliza EXCLUSIVAMENTE PreparedStatement para prevenir
 * ataques de inyección SQL (SQL Injection).
 * 
 * ¿Por qué PreparedStatement es más seguro?
 * - Los parámetros se envían separados de la consulta SQL
 * - El driver escapa automáticamente caracteres especiales
 * - Previene que código malicioso se ejecute como SQL
 * 
 * Ejemplo de INYECCIÓN SQL (lo que PREVENIMOS):
 * String sql = "SELECT * FROM empleados WHERE nombre = '" + nombre + "'";
 * Si nombre = "' OR '1'='1", retornaría TODOS los registros
 * 
 * Con PreparedStatement:
 * String sql = "SELECT * FROM empleados WHERE nombre = ?";
 * stmt.setString(1, nombre);
 * El valor se trata como DATO, no como código SQL
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {
    
    @Override
    public boolean insertar(Empleado empleado) {
        // SQL con placeholders (?) para evitar inyección SQL
        String sql = "INSERT INTO empleados (nombre, salario_base, bono_mensual, genero, es_activo) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        // Try-with-resources: Connection y PreparedStatement se cierran automáticamente
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // SEGURIDAD: Setear parámetros usando métodos tipados
            // Esto previene SQL Injection al escapar caracteres especiales
            stmt.setString(1, empleado.getNombre());
            stmt.setLong(2, empleado.getSalarioBase());
            stmt.setFloat(3, empleado.getBonoMensual());
            stmt.setString(4, String.valueOf(empleado.getGenero()));
            stmt.setBoolean(5, empleado.isEsActivo());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            return false;
        }
        // No necesitamos finally: try-with-resources cierra automáticamente
        // Esto previene MEMORY LEAKS
    }
    
    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados ORDER BY id_empleado";
        
        // Try-with-resources con múltiples recursos: Connection, Statement, ResultSet
        // Se cierran en ORDEN INVERSO: ResultSet -> Statement -> Connection
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Empleado emp = new Empleado();
                emp.setIdEmpleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setSalarioBase(rs.getLong("salario_base"));
                emp.setBonoMensual(rs.getFloat("bono_mensual"));
                emp.setGenero(rs.getString("genero").charAt(0));
                emp.setEsActivo(rs.getBoolean("es_activo"));
                
                empleados.add(emp);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }
        
        return empleados;
    }
    
    @Override
    public Empleado buscarPorId(int id) {
        // SEGURIDAD: Usar PreparedStatement incluso para consultas simples
        String sql = "SELECT * FROM empleados WHERE id_empleado = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Empleado emp = new Empleado();
                    emp.setIdEmpleado(rs.getInt("id_empleado"));
                    emp.setNombre(rs.getString("nombre"));
                    emp.setSalarioBase(rs.getLong("salario_base"));
                    emp.setBonoMensual(rs.getFloat("bono_mensual"));
                    emp.setGenero(rs.getString("genero").charAt(0));
                    emp.setEsActivo(rs.getBoolean("es_activo"));
                    return emp;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public boolean actualizar(Empleado empleado) {
        // PreparedStatement previene inyección SQL en actualizaciones
        String sql = "UPDATE empleados SET nombre = ?, salario_base = ?, " +
                     "bono_mensual = ?, genero = ?, es_activo = ? " +
                     "WHERE id_empleado = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Parámetros en el orden de los placeholders
            stmt.setString(1, empleado.getNombre());
            stmt.setLong(2, empleado.getSalarioBase());
            stmt.setFloat(3, empleado.getBonoMensual());
            stmt.setString(4, String.valueOf(empleado.getGenero()));
            stmt.setBoolean(5, empleado.isEsActivo());
            stmt.setInt(6, empleado.getIdEmpleado());
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        // SEGURIDAD CRÍTICA: PreparedStatement para DELETE
        // Previene: DELETE FROM empleados WHERE id = '1 OR 1=1' (borraría todo)
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }
    
    /*
     * COMPARACIÓN: Statement vs PreparedStatement
     * 
     * ❌ INSEGURO (Statement):
     * String sql = "SELECT * FROM empleados WHERE nombre = '" + nombre + "'";
     * Statement stmt = conn.createStatement();
     * ResultSet rs = stmt.executeQuery(sql);
     * Vulnerable a: nombre = "'; DROP TABLE empleados; --"
     * 
     * ✅ SEGURO (PreparedStatement):
     * String sql = "SELECT * FROM empleados WHERE nombre = ?";
     * PreparedStatement stmt = conn.prepareStatement(sql);
     * stmt.setString(1, nombre);
     * ResultSet rs = stmt.executeQuery();
     * El valor se escapa automáticamente, sin importar su contenido
     * 
     * VENTAJAS ADICIONALES de PreparedStatement:
     * 1. Mejor rendimiento (query pre-compilada)
     * 2. Código más limpio y legible
     * 3. Previene errores de sintaxis SQL
     * 4. Manejo automático de tipos de datos
     */
}
