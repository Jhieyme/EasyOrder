package com.jennifer.easyorder.model;

public class Role {

    private int idRol;
    private String descripcion;
    private boolean activo;

    public Role(int idRol, String descripcion, boolean activo) {
        this.idRol = idRol;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
