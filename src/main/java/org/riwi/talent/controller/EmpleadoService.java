package org.riwi.talent.controller;

import org.riwi.talent.model.Empleado;

public class EmpleadoService {

    public double calcularSalarioFinal(Empleado emp) {
        return (emp.getSalarioBase() + (emp.getBonoMensual() * 1.10))
                - (emp.getSalarioBase() * 0.05);
    }

    public boolean bonoExtraPorID(Empleado emp) {
        return (emp.getIdEmpleado() % 2 == 0);
    }

    public boolean validarElegibilidad(Empleado emp, int puntajeTest, int edad, int idSede) {
        return (puntajeTest > 85 && edad < 30)
                || (idSede == 1 && !emp.isEsActivo());
    }

    public void actualizarBono(Empleado emp, float incremento) {
        emp.setBonoMensual(emp.getBonoMensual() + incremento);
    }
}