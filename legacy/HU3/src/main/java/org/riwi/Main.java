package org.riwi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Empleado {
    String id;
    String nombre;
    double salario;

    public Empleado(String id, String nombre, double salario) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Salario: " + salario;
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Empleado> listaEmpleados = new ArrayList<>();
        HashMap<String, Empleado> mapaEmpleados = new HashMap<>();

        int opcion;

        do {
            System.out.println("\n1. Agregar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Eliminar empleado");
            System.out.println("4. Buscar empleado por ID");
            System.out.println("0. Salir");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("ID: ");
                    String id = sc.nextLine();

                    if (mapaEmpleados.containsKey(id)) {
                        System.out.println("El ID ya existe.");
                        break;
                    }

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Salario: ");
                    double salario = sc.nextDouble();
                    sc.nextLine();

                    Empleado nuevo = new Empleado(id, nombre, salario);

                    listaEmpleados.add(nuevo);
                    mapaEmpleados.put(id, nuevo);

                    System.out.println("Empleado agregado.");
                    break;

                case 2:
                    if (listaEmpleados.isEmpty()) {
                        System.out.println("No hay empleados.");
                    } else {
                        for (Empleado e : listaEmpleados) {
                            System.out.println(e);
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID a eliminar: ");
                    String idEliminar = sc.nextLine();

                    Empleado eliminado = mapaEmpleados.remove(idEliminar);

                    if (eliminado != null) {
                        listaEmpleados.remove(eliminado);
                        System.out.println("Empleado eliminado.");
                    } else {
                        System.out.println("No existe ese ID.");
                    }
                    break;

                case 4:
                    System.out.print("ID a buscar: ");
                    String idBuscar = sc.nextLine();

                    Empleado encontrado = mapaEmpleados.get(idBuscar);

                    if (encontrado != null) {
                        System.out.println(encontrado);
                    } else {
                        System.out.println("Empleado no encontrado.");
                    }
                    break;

            }

        } while (opcion != 0);

        sc.close();
    }
}