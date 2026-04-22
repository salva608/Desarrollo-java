package org.riwi;

// java 8/11 - pojo tradicional, hay que escribir todo a mano
class DesempenoReportLegacy {
    private int idEmpleado;
    private double promedio;
    private String feedback;

    public DesempenoReportLegacy(int idEmpleado, double promedio, String feedback) {
        this.idEmpleado = idEmpleado;
        this.promedio = promedio;
        this.feedback = feedback;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public double getPromedio() { return promedio; }
    public String getFeedback() { return feedback; }

    @Override
    public String toString() {
        return "id=" + idEmpleado + ", promedio=" + promedio + ", feedback=" + feedback;
    }
}

// java 17/21 - record, el compilador genera el constructor, getters, equals, hashCode y toString
// ademas es inmutable, una vez creado no se puede modificar
record DesempenoReport(int idEmpleado, double promedio, String feedback) {

    // constructor compacto para validar los datos antes de crear el objeto
    DesempenoReport {
        if (promedio < 0 || promedio > 10) {
            throw new IllegalArgumentException("el promedio debe estar entre 0 y 10");
        }
    }

    public String nivel() {
        if (promedio >= 9.0) return "sobresaliente";
        if (promedio >= 7.0) return "bueno";
        if (promedio >= 5.0) return "regular";
        return "insuficiente";
    }
}

// servicio que genera los reportes de fin de mes
class ReporteService {

    public DesempenoReport generarReporte(int idEmpleado, double[] notas, String feedback) {
        double suma = 0;
        for (double n : notas) {
            suma += n;
        }
        double promedio = Math.round((suma / notas.length) * 100.0) / 100.0;
        return new DesempenoReport(idEmpleado, promedio, feedback);
    }

    public void mostrarReporte(DesempenoReport r) {
        System.out.println("empleado id: " + r.idEmpleado());
        System.out.println("promedio: " + r.promedio());
        System.out.println("nivel: " + r.nivel());
        System.out.println("feedback: " + r.feedback());
        System.out.println("-----------------------------");
    }
}