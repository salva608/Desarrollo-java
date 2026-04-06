package org.riwi;

import java.util.ArrayList;
import java.util.Scanner;

public class task2 {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        var option = 0;
        String nombre = "";

        // Listas para almacenar datos
        ArrayList<String> empleados = new ArrayList<>();
        ArrayList<Double> salarios = new ArrayList<>();

        do {
            System.out.println("\n1. Registrar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");

            option = reader.nextInt();
            reader.nextLine();

            switch (option) {

                case 1:
                    System.out.print("Ingresa nombre: ");
                    nombre = reader.nextLine();
                    empleados.add(nombre);

                    System.out.print("Ingrese salario: ");
                    double salario = reader.nextDouble();
                    reader.nextLine();

                    salarios.add(salario);

                    String categoria = obtenerCategoriaSalarial(salario);

                    System.out.println("Usuario registrado correctamente: " + nombre);
                    System.out.println("El usuario " + nombre + " gana un salario " + categoria);
                    break;

                case 2:
                    if (empleados.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        System.out.println("\nLista de usuarios:");

                        for (int i = 0; i < empleados.size(); i++) {
                            String nombreEmp = empleados.get(i);
                            double salarioEmp = salarios.get(i);
                            String categoriaEmp = obtenerCategoriaSalarial(salarioEmp);

                            System.out.println("- " + nombreEmp + " | Salario: " + salarioEmp +
                                    " | Categoría: " + categoriaEmp);
                        }
                    }
                    break;

                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida");
                    break;
            }

        } while (option != 3);

        reader.close();
    }

    // 🔹 Switch moderno (Java 17+)
    public static String obtenerCategoriaSalarial(double salario) {
        return switch ((int) salario / 1000000) {
            case 0, 1 -> "Bajo";
            case 2, 3 -> "Promedio";
            case 4, 5 -> "Alto";
            default -> "Ejecutivo";
        };
    }
}