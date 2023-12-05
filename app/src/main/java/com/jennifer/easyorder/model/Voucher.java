package com.jennifer.easyorder.model;

public class Voucher {

    public int idBoleta;
    public int idCliente;
    public int idComanda;
    public int idPersonal;
    public String fechaHora;
    public int idTipoPago;


    public Customer idClienteNavigation;
    public Worker idWorkerNavigation;
    public Order idComandaNavigation;

    public MethodPay idTipoPagoNavigation;


    public Voucher(int idCliente, int idComanda, int idPersonal, int idTipoPago, String fechaHora) {

        this.idCliente = idCliente;
        this.idComanda = idComanda;
        this.idPersonal = idPersonal;
        this.idTipoPago = idTipoPago;
        this.fechaHora = fechaHora;
    }



    public int getIdBoleta() {
        return idBoleta;
    }

    public void setIdBoleta(int idBoleta) {
        this.idBoleta = idBoleta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(int idTipoPago) {
        this.idTipoPago = idTipoPago;
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
