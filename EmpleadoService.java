public class EmpleadoService {
    
    public double calcularSalarioFinal(Empleado emp) {
        return (emp.salarioBase + (emp.bonoMensual * 1.10)) - (emp.salarioBase * 0.05);
    }

    public boolean bonoExtraPorID(Empleado emp) {
        return (emp.idEmpleado % 2 == 0);
    }

    public boolean validarElegibilidad(Empleado emp, int puntajeTest, int edad, int idSede) {
        return (puntajeTest > 85 && edad < 30) || (idSede == 1 && !emp.esActivo);
    }

    public void actualizarBono(Empleado emp, float incremento) {
        emp.bonoMensual += incremento;
    }
}
