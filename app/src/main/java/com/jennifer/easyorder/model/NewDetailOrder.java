package com.jennifer.easyorder.model;

public class NewDetailOrder {

    public int cantidad;
    public double subTotal;
    public double importe;
    public int idComanda;
    public int idProducto;


    public NewDetailOrder(int cantidad, double subTotal, double importe, int idComanda, int idProducto) {
        this.cantidad = cantidad;
        this.subTotal = subTotal;
        this.importe = importe;
        this.idComanda = idComanda;
        this.idProducto = idProducto;
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

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
