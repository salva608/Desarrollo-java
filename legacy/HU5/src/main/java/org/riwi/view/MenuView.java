package org.riwi.view;

import org.riwi.controller.EmpleadoController;
import org.riwi.model.Empleado;

import java.util.List;
import java.util.Scanner;

/**
 * TASK 3: Vista - Capa de presentación
 * 
 * PATRÓN MVC - RESPONSABILIDADES DE LA VISTA:
 * - Interactuar con el usuario (Scanner, System.out)
 * - Capturar entradas del usuario
 * - Mostrar información de manera legible
 * - Delegar la lógica al Controlador
 * 
 * NO debe:
 * - Contener lógica de negocio
 * - Acceder directamente a la base de datos
 * - Realizar validaciones complejas (solo formato básico)
 */
public class MenuView {
    
    private final Scanner scanner;
    private final EmpleadoController controller;
    
    public MenuView() {
        this.scanner = new Scanner(System.in);
        this.controller = new EmpleadoController();
    }
    
    /**
     * Inicia la aplicación
     */
    public void iniciar() {
        mostrarBienvenida();
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1 -> registrarNuevoEmpleado();
                case 2 -> listarTodosLosEmpleados();
                case 3 -> buscarEmpleadoPorId();
                case 4 -> actualizarEmpleado();
                case 5 -> eliminarEmpleado();
                case 6 -> mostrarReporte();
                case 0 -> {
                    continuar = false;
                    System.out.println("\n¡Hasta pronto!");
                }
                default -> System.out.println("❌ Opción inválida");
            }
        }
        
        scanner.close();
    }
    
    private void mostrarBienvenida() {
        String banner = """
                
                ╔════════════════════════════════════════╗
                ║   CORPORATE TALENT HUB SYSTEM v5.0    ║
                ║        Gestión de Empleados JDBC      ║
                ╚════════════════════════════════════════╝
                """;
        System.out.println(banner);
    }
    
    private void mostrarMenu() {
        String menu = """
                
                ┌─────────────── MENÚ PRINCIPAL ───────────────┐
                │  1. ➕ Registrar nuevo empleado              │
                │  2. 📋 Listar todos los empleados            │
                │  3. 🔍 Buscar empleado por ID                │
                │  4. ✏️  Actualizar empleado                   │
                │  5. 🗑️  Eliminar empleado                     │
                │  6. 📊 Generar reporte consolidado           │
                │  0. 🚪 Salir                                  │
                └──────────────────────────────────────────────┘
                """;
        System.out.println(menu);
        System.out.print("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * CASO DE USO: Registrar empleado
     * La Vista captura datos y los envía al Controlador
     */
    private void registrarNuevoEmpleado() {
        System.out.println("\n═══ REGISTRAR NUEVO EMPLEADO ═══");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Salario base: ");
        long salarioBase = Long.parseLong(scanner.nextLine());
        
        System.out.print("Bono mensual: ");
        float bonoMensual = Float.parseFloat(scanner.nextLine());
        
        System.out.print("Género (M/F/O): ");
        char genero = scanner.nextLine().toUpperCase().charAt(0);
        
        System.out.print("¿Está activo? (true/false): ");
        boolean esActivo = Boolean.parseBoolean(scanner.nextLine());
        
        // Delegar al controlador (SIN lógica de negocio en la vista)
        boolean exito = controller.registrarEmpleado(nombre, salarioBase, bonoMensual, genero, esActivo);
        
        if (exito) {
            System.out.println("✅ Empleado registrado exitosamente");
        } else {
            System.out.println("❌ Error al registrar empleado");
        }
    }
    
    /**
     * CASO DE USO: Listar empleados
     */
    private void listarTodosLosEmpleados() {
        System.out.println("\n═══ LISTA DE EMPLEADOS ═══");
        
        List<Empleado> empleados = controller.obtenerTodosLosEmpleados();
        
        if (empleados.isEmpty()) {
            System.out.println("📭 No hay empleados registrados");
            return;
        }
        
        System.out.println("─".repeat(100));
        System.out.printf("%-5s %-25s %-15s %-12s %-8s %-8s%n",
                "ID", "NOMBRE", "SALARIO BASE", "BONO", "GÉNERO", "ACTIVO");
        System.out.println("─".repeat(100));
        
        for (Empleado emp : empleados) {
            System.out.printf("%-5d %-25s $%-14,d $%-11,.2f %-8c %-8s%n",
                    emp.getIdEmpleado(),
                    emp.getNombre(),
                    emp.getSalarioBase(),
                    emp.getBonoMensual(),
                    emp.getGenero(),
                    emp.isEsActivo() ? "✓" : "✗");
        }
        System.out.println("─".repeat(100));
        System.out.println("Total: " + empleados.size() + " empleados");
    }
    
    /**
     * CASO DE USO: Buscar empleado
     */
    private void buscarEmpleadoPorId() {
        System.out.println("\n═══ BUSCAR EMPLEADO ═══");
        System.out.print("Ingrese el ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Empleado empleado = controller.buscarEmpleado(id);
        
        if (empleado != null) {
            System.out.println("\n✓ Empleado encontrado:");
            mostrarDetalleEmpleado(empleado);
        } else {
            System.out.println("❌ Empleado no encontrado");
        }
    }
    
    /**
     * CASO DE USO: Actualizar empleado
     */
    private void actualizarEmpleado() {
        System.out.println("\n═══ ACTUALIZAR EMPLEADO ═══");
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        // Mostrar datos actuales
        Empleado empleado = controller.buscarEmpleado(id);
        if (empleado == null) {
            System.out.println("❌ Empleado no encontrado");
            return;
        }
        
        System.out.println("\nDatos actuales:");
        mostrarDetalleEmpleado(empleado);
        
        System.out.println("\nIngrese los nuevos datos:");
        
        System.out.print("Nombre [" + empleado.getNombre() + "]: ");
        String nombre = scanner.nextLine();
        if (nombre.trim().isEmpty()) nombre = empleado.getNombre();
        
        System.out.print("Salario base [" + empleado.getSalarioBase() + "]: ");
        String salarioStr = scanner.nextLine();
        long salarioBase = salarioStr.trim().isEmpty() ? empleado.getSalarioBase() : Long.parseLong(salarioStr);
        
        System.out.print("Bono mensual [" + empleado.getBonoMensual() + "]: ");
        String bonoStr = scanner.nextLine();
        float bonoMensual = bonoStr.trim().isEmpty() ? empleado.getBonoMensual() : Float.parseFloat(bonoStr);
        
        System.out.print("Género [" + empleado.getGenero() + "]: ");
        String generoStr = scanner.nextLine();
        char genero = generoStr.trim().isEmpty() ? empleado.getGenero() : generoStr.toUpperCase().charAt(0);
        
        System.out.print("¿Activo? [" + empleado.isEsActivo() + "]: ");
        String activoStr = scanner.nextLine();
        boolean esActivo = activoStr.trim().isEmpty() ? empleado.isEsActivo() : Boolean.parseBoolean(activoStr);
        
        boolean exito = controller.actualizarEmpleado(id, nombre, salarioBase, bonoMensual, genero, esActivo);
        
        if (exito) {
            System.out.println("✅ Empleado actualizado exitosamente");
        } else {
            System.out.println("❌ Error al actualizar empleado");
        }
    }
    
    /**
     * CASO DE USO: Eliminar empleado
     */
    private void eliminarEmpleado() {
        System.out.println("\n═══ ELIMINAR EMPLEADO ═══");
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Empleado empleado = controller.buscarEmpleado(id);
        if (empleado == null) {
            System.out.println("❌ Empleado no encontrado");
            return;
        }
        
        mostrarDetalleEmpleado(empleado);
        System.out.print("\n¿Está seguro de eliminar este empleado? (S/N): ");
        String confirmacion = scanner.nextLine();
        
        if (confirmacion.equalsIgnoreCase("S")) {
            boolean exito = controller.eliminarEmpleado(id);
            if (exito) {
                System.out.println("✅ Empleado eliminado exitosamente");
            } else {
                System.out.println("❌ Error al eliminar empleado");
            }
        } else {
            System.out.println("ℹ️ Operación cancelada");
        }
    }
    
    /**
     * TASK 4: Reporte con Records (se implementará en el siguiente archivo)
     */
    private void mostrarReporte() {
        System.out.println("\n📊 Generando reporte...");
        System.out.println("(Funcionalidad implementada en TASK 4)");
    }
    
    /**
     * Método auxiliar para mostrar detalles de un empleado
     */
    private void mostrarDetalleEmpleado(Empleado emp) {
        String detalle = String.format("""
                ┌──────────────────────────────────────┐
                │ ID:            %-21d │
                │ Nombre:        %-21s │
                │ Salario Base:  $%-20,d │
                │ Bono Mensual:  $%-20,.2f │
                │ Género:        %-21c │
                │ Activo:        %-21s │
                │ Salario Total: $%-20,.2f │
                └──────────────────────────────────────┘
                """,
                emp.getIdEmpleado(),
                emp.getNombre(),
                emp.getSalarioBase(),
                emp.getBonoMensual(),
                emp.getGenero(),
                emp.isEsActivo() ? "Sí" : "No",
                controller.calcularSalarioTotal(emp.getIdEmpleado()));
        
        System.out.println(detalle);
    }
}
