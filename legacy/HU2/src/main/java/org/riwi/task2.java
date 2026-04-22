package org.riwi;

import java.util.ArrayList;
import java.util.Scanner;

public class task2 {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        var option = 0;
        String nombre = "";

        ArrayList<String> empleados = new ArrayList<>();
        ArrayList<Double> salarios = new ArrayList<>();

        do {
            System.out.println("1. Registrar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opcion: ");

            try {
                option = reader.nextInt();
                reader.nextLine();
            } catch (Exception e) {
                System.out.println("Error: debes ingresar un número válido.");
                reader.nextLine();
                continue;
            }

            switch (option) {

                case 1:
                    System.out.print("Ingresa nombre: ");
                    nombre = reader.nextLine();

                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                        break;
                    }

                    empleados.add(nombre);

                    double salario;

                    try {
                        System.out.print("Ingrese salario: ");
                        salario = reader.nextDouble();
                        reader.nextLine();
                    } catch (Exception e) {
                        System.out.println("Error: salario inválido.");
                        reader.nextLine();
                        break;
                    }

                    if (salario <= 0) {
                        System.out.println("El salario debe ser mayor a 0.");
                        break;
                    }

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

                            System.out.println("- " + nombreEmp +
                                    " | Salario: " + salarioEmp +
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

    public static String obtenerCategoriaSalarial(double salario) {
        return switch ((int) salario / 1000000) {
            case 0, 1 -> "Bajo";
            case 2, 3 -> "Promedio";
            case 4, 5 -> "Alto";
            default -> "Ejecutivo";
        };
    }
}