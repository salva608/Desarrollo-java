package org.riwi.talent.view;

import org.riwi.talent.controller.EmpleadoService;
import org.riwi.talent.model.Empleado;
import org.riwi.talent.model.EmpresaRecord;

public class Main {
    public static void main(String[] args) {

        String header = """
            
            CORPORATE TALENT HUB SYSTEM
            
            """;
        System.out.println(header);

        Empleado emp = new Empleado(2, 2000000L, 300000f, 'M', true, "Carlos");
        EmpresaRecord empresa = new EmpresaRecord("Acme S.A.S", "900-123-456-7", 2005);

        EmpleadoService service = new EmpleadoService();

        System.out.println(service.calcularSalarioFinal(emp));
        System.out.println(service.bonoExtraPorID(emp));
        System.out.println(service.validarElegibilidad(emp, 90, 28, 2));
        System.out.println(empresa.nombre() + " | " + empresa.nit());

        service.actualizarBono(emp, 50000f);

        // Laboratorio de nulos
        emp.nombre = null;
        try {
            System.out.println(emp.nombre.toUpperCase());
        } catch (NullPointerException e) {
            System.out.println("NPE: " + e.getMessage());
        }

        // Comparación de referencias en Heap
        Empleado emp2 = new Empleado(2, 2000000L, 300000f, 'M', true, "Carlos");
        System.out.println("== : " + (emp == emp2));
    }

}
