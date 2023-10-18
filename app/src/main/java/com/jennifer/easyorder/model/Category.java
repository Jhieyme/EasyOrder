package com.jennifer.easyorder.model;

public class Category {

    private int idCategoria;
    private String descripcion;

    private String urlImagen;

    public Category(int idCategoria, String descripcion, String urlImagen) {
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
