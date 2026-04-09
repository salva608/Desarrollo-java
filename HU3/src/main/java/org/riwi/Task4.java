package org.riwi;

import java.util.*;

class Empleado {
    String id;
    String nombre;
    double salario;
    double puntaje;

    public Empleado(String id, String nombre, double salario, double puntaje) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + nombre + " | Salario: " + salario + " | Puntaje: " + puntaje;
    }
}

public class Task4 {
    public static void main(String[] args) {

        var lista = new ArrayList<Empleado>();

        lista.add(new Empleado("1", "Ana", 2000, 80));
        lista.add(new Empleado("2", "Luis", 1500, 50));
        lista.add(new Empleado("3", "Carlos", 3000, 90));

        lista.removeIf(e -> e.puntaje < 60);

        var total = lista.size();
        var suma = 0.0;

        for (var e : lista) {
            suma += e.salario;
        }

        var promedio = total > 0 ? suma / total : 0;

        System.out.println("Total empleados: " + total);
        System.out.println("Promedio salarios: " + promedio);
    }
}