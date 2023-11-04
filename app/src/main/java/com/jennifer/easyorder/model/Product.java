package com.jennifer.easyorder.model;

public class Product {

    private int idProducto;
    private String nombre;
    private String descripcion;
    private double precio;
    private String urlImagen;

    private Category idCategoriaNavigation;


    private boolean activo;

    public Product(int idProducto, String nombre, String descripcion, double precio, String urlImagen, Category idCategoriaNavigation, boolean activo) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.urlImagen = urlImagen;
        this.idCategoriaNavigation = idCategoriaNavigation;
        this.activo = activo;
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

    public Category getIdCategoriaNavigation() {
        return idCategoriaNavigation;
    }

    public void setIdCategoriaNavigation(Category idCategoriaNavigation) {
        this.idCategoriaNavigation = idCategoriaNavigation;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
