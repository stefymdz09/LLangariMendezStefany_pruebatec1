package com.development.employeecrud.igu;

import com.development.employeecrud.controller.EmpleadoDAO;

import com.development.employeecrud.persistencia.ControladoraPersistencia;
import com.development.employeecrud.persistencia.exceptions.EntradaInvalidaException;
import com.development.employeecrud.persistencia.exceptions.FechaInvalidaException;
import java.util.InputMismatchException;

import java.util.Scanner;

public class GestionEmpleados {

    public static void main(String[] args) throws EntradaInvalidaException, FechaInvalidaException {
        Scanner scanner = new Scanner(System.in);
        ControladoraPersistencia controladoraPersistencia = new ControladoraPersistencia();

        int opcion = 0;

        do {
            try {
                mostrarMenu();
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        EmpleadoDAO.crearEmpleado(scanner, controladoraPersistencia);
                        break;
                    case 2:
                        EmpleadoDAO.mostrarEmpleados(controladoraPersistencia);
                        break;
                    case 3:
                        EmpleadoDAO.modificarEmpleado(scanner, controladoraPersistencia);
                        break;
                    case 4:
                        EmpleadoDAO.borrarEmpleado(scanner, controladoraPersistencia);
                        break;
                    case 5:
                        EmpleadoDAO.buscarEmpleadoPorCargo(scanner, controladoraPersistencia);

                        break;

                    case 0:
                        System.out.println("Saliendo del programa. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor, ingrese un número del menú.");
                }
            } catch (EntradaInvalidaException | FechaInvalidaException e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un valor válido.");
                scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
                scanner.nextLine(); 
            }
        } while (opcion != 0);

    }

    private static void mostrarMenu() {
        System.out.println("===================================");
        System.out.println("|        Menú CRUD Empleados      |");
        System.out.println("===================================");
        System.out.println("|  Opción  |       Descripción     |");
        System.out.println("|---------------------------------|");
        System.out.println("|     1    |   Crear Empleado      |");
        System.out.println("|     2    |   Listar Empleados    |");
        System.out.println("|     3    |   Modificar Empleado  |");
        System.out.println("|     4    |   Eliminar Empleado   |");
        System.out.println("|     5    |   Buscar por Cargo    |");
        System.out.println("|     0    |   Salir               |");
        System.out.println("===================================");
    }

}
