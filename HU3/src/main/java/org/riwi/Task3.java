package org.riwi;

import java.util.*;

public class Task3 {
    public static void main(String[] args) {

        var lista = new ArrayList<String>();
        lista.add("Ana");
        lista.add("Luis");
        lista.add("Carlos");

        // Legacy (Java 8)
        System.out.println("Primero: " + lista.get(0));
        System.out.println("Último: " + lista.get(lista.size() - 1));

        // Moderno (Java 21)
        System.out.println("Primero (21): " + lista.getFirst());
        System.out.println("Último (21): " + lista.getLast());

        System.out.println("Reversa: " + lista.reversed());


    }
}