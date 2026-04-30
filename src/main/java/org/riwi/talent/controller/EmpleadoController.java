package org.riwi.talent.controller;

import org.riwi.talent.dao.EmpleadoDAO;
import org.riwi.talent.dao.impl.EmpleadoDAOImpl;
import org.riwi.talent.model.Empleado;
import java.util.List;

public class EmpleadoController {

    private final EmpleadoDAO empleadoDAO;

    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }

    public boolean registrarEmpleado(String nombre, long salarioBase, float bonoMensual, char genero, boolean esActivo) {
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío");
            return false;
        }
        if (salarioBase < 0) {
            System.err.println("Error: El salario no puede ser negativo");
            return false;
        }
        Empleado empleado = new Empleado(nombre, salarioBase, bonoMensual, genero, esActivo);
        return empleadoDAO.insertar(empleado);
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoDAO.listarTodos();
    }

    public Empleado buscarEmpleado(int id) {
        if (id <= 0) {
            System.err.println("Error: ID inválido");
            return null;
        }
        return empleadoDAO.buscarPorId(id);
    }

    public boolean actualizarEmpleado(int id, String nombre, long salarioBase, float bonoMensual, char genero, boolean esActivo) {
        Empleado existente = empleadoDAO.buscarPorId(id);
        if (existente == null) {
            System.err.println("Error: Empleado no encontrado");
            return false;
        }
        Empleado empleado = new Empleado(id, nombre, salarioBase, bonoMensual, genero, esActivo);
        return empleadoDAO.actualizar(empleado);
    }

    public boolean eliminarEmpleado(int id) {
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null) {
            System.err.println("Error: Empleado no encontrado");
            return false;
        }
        return empleadoDAO.eliminar(id);
    }

    public double calcularSalarioTotal(int id) {
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null) return 0;
        return empleado.getSalarioBase() + (empleado.getBonoMensual() * 1.10);
    }
}