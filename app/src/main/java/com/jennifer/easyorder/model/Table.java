package com.jennifer.easyorder.model;

public class Table {

    private int idMesa;
    private int nroMesa;


    public Table(int idMesa, int nroMesa) {
        this.idMesa = idMesa;
        this.nroMesa = nroMesa;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNroMesa() {
        return nroMesa;
    }

    public void setNroMesa(int nroMesa) {
        this.nroMesa = nroMesa;
    }

}
