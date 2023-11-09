package com.jennifer.easyorder.model;

public class DetailOrder {

    private int idDetalleComanda;
    private int cantidad;
    private double subTotal;
    private double importe;
    private Order idComandaNavigation;
    private Product idProductoNavigation;

//    public DetailOrder(int idDetalleComanda, int cantidad, double subTotal, double importe, Order idComandaNavigation, Product idProductoNavigation) {
//        this.idDetalleComanda = idDetalleComanda;
//        this.cantidad = cantidad;
//        this.subTotal = subTotal;
//        this.importe = importe;
//        this.idComandaNavigation = idComandaNavigation;
//        this.idProductoNavigation = idProductoNavigation;
//    }

    public int getIdDetalleComanda() {
        return idDetalleComanda;
    }

    public void setIdDetalleComanda(int idDetalleComanda) {
        this.idDetalleComanda = idDetalleComanda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public Order getIdComandaNavigation() {
        return idComandaNavigation;
    }

    public void setIdComandaNavigation(Order idComandaNavigation) {
        this.idComandaNavigation = idComandaNavigation;
    }

    public Product getIdProductoNavigation() {
        return idProductoNavigation;
    }

    public void setIdProductoNavigation(Product idProductoNavigation) {
        this.idProductoNavigation = idProductoNavigation;
    }
}
