package org.riwi;

// java 8/11 - herencia abierta, cualquier clase puede extender PersonaLegacy
abstract class PersonaLegacy {
    protected int id;
    protected String nombre;

    public PersonaLegacy(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public abstract String obtenerRol();

    @Override
    public String toString() {
        return obtenerRol() + " - " + nombre + " (id: " + id + ")";
    }
}

class EmpleadoLegacy extends PersonaLegacy {
    public EmpleadoLegacy(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public String obtenerRol() {
        return "Empleado";
    }
}

// java 17/21 - con sealed solo Empleado y ConsultorExterno pueden heredar
// a diferencia de la herencia abierta, nadie puede meter un tipo raro
// sin que el compilador lo detecte, eso protege mejor el diseno de la api
sealed class Persona permits Empleado, ConsultorExterno {
    protected final int id;
    protected final String nombre;

    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public abstract String obtenerRol();

    @Override
    public String toString() {
        return obtenerRol() + " - " + nombre + " (id: " + id + ")";
    }
}

non-sealed class Empleado extends Persona {
    private final String cargo;

    public Empleado(int id, String nombre, String cargo) {
        super(id, nombre);
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String obtenerRol() {
        return "Empleado - " + cargo;
    }
}

final class ConsultorExterno extends Persona {
    private final String empresa;

    public ConsultorExterno(int id, String nombre, String empresa) {
        super(id, nombre);
        this.empresa = empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    @Override
    public String obtenerRol() {
        return "Consultor de " + empresa;
    }
}