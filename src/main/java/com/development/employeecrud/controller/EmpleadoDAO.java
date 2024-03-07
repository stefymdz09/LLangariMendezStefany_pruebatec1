package com.development.employeecrud.controller;

import com.development.employeecrud.logica.Controladora;
import com.development.employeecrud.logica.Departamento;
import com.development.employeecrud.logica.Empleado;
import com.development.employeecrud.persistencia.ControladoraPersistencia;
import com.development.employeecrud.persistencia.exceptions.EntradaInvalidaException;
import com.development.employeecrud.persistencia.exceptions.FechaInvalidaException;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Scanner;

public class EmpleadoDAO {

    private static final Controladora controladoraPersistencia = new Controladora();

    public static void crearEmpleado(Scanner scanner, ControladoraPersistencia controladoraPersistencia) throws EntradaInvalidaException, FechaInvalidaException {

        Empleado nuevoEmpleado = new Empleado();
        System.out.println("|--------------------------------------------|");
        System.out.println("|      1  -   Crear Empleado                 |");
        System.out.println("|--------------------------------------------|");
        scanner.nextLine();
        nuevoEmpleado.setNombre(Validaciones.validarEntradaTexto(scanner, "Ingrese nombre del empleado: "));
        nuevoEmpleado.setApellido(Validaciones.validarEntradaTexto(scanner, "Ingrese apellido del empleado: "));
        nuevoEmpleado.setCargo(Validaciones.validarEntradaTexto(scanner, "Ingrese cargo del empleado: "));
        nuevoEmpleado.setSalario(Validaciones.validarEntradaSalario(scanner, "Ingrese salario del empleado: "));
        nuevoEmpleado.setFechaInicio(Validaciones.validarEntradaFecha(scanner, "Ingrese fecha de inicio del empleado (yyyy-MM-dd): "));
        nuevoEmpleado.setEmail(Validaciones.validarEntradaEmail(scanner, "Ingrese email del empleado: "));

        // Creamos el objeto Departamento con el nombre proporcionado por el usuario
        Departamento nuevoDepartamento = new Departamento();
        nuevoDepartamento.setNombre(Validaciones.validarEntradaTexto(scanner, "Ingrese nombre del departamento al que pertenece el empleado"));

        // Creamos el nuevo empleado con el departamento
        controladoraPersistencia.crearEmpleadoConDepartamento(nuevoEmpleado, nuevoDepartamento);

        System.out.println("Empleado creado correctamente !");

    }

//--------------------MODIFICAR DATOS DEL EMPLEADO-----------------------------------------------------------
    public static void modificarEmpleado(Scanner scanner, ControladoraPersistencia controladoraPersistencia) throws FechaInvalidaException, EntradaInvalidaException {
        System.out.println("|--------------------------------------------|");
        System.out.println("|      3  -   Modificar Empleado                 |");
        System.out.println("|--------------------------------------------|");

        System.out.print("Ingrese ID del empleado a modificar: ");
        Long idEmpleado = scanner.nextLong();
        scanner.nextLine();

        // Verificamos si el empleado existe
        Empleado empleadoAModificar = controladoraPersistencia.encontrarEmpleadoPorId(idEmpleado);
        //Verificar si existe y si esta en 1 (eliminado) en la BBDD

        if (empleadoAModificar == null || empleadoAModificar.isEliminado()) {
            System.out.println("No se encontró empleado con ese ID / o ese ID esta desactivado(eliminado)");
            return;
        }

        System.out.println("|--------------------------------------------|");
        System.out.println("|     Información actual del empleado:             |");
        System.out.println("|--------------------------------------------|");

        mostrarInformacionEmpleado(empleadoAModificar);

        System.out.print("Ingrese nuevo nombre del empleado (deje en blanco para no modificar): ");
        String nuevoNombre = scanner.nextLine().trim();
        if (!nuevoNombre.isEmpty()) {
            empleadoAModificar.setNombre(nuevoNombre);
        }

        System.out.print("Ingrese nuevo apellido del empleado (deje en blanco para no modificar): ");
        String nuevoApellido = scanner.nextLine().trim();
        if (!nuevoApellido.isEmpty()) {
            empleadoAModificar.setApellido(nuevoApellido);
        }

        System.out.print("Ingrese nuevo cargo del empleado (deje en blanco para no modificar): ");
        String nuevoCargo = scanner.nextLine().trim();
        //scanner.nextLine(); 
        if (!nuevoCargo.isEmpty()) {
            empleadoAModificar.setCargo(nuevoCargo);
        }
        double nuevoSalario = Validaciones.validarEntradaSalario(scanner, "Ingrese nuevo salario del empleado (deje 0 para no modificar): ");
        scanner.nextLine();
        if (nuevoSalario != 0) {
            empleadoAModificar.setSalario(nuevoSalario);
        }

        Date nuevaFechaInicio = Validaciones.validarEntradaFecha(scanner, "Ingrese nueva fecha de inicio del empleado (yyyy-MM-dd, deje en blanco para no modificar): ");

        if (nuevaFechaInicio != null) {
            empleadoAModificar.setFechaInicio(nuevaFechaInicio);
        } else {
            System.out.println("No se modifico la fecha.");
        }

        String nuevoEmail = Validaciones.validarEntradaEmail(scanner, "Ingrese nuevo email del empleado");

        if (Validaciones.validarFormatoEmail(nuevoEmail)) {
            empleadoAModificar.setEmail(nuevoEmail);
        } else {
            //Solicitar email hasta que lo ingrese correctamente
            do {
                System.out.print("Email no válido. Debe contener un @ o . ");
                nuevoEmail = scanner.nextLine().trim();
            } while (!Validaciones.validarFormatoEmail(nuevoEmail));

        }
        empleadoAModificar.setEmail(nuevoEmail);

        System.out.print("Ingrese nuevo nombre del departamento (deje en blanco para no modificar): ");
        String nuevoNombreDepartamento = scanner.nextLine().trim();

        // Verificamos si el departamento ya existe en la base de datos, lo buscamos
        Departamento departamentoExistente = controladoraPersistencia.encontrarDepartamentoPorNombre(nuevoNombreDepartamento);

        //Verificamos la existencia del departamento escrito por el usuario, si existe se modifica
        if (departamentoExistente != null) {
            // el departamento existente se lo asignamos a empleado
            empleadoAModificar.setDepartamento(departamentoExistente);
        } else {
            // Creamos un nuevo departamento (ID) si no existe
            Departamento nuevoDepartamento = new Departamento();
            nuevoDepartamento.setNombre(nuevoNombreDepartamento);

            empleadoAModificar.setDepartamento(nuevoDepartamento);
        }

        controladoraPersistencia.modificarEmpleado(empleadoAModificar);

        System.out.println("Empleado modificado correctamente!.");

    }

    //------------MOSTRAR LA LISTA DE EMPLEADOS- Y FILTRADO POR CARGO-------------------------
    public static void mostrarEmpleados(ControladoraPersistencia controladoraPersistencia) {
        List<Empleado> empleados = controladoraPersistencia.mostrarEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            System.out.println("====================================");
            System.out.println("          Lista de Empleado      ");
            System.out.println("====================================");

            System.out.printf("| %-6s | %-15s | %-15s | %-15s | %-12s | %-35s | %-25s | %-7s | %-20s |\n",
                    "ID", "Nombre", "Apellido", "Cargo", "Salario", "Fecha Inicio", "Email", "ID Dept.", "Nombre Dept.");

            mostrarSeparador();

            for (Empleado empleado : empleados) {
                long departamentoId;
                String departamentoNombre;

                if (empleado.getDepartamento() != null) {
                    departamentoId = empleado.getDepartamento().getId();
                    departamentoNombre = empleado.getDepartamento().getNombre();
                } else {
                    departamentoId = 0;
                    departamentoNombre = "Sin asignar";
                }

                System.out.printf("| %-6d | %-15s | %-15s | %-15s | %-12.2f | %-35s | %-25s | %-7d | %-20s |\n",
                        empleado.getId(), empleado.getNombre(), empleado.getApellido(),
                        empleado.getCargo(), empleado.getSalario(), empleado.getFechaInicio(),
                        empleado.getEmail(),
                        departamentoId,
                        departamentoNombre);
            }

            mostrarSeparador();
        }

    }

    public static void buscarEmpleadoPorCargo(Scanner scanner, ControladoraPersistencia controladoraPersistencia) {
        System.out.println("|-----------------------------------------------------|");
        System.out.println("|      5  -   Filtrar busqueda por cargo del empleado   |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println("Ingrese el cargo a buscar:");
        String cargoBuscado = scanner.next();

        List<Empleado> listaEmpleados = controladoraPersistencia.mostrarEmpleados();
        List<Empleado> empleadosEncontrados = new ArrayList<>();

        for (Empleado empleado : listaEmpleados) {
            if (empleado.getCargo().equalsIgnoreCase(cargoBuscado)) {
                empleadosEncontrados.add(empleado);
            }
        }

        if (empleadosEncontrados.isEmpty()) {
            System.out.println("No se encontraron empleados con el cargo: " + cargoBuscado);
        } else {
            System.out.println("Empleados encontrados:");
            for (Empleado empleado : empleadosEncontrados) {
                System.out.println(empleado);

            }
        }
    }

    private static void mostrarSeparador() {
        System.out.println("|--------------------------------------"
                + "---------------------------------------"
                + "---------------------------------------------------------"
                + "--------------------------------------------------------|");
    }

    public static void borrarEmpleado(Scanner scanner, ControladoraPersistencia controladoraPersistencia) {
        System.out.println("|--------------------------------------------|");
        System.out.println("|      4  -   Elimianr Empleado                 |");
        System.out.println("|--------------------------------------------|");

        System.out.print("Ingrese el ID del empleado que desea borrar: ");
        Long idEmpleado = scanner.nextLong();

        // Verificar si el empleado existe antes de intentar borrarlo
        Empleado empleadoExistente = controladoraPersistencia.encontrarEmpleadoPorId(idEmpleado);
        //como es un borrado logico verificaremos que el boolean sea false(0), significa que sigue en la BBDD  
        if (empleadoExistente != null && !empleadoExistente.isEliminado()) {

            System.out.println("Información del Empleado a eliminar:");
            mostrarInformacionEmpleado(empleadoExistente);

            System.out.print("¿Estas seguro de que quieres eliminar este empleado? (S/N): ");
            String confirmacion = scanner.next();

            if (confirmacion.equalsIgnoreCase("S")) {

                controladoraPersistencia.borrarEmpleadoLogico(idEmpleado);
                System.out.println("El empleado se ha eliminado correctamente !");
            } else {
                System.out.println("Se cancela la operación de eliminar empleado");
            }
        } else {
            System.out.println("No se encontró ningún empleado con ese ID");
        }
    }

    private static void mostrarInformacionEmpleado(Empleado empleado) {

        System.out.printf("  Nombre: %s\n", empleado.getNombre());
        System.out.printf("  Apellido: %s\n", empleado.getApellido());
        System.out.printf("  Cargo: %s\n", empleado.getCargo());
        System.out.printf("  Salario: %.2f\n", empleado.getSalario());
        System.out.printf("  Fecha de Inicio: %s\n", empleado.getFechaInicio());
        System.out.printf("  Email: %s\n", empleado.getEmail());

        if (empleado.getDepartamento() != null) {
            System.out.printf("  ID Departamento: %d\n", empleado.getDepartamento().getId());
            System.out.printf("  Nombre Departamento: %s\n", empleado.getDepartamento().getNombre());
        } else {
            System.out.println("  Sin  departamento asignado");
        }

        System.out.println("|--------------------------------------------|");

    }
}
