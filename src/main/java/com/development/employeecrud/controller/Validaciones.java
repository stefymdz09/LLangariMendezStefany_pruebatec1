package com.development.employeecrud.controller;

import com.development.employeecrud.persistencia.exceptions.EntradaInvalidaException;
import com.development.employeecrud.persistencia.exceptions.FechaInvalidaException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Validaciones {
    //Campos obligatorios en la solicitud de datos del empleado con excepciones 

    public static String validarEntradaTexto(Scanner scanner, String mensaje) throws EntradaInvalidaException {
        String entrada = null;

        do {
            System.out.print(mensaje);
            entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Error: Este campo es obligatorio. Por favor, ingrese un nuevo valor/dato ");
            }
        } while (entrada == null || entrada.isEmpty());

        return entrada;
    }

    public static Date validarEntradaFecha(Scanner scanner, String mensaje) throws FechaInvalidaException {
        //formato de la fecha
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio = null;

        do {
            scanner.nextLine();
            System.out.print(mensaje);
            String fechaInicioStr = scanner.nextLine().trim();

            if (fechaInicioStr.isEmpty()) {

                return null;
            }

            try {
                fechaInicio = sdf.parse(fechaInicioStr);

                if (!fechaInicioStr.equals(sdf.format(fechaInicio))) {
                    throw new ParseException("Fecha con formato incorrecto", 0);
                }

                // comprobar que la fecha ingresesada no sea mayor a la fecha actual 
                Date fechaActual = new Date();
                if (fechaInicio.after(fechaActual)) {
                    System.out.println("Error: La fecha no puede ser mayor que la fecha actual.");
                    //restablecer la fecha a null para seguir solicitandola 
                    fechaInicio = null;
                }

            } catch (ParseException e) {
                System.out.println("Formato de fecha inválido. Ingrese una fecha válida (yyyy-MM-dd).");
            }

        } while (fechaInicio == null);

        return fechaInicio;
    }

    public static double validarEntradaSalario(Scanner scanner, String mensaje) {
        double salario = -1;

        while (salario < 0) {
            System.out.print(mensaje);

            try {
                salario = scanner.nextDouble();

                if (salario < 0) {
                    System.out.println("\nEl salario debe ser un número mayor o igual a cero. Por favor, ingrese un nuevo salario");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nIngrese un valor numérico válido. Por favor, intentelo de nuevo");

                scanner.nextLine();
            }
        }

        return salario;
    }

    public static boolean validarFormatoEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            //System.out.println("Formato de email válido.");
            return true;
        } else {

            return false;
        }
    }
    //metodo de validacion de entrada al crear email

    public static String validarEntradaEmail(Scanner scanner, String mensaje) throws EntradaInvalidaException {
        while (true) {
            String email = validarEntradaTexto(scanner, mensaje);

            if (validarFormatoEmail(email)) {
                return email;
            } else {
                System.out.println("Formato de email no válido. Debe tener al menos un '@' y un '.'");
            }
        }
    }
}
