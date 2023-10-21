package com.jennifer.easyorder.model;

public class Worker {
    private int idPersonal;
    private String nombres;
    private String apellidos;
    private Gender idGeneroNavigation;

    public Worker(int idPersonal, String nombres, String apellidos, Gender idGeneroNavigation) {
        this.idPersonal = idPersonal;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.idGeneroNavigation = idGeneroNavigation;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
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

    public Gender getIdGeneroNavigation() {
        return idGeneroNavigation;
    }

    public void setIdGeneroNavigation(Gender idGeneroNavigation) {
        this.idGeneroNavigation = idGeneroNavigation;
    }
}
