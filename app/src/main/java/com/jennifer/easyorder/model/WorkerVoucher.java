package com.jennifer.easyorder.model;

public class WorkerVoucher {

    private int idPersonal;
    private String nombre;
    private String apellidos;


    public WorkerVoucher() {
    }

    public WorkerVoucher(int idPersonal, String nombre, String apellidos) {
        this.idPersonal = idPersonal;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }


    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
