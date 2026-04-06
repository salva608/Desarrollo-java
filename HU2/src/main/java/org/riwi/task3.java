package org.riwi;

import java.util.ArrayList;
import java.util.Scanner;

public class task3 {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        var option = 0;

        ArrayList<String> empleados = new ArrayList<>();
        ArrayList<Double> salarios = new ArrayList<>();

        double[][] notas = new double[100][3];
        int contador = 0;

        do {
            System.out.println("\n1. Registrar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Ver desempeño");
            System.out.println("4. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                option = reader.nextInt();
                reader.nextLine();
            } catch (Exception e) {
                System.out.println("Entrada inválida");
                reader.nextLine();
                continue;
            }

            switch (option) {

                case 1:
                    System.out.print("Nombre: ");
                    String nombre = reader.nextLine();

                    empleados.add(nombre);

                    System.out.print("Salario: ");
                    double salario = reader.nextDouble();
                    reader.nextLine();

                    salarios.add(salario);


                    for (int i = 0; i < 3; i++) {
                        System.out.print("Nota trimestre " + (i + 1) + ": ");
                        notas[contador][i] = reader.nextDouble();
                    }
                    reader.nextLine();

                    contador++;
                    break;

                case 2:
                    for (int i = 0; i < empleados.size(); i++) {
                        System.out.println(empleados.get(i) + " - $" + salarios.get(i));
                    }
                    break;

                case 3:
                    System.out.println("\nDesempeño de empleados:");

                    for (int i = 0; i < contador; i++) {

                        double suma = 0;

                        for (int j = 0; j < 3; j++) {
                            suma += notas[i][j];
                        }

                        double promedio = suma / 3;


                        int puntaje = (int) promedio;

                        System.out.println(empleados.get(i) +
                                " | Promedio: " + promedio +
                                " | Puntaje simplificado: " + puntaje);
                    }
                    break;

                case 4:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida");
            }

        } while (option != 4);

        reader.close();
    }
}