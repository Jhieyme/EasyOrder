package com.jennifer.easyorder.model;

public class Customer {

    private String dni;
    private String nombres;
    private String apellidos;
    private boolean activo;

    public Customer(String dni, String nombres, String apellidos, boolean activo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.activo = activo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
