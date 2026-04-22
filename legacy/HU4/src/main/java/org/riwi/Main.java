package org.riwi;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== TASK 1 - herencia sellada ===");

        EmpleadoLegacy empLegacy = new EmpleadoLegacy(1, "Carlos");
        System.out.println(empLegacy);

        Empleado emp1 = new Empleado(101, "Diana", "Backend");
        ConsultorExterno con1 = new ConsultorExterno(201, "Pedro", "Deloitte");
        System.out.println(emp1);
        System.out.println(con1);

        System.out.println("\n=== TASK 2 - records ===");

        DesempenoReportLegacy rl = new DesempenoReportLegacy(101, 8.5, "buen desempeño");
        System.out.println("legacy: " + rl);

        ReporteService rs = new ReporteService();

        DesempenoReport r1 = rs.generarReporte(101, new double[]{8.0, 9.0, 8.5}, "entrega puntual");
        DesempenoReport r2 = rs.generarReporte(102, new double[]{5.0, 6.0, 5.5}, "debe mejorar documentacion");

        rs.mostrarReporte(r1);
        rs.mostrarReporte(r2);

        System.out.println("\n=== TASK 3 - pattern matching ===");

        ArrayList<Persona> equipo = new ArrayList<>();
        equipo.add(new Desarrollador(301, "Luis", "Java"));
        equipo.add(new Gerente(302, "Sandra", 8500000));
        equipo.add(new ConsultorExterno(303, "Felipe", "Accenture"));

        ValidadorRol v = new ValidadorRol();

        System.out.println("-- legacy --");
        for (Persona p : equipo) {
            v.validarLegacy(p);
        }

        System.out.println("-- moderno --");
        for (Persona p : equipo) {
            v.validarModerno(p);
        }

        System.out.println("\n=== TASK 4 - interfaz Promocionable ===");

        ArrayList<Promocionable> lista = new ArrayList<>();
        lista.add(new DesarrolladorP(401, "Andres", "Kotlin", 4500000));
        lista.add(new GerenteP(402, "Marcela", 9000000));

        for (Promocionable p : lista) {
            System.out.println("bono: " + p.calcularBonoAscenso());
            if (p instanceof Empleado e) {
                p.registrarLog(e.nombre);
            }
        }
    }
}