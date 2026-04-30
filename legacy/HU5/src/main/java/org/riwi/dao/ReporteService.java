package org.riwi.dao;

import org.riwi.model.EmpleadoReporteDTO;
import org.riwi.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK 4: Servicio de reportes usando Records
 * 
 * Demuestra cómo combinar JDBC moderno (try-with-resources) con Records
 * para generar reportes eficientes e inmutables.
 */
public class ReporteService {
    
    /**
     * Genera un reporte consolidado usando una consulta JOIN compleja
     * 
     * ANÁLISIS MODERNO: Records + JDBC
     * 
     * VENTAJAS de combinar Records con JDBC:
     * 
     * 1. MAPEO DIRECTO Y SEGURO:
     *    - Record = estructura de datos inmutable
     *    - ResultSet → Record = transformación clara y tipo-segura
     *    - No hay riesgo de modificación accidental
     * 
     * 2. CÓDIGO MÁS MANTENIBLE:
     *    - Si la consulta SQL cambia, solo actualizas el Record
     *    - El compilador detecta incompatibilidades de tipos
     *    - No necesitas actualizar getters/setters/equals/hashCode
     * 
     * 3. INMUTABILIDAD = THREAD-SAFETY:
     *    - Los registros del reporte no pueden cambiar
     *    - Seguro para compartir entre threads
     *    - Ideal para caching
     * 
     * 4. PERFORMANCE:
     *    - Records son más livianos que POJOs
     *    - Menos overhead de memoria
     *    - Mejor para grandes volúmenes de datos
     * 
     * vs POJO TRADICIONAL (Java 8):
     * - Requiere clase con 40+ líneas de boilerplate
     * - Campos mutables por defecto (no thread-safe)
     * - Más propenso a errores (olvidar actualizar equals/hashCode)
     * - Más código para mantener
     */
    public List<EmpleadoReporteDTO> generarReporteConsolidado() {
        List<EmpleadoReporteDTO> reporte = new ArrayList<>();
        
        // Consulta SQL compleja con JOIN
        String sql = """
                SELECT 
                    e.id_empleado,
                    e.nombre,
                    e.salario_base,
                    e.bono_mensual,
                    (e.salario_base + (e.bono_mensual * 1.10)) as salario_total,
                    e.genero,
                    e.es_activo,
                    COALESCE(d.nombre, 'Sin departamento') as departamento
                FROM empleados e
                LEFT JOIN departamentos d ON e.departamento_id = d.id_departamento
                ORDER BY e.salario_base DESC
                """;
        
        // Try-with-resources: cierre automático de recursos
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            // MAPEO DE ResultSet A Record
            // Limpio, conciso, tipo-seguro
            while (rs.next()) {
                EmpleadoReporteDTO empleado = new EmpleadoReporteDTO(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getLong("salario_base"),
                        rs.getFloat("bono_mensual"),
                        rs.getDouble("salario_total"),
                        rs.getString("genero").charAt(0),
                        rs.getBoolean("es_activo"),
                        rs.getString("departamento")
                );
                
                reporte.add(empleado);
            }
            
        } catch (SQLException e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
        }
        
        return reporte;
        
        /*
         * COMPARACIÓN con POJO (Java 8):
         * 
         * EmpleadoReporteDTO emp = new EmpleadoReporteDTO();
         * emp.setIdEmpleado(rs.getInt("id_empleado"));
         * emp.setNombre(rs.getString("nombre"));
         * emp.setSalarioBase(rs.getLong("salario_base"));
         * // ... 5 líneas más de setters
         * 
         * Problemas:
         * - Más código
         * - Objeto mutable (puede cambiar después)
         * - No thread-safe
         * - Requiere clase con getters/setters/equals/hashCode
         * 
         * Con Record:
         * - 1 línea de creación
         * - Inmutable por diseño
         * - Thread-safe
         * - Todo generado automáticamente
         */
    }
    
    /**
     * Genera reporte solo de empleados activos
     */
    public List<EmpleadoReporteDTO> generarReporteActivos() {
        List<EmpleadoReporteDTO> reporte = new ArrayList<>();
        
        String sql = """
                SELECT 
                    e.id_empleado,
                    e.nombre,
                    e.salario_base,
                    e.bono_mensual,
                    (e.salario_base + (e.bono_mensual * 1.10)) as salario_total,
                    e.genero,
                    e.es_activo,
                    COALESCE(d.nombre, 'Sin departamento') as departamento
                FROM empleados e
                LEFT JOIN departamentos d ON e.departamento_id = d.id_departamento
                WHERE e.es_activo = true
                ORDER BY e.salario_total DESC
                """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                reporte.add(new EmpleadoReporteDTO(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getLong("salario_base"),
                        rs.getFloat("bono_mensual"),
                        rs.getDouble("salario_total"),
                        rs.getString("genero").charAt(0),
                        rs.getBoolean("es_activo"),
                        rs.getString("departamento")
                ));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al generar reporte: " + e.getMessage());
        }
        
        return reporte;
    }
    
    /**
     * Genera estadísticas usando Records
     */
    public EstadisticasDTO generarEstadisticas() {
        String sql = """
                SELECT 
                    COUNT(*) as total_empleados,
                    SUM(CASE WHEN es_activo = true THEN 1 ELSE 0 END) as activos,
                    AVG(salario_base) as salario_promedio,
                    MAX(salario_base) as salario_maximo,
                    MIN(salario_base) as salario_minimo
                FROM empleados
                """;
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return new EstadisticasDTO(
                        rs.getInt("total_empleados"),
                        rs.getInt("activos"),
                        rs.getDouble("salario_promedio"),
                        rs.getLong("salario_maximo"),
                        rs.getLong("salario_minimo")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error al generar estadísticas: " + e.getMessage());
        }
        
        return new EstadisticasDTO(0, 0, 0.0, 0L, 0L);
    }
    
    /**
     * Record para estadísticas - Otro ejemplo de uso de Records
     */
    public record EstadisticasDTO(
            int totalEmpleados,
            int empleadosActivos,
            double salarioPromedio,
            long salarioMaximo,
            long salarioMinimo
    ) {
        public int empleadosInactivos() {
            return totalEmpleados - empleadosActivos;
        }
        
        public double porcentajeActivos() {
            return totalEmpleados > 0 ? (empleadosActivos * 100.0 / totalEmpleados) : 0;
        }
    }
}
