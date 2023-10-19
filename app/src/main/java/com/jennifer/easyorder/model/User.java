package com.jennifer.easyorder.model;

public class User {

    private int idUsuario;
    private String nombre;
    private String contra;
    private Role idRolNavigation;

    public User(int idUsuario, String nombre, String contra, Role idRolNavigation) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.contra = contra;
        this.idRolNavigation = idRolNavigation;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public Role getIdRolNavigation() {
        return idRolNavigation;
    }

    public void setIdRolNavigation(Role idRolNavigation) {
        this.idRolNavigation = idRolNavigation;
    }
}