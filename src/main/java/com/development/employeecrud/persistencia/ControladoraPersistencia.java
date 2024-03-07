package com.development.employeecrud.persistencia;

import com.development.employeecrud.logica.Departamento;
import com.development.employeecrud.logica.Empleado;
import com.development.employeecrud.persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladoraPersistencia {

    DepartamentoJpaController departamentoJPA = new DepartamentoJpaController();

    EmpleadoJpaController empleadoJPA = new EmpleadoJpaController();

    public void crearEmpleado(Empleado empleado) {
        empleadoJPA.create(empleado);
    }

    public List<Empleado> mostrarEmpleados() {
        return empleadoJPA.findEmpleadoEntities();
    }

    public void modificarEmpleado(Empleado empleado) {

        try {
            empleadoJPA.edit(empleado);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarEmpleado(Long id) {
        try {
            empleadoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("El empleado que desea eliminar no se encuentra en la BBDD");
        }
    }

    public void borrarEmpleadoLogico(Long id) {
        try {
            Empleado empleado = empleadoJPA.findEmpleado(id);
            if (empleado != null) {
                empleado.setEliminado(true);
                empleadoJPA.edit(empleado);
                System.out.println("Empleado eliminado correctamente.");
            } else {
                System.out.println("El empleado no se encuentra en la BBDD.");
            }
        } catch (Exception ex) {

            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Empleado encontrarEmpleadoPorId(Long idEmpleado) {
        return empleadoJPA.findEmpleado(idEmpleado);
    }

    //Creación de métodos para la relcion de la clase Empleado con Departamento
    //metodo que usuaremos para comprobar/ buscar si el departamento ya existe 
    public Departamento encontrarDepartamentoPorNombre(String nombreDepartamento) {
        //Se busca el departamento en la BBDD, ignorando las mayusculas y minúsculas
        List<Departamento> departamentos = findDepartamentoByNombreIgnoreCase(nombreDepartamento);
        //Verificamos que el departamento exista (que no sea nula ni vacía)
        if (departamentos != null && !departamentos.isEmpty()) {
            // Si se encuentra el departamento, devolvemos el primero de la lista
            System.out.println("Departamento encontrado: " + departamentos.get(0).getNombre());
            return departamentos.get(0);
        } else {

            System.out.println("No se encontró ningún departamento con el nombre: " + nombreDepartamento);

            // Si no se encuentra el departamento, creamos uno nuevo y guardamos en la BBDD
            Departamento nuevoDepartamento = new Departamento();
            nuevoDepartamento.setNombre(nombreDepartamento);
            departamentoJPA.create(nuevoDepartamento);

            System.out.println("Nuevo departamento creado: " + nuevoDepartamento.getNombre());

            return nuevoDepartamento;
        }
    }

    public void crearEmpleadoConDepartamento(Empleado empleado, Departamento departamento) {
        // Verificar si el departamento ya existe en la base de datos
        Departamento departamentoExistente = encontrarDepartamentoPorNombre(departamento.getNombre());

        if (departamentoExistente != null) {
            // si existe se le asigna 
            empleado.setDepartamento(departamentoExistente);
        } else {
            //si no existe creamos un departamento (nombreDept +id) nuevo
            departamentoJPA.create(departamento);
            empleado.setDepartamento(departamento);
        }
        //Creamos el empleado en la BBDD
        crearEmpleado(empleado);
    }

    public List<Departamento> findDepartamentoByNombreIgnoreCase(String nombreDepartamento) {
        List<Departamento> allDepartamentos = departamentoJPA.findDepartamentoEntities();

        List<Departamento> resultDepartamentos = new ArrayList<>();

        for (Departamento dep : allDepartamentos) {
            if (dep.getNombre().equalsIgnoreCase(nombreDepartamento)) {
                resultDepartamentos.add(dep);
            }
        }

        return resultDepartamentos;
    }

    // Métodos para la entidad Departamento, pero que de momento no se les da uso 
    public void crearDepartamento(Departamento departamento) {
        departamentoJPA.create(departamento);
    }

    public List<Departamento> mostrarDepartamentos() {
        return departamentoJPA.findDepartamentoEntities();
    }

    public void modificarDepartamento(Departamento departamento) {
        try {
            departamentoJPA.edit(departamento);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarDepartamento(Long id) {
        try {
            departamentoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            System.out.println("El departamento que intenta eliminar no existe en la BBDD");
        }
    }

    public Departamento encontrarDepartamento(Long id) {
        return departamentoJPA.findDepartamento(id);
    }

}
