package org.riwi.talent.dao;

import org.riwi.talent.model.EmpleadoReporteDTO;
import org.riwi.talent.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReporteService {

    public List<EmpleadoReporteDTO> generarReporteConsolidado() {
        List<EmpleadoReporteDTO> reporte = new ArrayList<>();
        String sql = "SELECT e.id_empleado, e.nombre, e.salario_base, e.bono_mensual, " +
                "(e.salario_base + (e.bono_mensual * 1.10)) as salario_total, " +
                "e.genero, e.es_activo, COALESCE(d.nombre, 'Sin departamento') as departamento " +
                "FROM empleados e LEFT JOIN departamentos d ON e.departamento_id = d.id_departamento " +
                "ORDER BY e.salario_base DESC";

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
            System.err.println("Error: " + e.getMessage());
        }
        return reporte;
    }

    public EstadisticasDTO generarEstadisticas() {
        String sql = "SELECT COUNT(*) as total, " +
                "SUM(CASE WHEN es_activo = true THEN 1 ELSE 0 END) as activos, " +
                "AVG(salario_base) as promedio, MAX(salario_base) as maximo, MIN(salario_base) as minimo " +
                "FROM empleados";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new EstadisticasDTO(
                        rs.getInt("total"), rs.getInt("activos"),
                        rs.getDouble("promedio"), rs.getLong("maximo"), rs.getLong("minimo")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return new EstadisticasDTO(0, 0, 0.0, 0L, 0L);
    }

    public record EstadisticasDTO(
            int totalEmpleados, int empleadosActivos,
            double salarioPromedio, long salarioMaximo, long salarioMinimo
    ) {
        public int empleadosInactivos() { return totalEmpleados - empleadosActivos; }
        public double porcentajeActivos() {
            return totalEmpleados > 0 ? (empleadosActivos * 100.0 / totalEmpleados) : 0;
        }
    }
}