package org.riwi.talent.model;

public class Empleado {
    private int idEmpleado;
    private String nombre;
    private long salarioBase;
    private float bonoMensual;
    private char genero;
    private boolean esActivo;

    public Empleado() {}

    public Empleado(int idEmpleado, String nombre, long salarioBase,
                    float bonoMensual, char genero, boolean esActivo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
        this.bonoMensual = bonoMensual;
        this.genero = genero;
        this.esActivo = esActivo;
    }

    public Empleado(String nombre, long salarioBase, float bonoMensual,
                    char genero, boolean esActivo) {
        this.nombre = nombre;
        this.salarioBase = salarioBase;
        this.bonoMensual = bonoMensual;
        this.genero = genero;
        this.esActivo = esActivo;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public long getSalarioBase() { return salarioBase; }
    public void setSalarioBase(long salarioBase) { this.salarioBase = salarioBase; }

    public float getBonoMensual() { return bonoMensual; }
    public void setBonoMensual(float bonoMensual) { this.bonoMensual = bonoMensual; }

    public char getGenero() { return genero; }
    public void setGenero(char genero) { this.genero = genero; }

    public boolean isEsActivo() { return esActivo; }
    public void setEsActivo(boolean esActivo) { this.esActivo = esActivo; }
}