package com.jennifer.easyorder.model;

public class CustomerWorker {

    private CustomerVoucher cliente;

    private WorkerVoucher personal;

    public CustomerWorker(CustomerVoucher cliente, WorkerVoucher personal) {
        this.cliente = cliente;
        this.personal = personal;
    }
    
    public CustomerVoucher getCliente() {
        return cliente;
    }

    public void setCliente(CustomerVoucher cliente) {
        this.cliente = cliente;
    }

    public WorkerVoucher getPersonal() {
        return personal;
    }

    public void setPersonal(WorkerVoucher personal) {
        this.personal = personal;
    }
}
