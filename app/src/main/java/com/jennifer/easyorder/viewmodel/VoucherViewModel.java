package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Customer;

public class VoucherViewModel extends ViewModel {

    private final MutableLiveData<Customer> selectedCustomer = new MutableLiveData<>();

    public void selectedCustomer(Customer customer) {
        selectedCustomer.setValue(customer);
    }

    public LiveData<Customer> getSelectedCustomer() {
        return selectedCustomer;
    }

}