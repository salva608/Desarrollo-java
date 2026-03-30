package HU1;

public class NotasArquitectura {
       /*
     * Java 8 (Legacy) vs Java 17/21 (LTS)
     * -----------------------------------
     * Java 8:
     * - Código más verboso (getters, setters, constructores manuales).
     * - No existen Records.
     * - Manejo de errores menos descriptivo (NullPointerException básico).
     * - Menor optimización del Garbage Collector.
     *
     * Java 17/21:
     * - Introduce Records → menos código, más claridad.
     * - Text Blocks ("""") para strings multilínea.
     * - Helpful NullPointerExceptions (Java 14+) muestran exactamente qué variable es null.
     * - Mejoras en Garbage Collector (G1, ZGC, Shenandoah).
     *
     * JVM y Garbage Collector:
     * -------------------------
     * La JVM gestiona la memoria en el Heap.
     * Los objetos (como Empleado) se crean en el Heap.
     * El Garbage Collector elimina objetos sin referencia para liberar memoria.
     * Esto optimiza el rendimiento y evita fugas de memoria.
     */
}
