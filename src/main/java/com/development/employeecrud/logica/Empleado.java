package com.development.employeecrud.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Empleado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String cargo;
    private double salario;
    private Date fechaInicio;
    private String email;
    private boolean eliminado;

    @ManyToOne
    private Departamento departamento;

    public Empleado() {
    }

    public Empleado(String nombre, String apellido, String cargo, double salario, Date fechaInicio, String email) {

        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.email = email;
    }

    public Empleado(String nombre, String apellido, String cargo, double salario, Date fechaInicio, String email, Departamento departamento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.email = email;
        this.departamento = departamento;
    }

    public Empleado(Long id, String nombre, String apellido, String cargo, double salario, Date fechaInicio, String email, boolean eliminado, Departamento departamento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cargo = cargo;
        this.salario = salario;
        this.fechaInicio = fechaInicio;
        this.email = email;
        this.eliminado = eliminado;
        this.departamento = departamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Nombre: %s, Apellido: %s, Cargo: %s, Salario: %.2f, Fecha Inicio: %s",
                id, nombre, apellido, cargo, salario, fechaInicio);
    }

}
