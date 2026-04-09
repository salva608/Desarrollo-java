package org.riwi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var sc = new Scanner(System.in);

        var listaEmpleados = new ArrayList<Empleado>();
        var mapaEmpleados = new HashMap<String, Empleado>();

        int opcion;

        do {
            System.out.println("\n1. Agregar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Eliminar empleado");
            System.out.println("4. Buscar empleado por ID");
            System.out.println("5. Filtrar por puntaje (Task 4)");
            System.out.println("6. Reporte (Task 4)");
            System.out.println("0. Salir");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1 -> {
                    System.out.print("ID: ");
                    var id = sc.nextLine();

                    if (mapaEmpleados.containsKey(id)) {
                        System.out.println("El ID ya existe.");
                        break;
                    }

                    System.out.print("Nombre: ");
                    var nombre = sc.nextLine();

                    System.out.print("Salario: ");
                    var salario = sc.nextDouble();

                    System.out.print("Puntaje: ");
                    var puntaje = sc.nextDouble();
                    sc.nextLine();

                    var emp = new Empleado(id, nombre, salario, puntaje);

                    listaEmpleados.add(emp);
                    mapaEmpleados.put(id, emp);

                    System.out.println("Empleado agregado.");
                }

                case 2 -> {
                    if (listaEmpleados.isEmpty()) {
                        System.out.println("No hay empleados.");
                    } else {
                        listaEmpleados.forEach(System.out::println);
                    }
                }

                case 3 -> {
                    System.out.print("ID a eliminar: ");
                    var idEliminar = sc.nextLine();

                    var eliminado = mapaEmpleados.remove(idEliminar);

                    if (eliminado != null) {
                        listaEmpleados.remove(eliminado);
                        System.out.println("Empleado eliminado.");
                    } else {
                        System.out.println("No existe ese ID.");
                    }
                }

                case 4 -> {
                    System.out.print("ID a buscar: ");
                    var idBuscar = sc.nextLine();

                    var encontrado = mapaEmpleados.get(idBuscar);

                    if (encontrado != null) {
                        System.out.println(encontrado);
                    } else {
                        System.out.println("Empleado no encontrado.");
                    }
                }

                // 🔥 TASK 4 → removeIf
                case 5 -> {
                    System.out.print("Puntaje mínimo: ");
                    var min = sc.nextDouble();
                    sc.nextLine();

                    listaEmpleados.removeIf(e -> e.puntaje < min);
                    System.out.println("Filtrado aplicado.");
                }

                // 🔥 TASK 4 → reporte
                case 6 -> {
                    var total = listaEmpleados.size();
                    var suma = 0.0;

                    for (var e : listaEmpleados) {
                        suma += e.salario;
                    }

                    var promedio = total > 0 ? suma / total : 0;

                    System.out.println("Total empleados: " + total);
                    System.out.println("Promedio salarios: " + promedio);
                }
            }

        } while (opcion != 0);

        sc.close();
    }
}