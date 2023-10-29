package com.jennifer.easyorder.model;

import java.util.Date;

public class Order {

    private int idComanda;
    private String fechaHora;
    private String estado;
    private double total;

    private Table idMesaNavigation;

    public Order(int idComanda, String fechaHora, String estado, double total, Table idMesaNavigation) {
        this.idComanda = idComanda;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.total = total;
        this.idMesaNavigation = idMesaNavigation;
    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Table getIdMesaNavigation() {
        return idMesaNavigation;
    }

    public void setIdMesaNavigation(Table idMesaNavigation) {
        this.idMesaNavigation = idMesaNavigation;
    }
}