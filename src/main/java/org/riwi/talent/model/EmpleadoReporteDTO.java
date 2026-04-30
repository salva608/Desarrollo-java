package org.riwi.talent.model;

public record EmpleadoReporteDTO(
        int idEmpleado,
        String nombre,
        long salarioBase,
        float bonoMensual,
        double salarioTotal,
        char genero,
        boolean esActivo,
        String departamento
) {
    public String obtenerEstadoActividad() {
        return esActivo ? "ACTIVO" : "INACTIVO";
    }

    public String formatoReporte() {
        return String.format(
                "%-5d | %-25s | $%-14,d | $%-11,.2f | $%-14,.2f | %-8c | %-15s | %-8s",
                idEmpleado, nombre, salarioBase, bonoMensual, salarioTotal,
                genero, departamento, obtenerEstadoActividad()
        );
    }
}