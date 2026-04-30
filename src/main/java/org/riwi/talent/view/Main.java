package org.riwi.talent.view;

import org.riwi.talent.util.DBConnection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DBConnection.testConnection();

        System.out.println("\n=== CORPORATE TALENT HUB v5.0 ===");

        boolean continuar = true;
        while (continuar) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> ejecutarHU2(scanner);
                case 2 -> ejecutarHU3(scanner);
                case 3 -> ejecutarHU4(scanner);
                case 4 -> ejecutarHU5(scanner);
                case 0 -> { continuar = false; System.out.println("Hasta pronto"); }
                default -> System.out.println("Opción inválida");
            }
        }
        scanner.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. HU2 - Tipos de Datos");
        System.out.println("2. HU3 - Operadores");
        System.out.println("3. HU4 - Estructuras Control");
        System.out.println("4. HU5 - CRUD JDBC");
        System.out.println("0. Salir");
        System.out.print("Opción: ");
    }

    private static int leerOpcion(Scanner scanner) {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { return -1; }
    }

    private static void ejecutarHU2(Scanner scanner) {
        System.out.println("\n=== HU2 ===");
        // TU CÓDIGO DE HU2 AQUÍ
        esperarEnter(scanner);
    }

    private static void ejecutarHU3(Scanner scanner) {
        System.out.println("\n=== HU3 ===");
        // TU CÓDIGO DE HU3 AQUÍ
        esperarEnter(scanner);
    }

    private static void ejecutarHU4(Scanner scanner) {
        System.out.println("\n=== HU4 ===");
        // TU CÓDIGO DE HU4 AQUÍ
        esperarEnter(scanner);
    }

    private static void ejecutarHU5(Scanner scanner) {
        System.out.println("\n=== HU5: CRUD JDBC ===");
        DBConnection.testConnection();
        MenuView menuView = new MenuView(scanner);
        menuView.mostrarMenuCRUD();
    }

    private static void esperarEnter(Scanner scanner) {
        System.out.print("\nPresione Enter...");
        scanner.nextLine();
    }
}