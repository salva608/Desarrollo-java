package org.riwi.talent.dao;

import org.riwi.talent.model.Empleado;
import java.util.List;

public interface EmpleadoDAO {
    boolean insertar(Empleado empleado);
    List<Empleado> listarTodos();
    Empleado buscarPorId(int id);
    boolean actualizar(Empleado empleado);
    boolean eliminar(int id);
}