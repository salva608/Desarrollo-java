package org.riwi.talent.dao.impl;

import org.riwi.talent.dao.EmpleadoDAO;
import org.riwi.talent.model.Empleado;
import org.riwi.talent.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public boolean insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, salario_base, bono_mensual, genero, es_activo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setLong(2, empleado.getSalarioBase());
            stmt.setFloat(3, empleado.getBonoMensual());
            stmt.setString(4, String.valueOf(empleado.getGenero()));
            stmt.setBoolean(5, empleado.isEsActivo());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados ORDER BY id_empleado";
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
            System.err.println("Error: " + e.getMessage());
        }
        return empleados;
    }

    @Override
    public Empleado buscarPorId(int id) {
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
            System.err.println("Error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre=?, salario_base=?, bono_mensual=?, genero=?, es_activo=? WHERE id_empleado=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, empleado.getNombre());
            stmt.setLong(2, empleado.getSalarioBase());
            stmt.setFloat(3, empleado.getBonoMensual());
            stmt.setString(4, String.valueOf(empleado.getGenero()));
            stmt.setBoolean(5, empleado.isEsActivo());
            stmt.setInt(6, empleado.getIdEmpleado());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
}