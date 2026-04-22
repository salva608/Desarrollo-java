package org.riwi;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class task4 {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        var option = 0;

        ArrayList<String> empleados = new ArrayList<>();
        ArrayList<Double> salarios = new ArrayList<>();

        do {
            System.out.println("\n1. Registrar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Ingresar salario y ver promoción");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                option = reader.nextInt();
                reader.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número entre 1 y 4.");
                reader.next();
                continue;
            }

            switch (option) {

                case 1:
                    System.out.print("Ingresa nombre: ");
                    String nombre = reader.nextLine();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                        break;
                    }
                    empleados.add(nombre);
                    System.out.println("Usuario registrado correctamente: " + nombre);
                    break;

                case 2:
                    if (empleados.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                    } else {
                        System.out.println("\nLista de usuarios:");
                        for (int i = 0; i < empleados.size(); i++) {
                            String nombreEmp = empleados.get(i);
                            double salarioEmp = (i < salarios.size()) ? salarios.get(i) : 0;
                            String estado = (salarioEmp >= 4000000) ? "Promocionable" : "No promocionable";
                            System.out.println("- " + nombreEmp + " | Salario: " + salarioEmp + " | Estado: " + estado);
                        }
                    }
                    break;

                case 3:
                    if (empleados.isEmpty()) {
                        System.out.println("No hay usuarios registrados.");
                        break;
                    }

                    for (int i = 0; i < empleados.size(); i++) {
                        boolean valido = false;
                        double salario = 0;
                        do {
                            try {
                                System.out.print("Ingrese salario de " + empleados.get(i) + ": ");
                                salario = reader.nextDouble();
                                if (salario < 0) {
                                    System.out.println("El salario no puede ser negativo.");
                                } else {
                                    valido = true;
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Error: Debe ingresar un número válido.");
                                reader.next();
                            }
                        } while (!valido);

                        if (i < salarios.size()) {
                            salarios.set(i, salario);
                        } else {
                            salarios.add(salario);
                        }

                        String estadoPromocion = (salario >= 4000000) ? "Empleado promocionable" : "Empleado no promocionable";
                        System.out.println(empleados.get(i) + " | Salario: " + salario + " | Estado: " + estadoPromocion);
                    }
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida");
                    break;
            }

        } while (option != 4);

        reader.close();
    }
}


// Análisis LTS: En Java 17/21, los mensajes de las excepciones son más detallados y precisos que en Java 8.
// Por ejemplo, InputMismatchException ahora indica el tipo esperado y la posición exacta del error en la entrada,
// facilitando el diagnóstico y depuración de errores, mientras que en Java 8 los mensajes eran más genéricos.