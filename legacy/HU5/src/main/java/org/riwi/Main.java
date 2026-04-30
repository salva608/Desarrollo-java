package org.riwi;

import org.riwi.dao.ReporteService;
import org.riwi.model.EmpleadoReporteDTO;
import org.riwi.util.DBConnection;
import org.riwi.view.MenuView;

import java.util.List;

/**
 * HISTORIA DE USUARIO M5.1S5
 * Corporate Talent Hub - Persistencia relacional y arquitectura MVC
 * 
 * Esta clase demuestra:
 * - TASK 1: Gestión de conexiones Legacy vs Modern
 * - TASK 2: DAO y CRUD con PreparedStatement
 * - TASK 3: Patrón MVC (Model-View-Controller)
 * - TASK 4: Records para reportes (Java 17+)
 */
public class Main {
    
    public static void main(String[] args) {
        mostrarHeader();
        
        // TASK 1: Verificar conexión a la base de datos
        System.out.println("═══ TASK 1: Verificando conexión a BD ═══");
        DBConnection.testConnection();
        
        System.out.println("\n¿Desea ver un reporte de ejemplo antes de iniciar? (S/N)");
        String respuesta = new java.util.Scanner(System.in).nextLine();
        
        if (respuesta.equalsIgnoreCase("S")) {
            mostrarReporteEjemplo();
        }
        
        // TASK 3: Iniciar aplicación MVC
        System.out.println("\n═══ Iniciando sistema MVC ═══");
        MenuView menu = new MenuView();
        menu.iniciar();
    }
    
    private static void mostrarHeader() {
        String header = """
                
                ╔══════════════════════════════════════════════════════════════╗
                ║                                                              ║
                ║         HISTORIA DE USUARIO M5.1S5 - HU5                     ║
                ║     Corporate Talent Hub - JDBC & MVC Architecture          ║
                ║                                                              ║
                ║  TASKS IMPLEMENTADAS:                                        ║
                ║  ✓ TASK 1: Conexiones Legacy vs Modern (Try-with-resources) ║
                ║  ✓ TASK 2: DAO y CRUD seguro (PreparedStatement)            ║
                ║  ✓ TASK 3: Patrón MVC (Model-View-Controller)               ║
                ║  ✓ TASK 4: Records para DTOs inmutables (Java 17+)          ║
                ║                                                              ║
                ╚══════════════════════════════════════════════════════════════╝
                """;
        System.out.println(header);
    }
    
    /**
     * TASK 4: Demostración de Records con Text Blocks
     */
    private static void mostrarReporteEjemplo() {
        System.out.println("\n═══ TASK 4: REPORTE CONSOLIDADO CON RECORDS ═══\n");
        
        ReporteService reporteService = new ReporteService();
        
        // Generar reporte usando Records
        List<EmpleadoReporteDTO> reporte = reporteService.generarReporteConsolidado();
        
        if (reporte.isEmpty()) {
            System.out.println("📭 No hay datos para mostrar. Registre empleados primero.");
            return;
        }
        
        // TEXT BLOCKS (Java 17+) para formato legible
        String cabecera = """
                ╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ║  ID  │         NOMBRE          │  SALARIO BASE  │    BONO     │ SALARIO TOTAL  │ GÉNERO │  DEPARTAMENTO   │  ESTADO  ║
                ╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣
                """;
        
        System.out.println(cabecera);
        
        // Usar el método del Record para formatear
        for (EmpleadoReporteDTO emp : reporte) {
            System.out.println("║ " + emp.formatoReporte() + " ║");
        }
        
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        
        // Mostrar estadísticas usando otro Record
        ReporteService.EstadisticasDTO stats = reporteService.generarEstadisticas();
        
        String estadisticas = String.format("""
                
                ┌─────────────── ESTADÍSTICAS ───────────────┐
                │ Total de empleados:        %,15d │
                │ Empleados activos:         %,15d │
                │ Empleados inactivos:       %,15d │
                │ Porcentaje activos:        %14.1f%% │
                │ Salario promedio:          $%,14.2f │
                │ Salario máximo:            $%,14d │
                │ Salario mínimo:            $%,14d │
                └────────────────────────────────────────────┘
                """,
                stats.totalEmpleados(),
                stats.empleadosActivos(),
                stats.empleadosInactivos(),
                stats.porcentajeActivos(),
                stats.salarioPromedio(),
                stats.salarioMaximo(),
                stats.salarioMinimo()
        );
        
        System.out.println(estadisticas);
        
        // Análisis de Records vs POJO
        String analisis = """
                
                ╔════════════════════════════════════════════════════════════════╗
                ║  ANÁLISIS: Records vs POJO tradicionales (Java 8)             ║
                ╠════════════════════════════════════════════════════════════════╣
                ║                                                                ║
                ║  ✓ VENTAJAS DE RECORDS:                                        ║
                ║    • Código 90% más conciso (1 línea vs 40+ líneas)           ║
                ║    • Inmutabilidad por diseño = Thread-safe                   ║
                ║    • equals/hashCode/toString generados correctamente         ║
                ║    • Semántica clara: "esto es solo datos"                    ║
                ║    • Ideal para DTOs y ResultSets de JDBC                     ║
                ║                                                                ║
                ║  ✓ COMBINACIÓN Records + JDBC moderno:                        ║
                ║    • Try-with-resources previene memory leaks                 ║
                ║    • Records aseguran inmutabilidad de resultados             ║
                ║    • Mapeo directo ResultSet → Record tipo-seguro             ║
                ║    • Mantenimiento simplificado del código                    ║
                ║                                                                ║
                ║  vs POJO Java 8:                                               ║
                ║    ✗ Mucho boilerplate (getters, setters, constructores)      ║
                ║    ✗ Mutables por defecto (no thread-safe)                    ║
                ║    ✗ Propenso a errores (olvidar actualizar equals/hashCode) ║
                ║    ✗ Más código para mantener                                 ║
                ║                                                                ║
                ╚════════════════════════════════════════════════════════════════╝
                """;
        
        System.out.println(analisis);
    }
}
