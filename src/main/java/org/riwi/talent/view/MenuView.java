package org.riwi.talent.view;

import org.riwi.talent.controller.EmpleadoController;
import org.riwi.talent.dao.ReporteService;
import org.riwi.talent.model.Empleado;
import org.riwi.talent.model.EmpleadoReporteDTO;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final Scanner scanner;
    private final EmpleadoController controller;
    private final ReporteService reporteService;

    public MenuView(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new EmpleadoController();
        this.reporteService = new ReporteService();
    }

    public void mostrarMenuCRUD() {
        boolean continuar = true;
        while (continuar) {
            mostrarOpciones();
            int opcion = leerOpcion();
            switch (opcion) {
                case 1 -> registrarNuevoEmpleado();
                case 2 -> listarTodosLosEmpleados();
                case 3 -> buscarEmpleadoPorId();
                case 4 -> actualizarEmpleado();
                case 5 -> eliminarEmpleado();
                case 6 -> mostrarReporte();
                case 0 -> { continuar = false; System.out.println("Volviendo..."); }
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void mostrarOpciones() {
        System.out.println("\n=== GESTIÓN DE EMPLEADOS (JDBC) ===");
        System.out.println("1. Registrar empleado");
        System.out.println("2. Listar empleados");
        System.out.println("3. Buscar por ID");
        System.out.println("4. Actualizar empleado");
        System.out.println("5. Eliminar empleado");
        System.out.println("6. Reporte consolidado");
        System.out.println("0. Volver");
        System.out.print("Opción: ");
    }

    private int leerOpcion() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { return -1; }
    }

    private void registrarNuevoEmpleado() {
        System.out.println("\n=== REGISTRAR EMPLEADO ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Salario base: ");
        long salario = Long.parseLong(scanner.nextLine());
        System.out.print("Bono mensual: ");
        float bono = Float.parseFloat(scanner.nextLine());
        System.out.print("Género (M/F/O): ");
        char genero = scanner.nextLine().toUpperCase().charAt(0);
        System.out.print("¿Activo? (true/false): ");
        boolean activo = Boolean.parseBoolean(scanner.nextLine());

        if (controller.registrarEmpleado(nombre, salario, bono, genero, activo)) {
            System.out.println("Empleado registrado");
        } else {
            System.out.println("Error al registrar");
        }
    }

    private void listarTodosLosEmpleados() {
        System.out.println("\n=== LISTA DE EMPLEADOS ===");
        List<Empleado> empleados = controller.obtenerTodosLosEmpleados();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados");
            return;
        }
        for (Empleado e : empleados) {
            System.out.printf("ID:%d | %s | $%,d | Bono:$%.2f | %c | %s%n",
                    e.getIdEmpleado(), e.getNombre(), e.getSalarioBase(),
                    e.getBonoMensual(), e.getGenero(), e.isEsActivo() ? "Activo" : "Inactivo");
        }
    }

    private void buscarEmpleadoPorId() {
        System.out.print("\nID a buscar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Empleado e = controller.buscarEmpleado(id);
        if (e != null) {
            System.out.printf("\nID:%d | %s | $%,d%n", e.getIdEmpleado(), e.getNombre(), e.getSalarioBase());
        } else {
            System.out.println("No encontrado");
        }
    }

    private void actualizarEmpleado() {
        System.out.print("\nID a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        Empleado e = controller.buscarEmpleado(id);
        if (e == null) { System.out.println("No encontrado"); return; }

        System.out.print("Nuevo nombre [" + e.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.trim().isEmpty()) nombre = e.getNombre();

        System.out.print("Nuevo salario [" + e.getSalarioBase() + "]: ");
        String salStr = scanner.nextLine();
        long salario = salStr.trim().isEmpty() ? e.getSalarioBase() : Long.parseLong(salStr);

        if (controller.actualizarEmpleado(id, nombre, salario, e.getBonoMensual(), e.getGenero(), e.isEsActivo())) {
            System.out.println("Actualizado");
        }
    }

    private void eliminarEmpleado() {
        System.out.print("\nID a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("¿Confirma? (S/N): ");
        if (scanner.nextLine().equalsIgnoreCase("S")) {
            if (controller.eliminarEmpleado(id)) {
                System.out.println("Eliminado");
            }
        }
    }

    private void mostrarReporte() {
        System.out.println("\n=== REPORTE CONSOLIDADO ===");
        List<EmpleadoReporteDTO> reporte = reporteService.generarReporteConsolidado();
        if (reporte.isEmpty()) { System.out.println("Sin datos"); return; }

        for (EmpleadoReporteDTO r : reporte) {
            System.out.println(r.formatoReporte());
        }

        ReporteService.EstadisticasDTO stats = reporteService.generarEstadisticas();
        System.out.printf("\nTotal: %d | Activos: %d | Promedio: $%,.2f%n",
                stats.totalEmpleados(), stats.empleadosActivos(), stats.salarioPromedio());
    }
}