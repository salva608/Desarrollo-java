package org.riwi.controller;

import org.riwi.dao.EmpleadoDAO;
import org.riwi.dao.EmpleadoDAOImpl;
import org.riwi.model.Empleado;

import java.util.List;

/**
 * TASK 3: Controlador - Capa de lógica de negocio
 * 
 * PATRÓN MVC (Model-View-Controller):
 * 
 * RESPONSABILIDADES DEL CONTROLADOR:
 * - Recibe datos de la Vista (sin lógica de interfaz)
 * - Coordina operaciones con el Modelo (DAO)
 * - Aplica reglas de negocio
 * - Devuelve resultados a la Vista
 * 
 * NO debe:
 * - Interactuar directamente con Scanner o System.out (eso es de la Vista)
 * - Conocer detalles de SQL (eso es del DAO)
 * - Manejar la presentación de datos (eso es de la Vista)
 */
public class EmpleadoController {
    
    private final EmpleadoDAO empleadoDAO;
    
    public EmpleadoController() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    /**
     * Registra un nuevo empleado
     * Aplica validaciones de negocio antes de persistir
     */
    public boolean registrarEmpleado(String nombre, long salarioBase, 
                                     float bonoMensual, char genero, boolean esActivo) {
        
        // Validaciones de negocio
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío");
            return false;
        }
        
        if (salarioBase < 0) {
            System.err.println("Error: El salario no puede ser negativo");
            return false;
        }
        
        if (genero != 'M' && genero != 'F' && genero != 'O') {
            System.err.println("Error: Género inválido (use M, F u O)");
            return false;
        }
        
        // Crear empleado y delegar al DAO
        Empleado empleado = new Empleado(nombre, salarioBase, bonoMensual, genero, esActivo);
        return empleadoDAO.insertar(empleado);
    }
    
    /**
     * Obtiene todos los empleados
     */
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoDAO.listarTodos();
    }
    
    /**
     * Busca un empleado específico
     */
    public Empleado buscarEmpleado(int id) {
        if (id <= 0) {
            System.err.println("Error: ID inválido");
            return null;
        }
        return empleadoDAO.buscarPorId(id);
    }
    
    /**
     * Actualiza información de un empleado
     */
    public boolean actualizarEmpleado(int id, String nombre, long salarioBase,
                                      float bonoMensual, char genero, boolean esActivo) {
        
        // Validar que el empleado existe
        Empleado empleadoExistente = empleadoDAO.buscarPorId(id);
        if (empleadoExistente == null) {
            System.err.println("Error: Empleado no encontrado");
            return false;
        }
        
        // Validaciones de negocio
        if (nombre == null || nombre.trim().isEmpty()) {
            System.err.println("Error: El nombre no puede estar vacío");
            return false;
        }
        
        if (salarioBase < 0) {
            System.err.println("Error: El salario no puede ser negativo");
            return false;
        }
        
        // Actualizar y delegar
        Empleado empleado = new Empleado(id, nombre, salarioBase, bonoMensual, genero, esActivo);
        return empleadoDAO.actualizar(empleado);
    }
    
    /**
     * Elimina un empleado
     */
    public boolean eliminarEmpleado(int id) {
        // Validar que existe antes de eliminar
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null) {
            System.err.println("Error: Empleado no encontrado");
            return false;
        }
        
        return empleadoDAO.eliminar(id);
    }
    
    /**
     * Calcula el salario total de un empleado
     * Regla de negocio: Salario base + bono + 10% del bono
     */
    public double calcularSalarioTotal(int id) {
        Empleado empleado = empleadoDAO.buscarPorId(id);
        if (empleado == null) {
            return 0;
        }
        
        return empleado.getSalarioBase() + (empleado.getBonoMensual() * 1.10);
    }
    
    /**
     * Obtiene empleados activos solamente
     */
    public List<Empleado> obtenerEmpleadosActivos() {
        return empleadoDAO.listarTodos().stream()
                .filter(Empleado::isEsActivo)
                .toList();
    }
}
