
package com.development.employeecrud.logica;

import com.development.employeecrud.persistencia.ControladoraPersistencia;
import java.util.List;

public class Controladora {

    ControladoraPersistencia controlPersistencia = new ControladoraPersistencia();

    public void crearEmpleado(Empleado empleado) {
        controlPersistencia.crearEmpleado(empleado);
    }

    public void borrarEmpleado(Long id) {
        controlPersistencia.borrarEmpleado(id);
    }

    public List<Empleado> mostrarEmpleados() {
        return controlPersistencia.mostrarEmpleados();
    }

    public void modificarEmpleado(Empleado empleado) {
        controlPersistencia.modificarEmpleado(empleado);
    }

    public void borrarEmpleadoLogico(Long id) {
        controlPersistencia.borrarEmpleadoLogico(id);
    }

    public Empleado encontrarEmpleadoPorId(Long idEmpleado) {
        return controlPersistencia.encontrarEmpleadoPorId(idEmpleado);
    }

    public Departamento encontrarDepartamentoPorNombre(String nombreDepartamento) {
        return controlPersistencia.encontrarDepartamentoPorNombre(nombreDepartamento);
    }

    public void crearEmpleadoConDepartamento(Empleado empleado, Departamento departamento) {
        controlPersistencia.crearEmpleadoConDepartamento(empleado, departamento);
    }

    public List<Departamento> findDepartamentoByNombreIgnoreCase(String nombreDepartamento) {
        return controlPersistencia.findDepartamentoByNombreIgnoreCase(nombreDepartamento);
    }

    public void crearDepartamento(Departamento departamento) {
        controlPersistencia.crearDepartamento(departamento);
    }

    public List<Departamento> mostrarDepartamentos() {
        return controlPersistencia.mostrarDepartamentos();
    }

    public void modificarDepartamento(Departamento departamento) {
        controlPersistencia.modificarDepartamento(departamento);
    }

    public void borrarDepartamento(Long id) {
        controlPersistencia.borrarDepartamento(id);
    }

    public Departamento encontrarDepartamento(Long id) {
        return controlPersistencia.encontrarDepartamento(id);
    }
}
