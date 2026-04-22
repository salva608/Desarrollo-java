package org.riwi;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);
        var option = 0;
        String nombre = "";

        do {
            System.out.println("1.Registrar usuario");
            System.out.println("2.ingresar salario");
            System.out.println("3.salir");
            System.out.println("selecciona una opcion: ");
            option = reader.nextInt();
            reader.nextLine();


            switch (option) {
                case 1:
                    System.out.println("Ingresa nombre: ");
                    nombre = reader.nextLine();
                    System.out.println("Usuario registrado correctamente: " + nombre );
                    break;



                case 2:
                    System.out.print("Ingrese salario: ");
                    double salario = reader.nextDouble();

                    String categoria = obtenerCategoriaSalarial(salario);

                    System.out.println("El usuario " + nombre + " gana un " + categoria);
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
            case 0, 1 -> "No ganas un carajo retirate pa la mrd";
            case 2, 3 -> " salario promedio";
            case 4, 5 -> " salario Alto";
            default -> "Ejecutiva";
        };
    }
}