package org.riwi.dao;

import org.riwi.model.Empleado;
import java.util.List;

/**
 * TASK 2: Interfaz DAO (Data Access Object)
 * Define el contrato para las operaciones CRUD sobre Empleado
 */
public interface EmpleadoDAO {
    
    /**
     * Inserta un nuevo empleado en la base de datos
     * @param empleado Objeto empleado a insertar
     * @return true si la inserción fue exitosa
     */
    boolean insertar(Empleado empleado);
    
    /**
     * Lista todos los empleados de la base de datos
     * @return Lista de todos los empleados
     */
    List<Empleado> listarTodos();
    
    /**
     * Busca un empleado por su ID
     * @param id ID del empleado a buscar
     * @return Empleado encontrado o null si no existe
     */
    Empleado buscarPorId(int id);
    
    /**
     * Actualiza los datos de un empleado existente
     * @param empleado Empleado con datos actualizados
     * @return true si la actualización fue exitosa
     */
    boolean actualizar(Empleado empleado);
    
    /**
     * Elimina un empleado de la base de datos
     * @param id ID del empleado a eliminar
     * @return true si la eliminación fue exitosa
     */
    boolean eliminar(int id);
}
