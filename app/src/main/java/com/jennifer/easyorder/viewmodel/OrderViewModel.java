package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Order;

public class OrderViewModel extends ViewModel {

    private MutableLiveData<Order> returnedOrderBody = new MutableLiveData<>();


    public void setOrderResponseBody(Order order) {
        returnedOrderBody.setValue(order);
    }

    public LiveData<Order> getSettedOrder() {
        return returnedOrderBody;
    }
}
