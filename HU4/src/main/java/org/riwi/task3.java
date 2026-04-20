package org.riwi;

class Desarrollador extends Empleado {
    private final String lenguajePrincipal;

    public Desarrollador(int id, String nombre, String lenguajePrincipal) {
        super(id, nombre, "Desarrollador");
        this.lenguajePrincipal = lenguajePrincipal;
    }

    public String getLenguajePrincipal() {
        return lenguajePrincipal;
    }

    @Override
    public String obtenerRol() {
        return "Desarrollador - lenguaje: " + lenguajePrincipal;
    }
}

class Gerente extends Empleado {
    private final double presupuestoMensual;

    public Gerente(int id, String nombre, double presupuestoMensual) {
        super(id, nombre, "Gerente");
        this.presupuestoMensual = presupuestoMensual;
    }

    public double getPresupuestoMensual() {
        return presupuestoMensual;
    }

    @Override
    public String obtenerRol() {
        return "Gerente - presupuesto: " + presupuestoMensual;
    }
}

class ValidadorRol {

    // java 8/11 - instanceof y luego cast manual obligatorio
    public void validarLegacy(Persona p) {
        System.out.println("validando (legacy): " + p.nombre);

        if (p instanceof Desarrollador) {
            Desarrollador dev = (Desarrollador) p;
            System.out.println("  lenguaje: " + dev.getLenguajePrincipal());
        } else if (p instanceof Gerente) {
            Gerente ger = (Gerente) p;
            System.out.println("  presupuesto: " + ger.getPresupuestoMensual());
        } else if (p instanceof ConsultorExterno) {
            ConsultorExterno con = (ConsultorExterno) p;
            System.out.println("  empresa: " + con.getEmpresa());
        }
    }

    // java 17/21 - pattern matching, el cast queda en la misma linea
    public void validarModerno(Persona p) {
        System.out.println("validando (moderno): " + p.nombre);

        if (p instanceof Desarrollador dev) {
            System.out.println("  lenguaje: " + dev.getLenguajePrincipal());
        } else if (p instanceof Gerente ger) {
            System.out.println("  presupuesto: " + ger.getPresupuestoMensual());
        } else if (p instanceof ConsultorExterno con) {
            System.out.println("  empresa: " + con.getEmpresa());
        }
    }
}