package org.riwi.talent.model;

public record EmpresaRecord(String nombre, String nit, int anioFundacion) {
    /*
     * Record:
     * - Inmutable (los datos no cambian)
     * - Menos código (no requiere getters ni constructores manuales)
     */
}

