package com.jennifer.easyorder.model;

public class Voucher {

    public int idBoleta;

    public Customer idClienteNavigation;
    public Worker idWorkerNavigation;
    public Order idComandaNavigation;

    public MethodPay idTipoPagoNavigation;

    public Voucher(int idBoleta, Customer idClienteNavigation, Worker idWorkerNavigation, Order idComandaNavigation, MethodPay idTipoPagoNavigation) {
        this.idBoleta = idBoleta;
        this.idClienteNavigation = idClienteNavigation;
        this.idWorkerNavigation = idWorkerNavigation;
        this.idComandaNavigation = idComandaNavigation;
        this.idTipoPagoNavigation = idTipoPagoNavigation;
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

    public Worker getIdWorkerNavigation() {
        return idWorkerNavigation;
    }

    public void setIdWorkerNavigation(Worker idWorkerNavigation) {
        this.idWorkerNavigation = idWorkerNavigation;
    }

    public Order getIdComandaNavigation() {
        return idComandaNavigation;
    }

    public void setIdComandaNavigation(Order idComandaNavigation) {
        this.idComandaNavigation = idComandaNavigation;
    }

    public MethodPay getIdTipoPagoNavigation() {
        return idTipoPagoNavigation;
    }

    public void setIdTipoPagoNavigation(MethodPay idTipoPagoNavigation) {
        this.idTipoPagoNavigation = idTipoPagoNavigation;
    }
}
