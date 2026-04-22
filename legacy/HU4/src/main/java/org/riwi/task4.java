package org.riwi;

// interfaz con metodo abstracto para calcular el bono de ascenso
interface Promocionable {

    double calcularBonoAscenso();

    // metodo default - java 8 permitio agregar metodos con implementacion a las interfaces
    // sin romper las clases que ya la usaban
    default void registrarLog(String nombre) {
        System.out.println("log - promocion de: " + nombre + " | bono: " + calcularBonoAscenso());
    }
}

class DesarrolladorP extends Desarrollador implements Promocionable {
    private final double salarioBase;

    public DesarrolladorP(int id, String nombre, String lenguaje, double salarioBase) {
        super(id, nombre, lenguaje);
        this.salarioBase = salarioBase;
    }

    @Override
    public double calcularBonoAscenso() {
        return salarioBase * 0.15;
    }
}

class GerenteP extends Gerente implements Promocionable {

    public GerenteP(int id, String nombre, double presupuesto) {
        super(id, nombre, presupuesto);
    }

    @Override
    public double calcularBonoAscenso() {
        return getPresupuestoMensual() * 0.20;
    }
}