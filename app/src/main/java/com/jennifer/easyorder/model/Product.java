package com.jennifer.easyorder.model;

public class Product {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private String urlImagen;

    private boolean activo;

    private Category idCategoriaNavigation;

    public Product(int idProducto, String nombre, String descripcion, double precio, String urlImagen, boolean activo, Category idCategoriaNavigation) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImagen = urlImagen;
        this.activo = activo;
        this.idCategoriaNavigation = idCategoriaNavigation;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Category getIdCategoriaNavigation() {
        return idCategoriaNavigation;
    }

    public void setIdCategoriaNavigation(Category idCategoriaNavigation) {
        this.idCategoriaNavigation = idCategoriaNavigation;
    }
}
