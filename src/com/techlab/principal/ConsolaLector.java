package com.techlab.principal;

import java.util.Scanner;

// Utilitario de lectura y validacion de entradas por consola. 
// Previene fallos por tipos de datos incorrectos y asegura que las entradas cumplan con las reglas de negocio de TechLab.

public class ConsolaLector {

    private static Scanner explorador = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        String textoEntrada = "";
        boolean validez = false;

        while (!validez) {
            System.out.print(mensaje);
            textoEntrada = explorador.nextLine().trim();
            if (!textoEntrada.isEmpty()) {
                validez = true;
            } else {
                System.out.println("[Error] El texto ingresado no puede estar vacio. Intente nuevamente.");
            }
        }
        return textoEntrada;
    }

    public static int leerEntero(String mensaje) {
        int numero = 0;
        boolean validez = false;

        while (!validez) {
            System.out.print(mensaje);
            String textoEntrada = explorador.nextLine().trim();
            try {
                numero = Integer.parseInt(textoEntrada);
                validez = true;
            } catch (NumberFormatException excepcion) {
                System.out.println("[Error] Debe ingresar un numero entero valido. Intente nuevamente.");
            }
        }
        return numero;
    }

    public static int leerEnteroNoNegativo(String mensaje) {
        int numero = 0;
        boolean validez = false;

        while (!validez) {
            numero = leerEntero(mensaje);
            if (numero >= 0) {
                validez = true;
            } else {
                System.out.println("[Error] El numero no puede ser negativo. Intente nuevamente.");
            }
        }
        return numero;
    }

    public static int leerEnteroPositivo(String mensaje) {
        int numero = 0;
        boolean validez = false;

        while (!validez) {
            numero = leerEntero(mensaje);
            if (numero > 0) {
                validez = true;
            } else {
                System.out.println("[Error] El numero debe ser estrictamente mayor a cero. Intente nuevamente.");
            }
        }
        return numero;
    }

    public static double leerDecimalNoNegativo(String mensaje) {
        double numero = 0.0;
        boolean validez = false;

        while (!validez) {
            System.out.print(mensaje);
            String textoEntrada = explorador.nextLine().trim().replace(',', '.');
            try {
                numero = Double.parseDouble(textoEntrada);
                if (numero >= 0.0) {
                    validez = true;
                } else {
                    System.out.println("[Error] El valor decimal no puede ser negativo. Intente nuevamente.");
                }
            } catch (NumberFormatException excepcion) {
                System.out.println("[Error] Debe ingresar un numero decimal valido. Intente nuevamente.");
            }
        }
        return numero;
    }
}
