package org.riwi.model;

/**
 * TASK 4: Record para transferencia de datos inmutables
 * 
 * Records (Java 17+) son clases especiales para almacenar datos inmutables.
 * 
 * VENTAJAS DE RECORDS vs POJO tradicionales (Java 8):
 * 
 * 1. CONCISIÓN:
 *    - Record: 1 línea de código
 *    - POJO: 50+ líneas (campos, constructor, getters, equals, hashCode, toString)
 * 
 * 2. INMUTABILIDAD por defecto:
 *    - Los campos son final automáticamente
 *    - No hay setters
 *    - Thread-safe sin esfuerzo adicional
 * 
 * 3. SEMÁNTICA CLARA:
 *    - Record indica explícitamente: "esto es solo datos"
 *    - POJO puede confundirse con entidad de dominio
 * 
 * 4. MENOS PROPENSO A ERRORES:
 *    - equals() y hashCode() generados correctamente
 *    - No se puede olvidar actualizar toString()
 * 
 * 5. MEJOR RENDIMIENTO:
 *    - Menos bytecode generado
 *    - Optimizaciones del compilador
 * 
 * CUANDO USAR RECORDS:
 * - DTOs (Data Transfer Objects)
 * - Resultados de consultas SQL
 * - Configuraciones
 * - Valores de retorno de múltiples campos
 * 
 * CUANDO NO USAR RECORDS:
 * - Entidades JPA (necesitan setters)
 * - Objetos que cambian de estado
 * - Clases con lógica de negocio compleja
 */
public record EmpleadoReporteDTO(
        int idEmpleado,
        String nombre,
        long salarioBase,
        float bonoMensual,
        double salarioTotal,
        char genero,
        boolean esActivo,
        String departamento  // Asumiendo JOIN con tabla departamentos
) {
    
    /**
     * Constructor compacto - Validaciones opcionales
     */
    public EmpleadoReporteDTO {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (salarioBase < 0) {
            throw new IllegalArgumentException("El salario no puede ser negativo");
        }
    }
    
    /**
     * Método personalizado - Los records pueden tener métodos
     */
    public String obtenerEstadoActividad() {
        return esActivo ? "ACTIVO" : "INACTIVO";
    }
    
    /**
     * Formato para reporte
     */
    public String formatoReporte() {
        return String.format(
            "%-5d | %-25s | $%-14,d | $%-11,.2f | $%-14,.2f | %-8c | %-15s | %-8s",
            idEmpleado, nombre, salarioBase, bonoMensual, salarioTotal,
            genero, departamento, obtenerEstadoActividad()
        );
    }
}

/*
 * ============================================================================
 * COMPARACIÓN: Record vs POJO tradicional (Java 8)
 * ============================================================================
 * 
 * RECORD (Java 17+):
 * ----------------
 * public record EmpleadoReporteDTO(int id, String nombre, long salario) {}
 * 
 * Genera automáticamente:
 * - Constructor
 * - Getters (id(), nombre(), salario())
 * - equals()
 * - hashCode()
 * - toString()
 * 
 * Total: 1 línea de código
 * 
 * ============================================================================
 * 
 * POJO EQUIVALENTE (Java 8):
 * -------------------------
 * public class EmpleadoReporteDTO {
 *     private final int id;
 *     private final String nombre;
 *     private final long salario;
 *     
 *     public EmpleadoReporteDTO(int id, String nombre, long salario) {
 *         this.id = id;
 *         this.nombre = nombre;
 *         this.salario = salario;
 *     }
 *     
 *     public int getId() { return id; }
 *     public String getNombre() { return nombre; }
 *     public long getSalario() { return salario; }
 *     
 *     @Override
 *     public boolean equals(Object o) {
 *         if (this == o) return true;
 *         if (o == null || getClass() != o.getClass()) return false;
 *         EmpleadoReporteDTO that = (EmpleadoReporteDTO) o;
 *         return id == that.id &&
 *                salario == that.salario &&
 *                Objects.equals(nombre, that.nombre);
 *     }
 *     
 *     @Override
 *     public int hashCode() {
 *         return Objects.hash(id, nombre, salario);
 *     }
 *     
 *     @Override
 *     public String toString() {
 *         return "EmpleadoReporteDTO{" +
 *                "id=" + id +
 *                ", nombre='" + nombre + '\'' +
 *                ", salario=" + salario +
 *                '}';
 *     }
 * }
 * 
 * Total: ~40 líneas de código
 * Propenso a errores si se agrega un campo y se olvida actualizar equals/hashCode
 * 
 * ============================================================================
 * 
 * VENTAJA EN JDBC:
 * ---------------
 * Con Record:
 * return new EmpleadoReporteDTO(
 *     rs.getInt("id"),
 *     rs.getString("nombre"),
 *     rs.getLong("salario")
 * );
 * 
 * Inmutable por diseño, thread-safe, menos boilerplate.
 * Perfecto para mapear ResultSets a objetos.
 * 
 * ============================================================================
 */
