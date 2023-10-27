package com.jennifer.easyorder.model;

public class Voucher {

    public int idBoleta;

    public Customer idClienteNavigation;

    public Voucher(int idBoleta, Customer idClienteNavigation) {
        this.idBoleta = idBoleta;
        this.idClienteNavigation = idClienteNavigation;
    }

    public int getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(int idBoleta) {
        this.idBoleta = idBoleta;
    }

    public Customer getIdClienteNavigation() {
        return idClienteNavigation;
    }

    public void setIdClienteNavigation(Customer idClienteNavigation) {
        this.idClienteNavigation = idClienteNavigation;
    }
}
