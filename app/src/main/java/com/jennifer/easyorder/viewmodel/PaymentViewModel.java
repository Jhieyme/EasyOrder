package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Worker;

import java.util.List;

public class PaymentViewModel extends ViewModel {

    private MutableLiveData<Integer> methodPayMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Worker> workerMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Customer> selectedCustomer = new MutableLiveData<>();

    private MutableLiveData<Order> selectedOrder = new MutableLiveData<>();
    private MutableLiveData<List<DetailOrder>> selectedDetailOrder = new MutableLiveData<>();


    public void setMethodPayDescription(Integer methodPayCod) {
        methodPayMutableLiveData.setValue(methodPayCod);
    }

    public LiveData<Integer> getMethodPayLiveData() {
        return methodPayMutableLiveData;
    }

    public void setWorker(Worker worker) {
        workerMutableLiveData.setValue(worker);
    }

    public LiveData<Worker> getWorkerLiveData() {
        return workerMutableLiveData;
    }


    public void selectedCustomer(Customer customer) {
        selectedCustomer.setValue(customer);
    }

    public LiveData<Customer> getSelectedCustomer() {
        return selectedCustomer;
    }


    public void setSelectOrder(Order order) {
        selectedOrder.setValue(order);
    }

    public LiveData<Order> getSelectedOrder() {
        return selectedOrder;
    }


    public void setSelectedDetailOrder(List<DetailOrder> detailOrders) {
        selectedDetailOrder.setValue(detailOrders);

    }

    public LiveData<List<DetailOrder>> getSelectedDetailOrder() {
        return selectedDetailOrder;
    }


}
