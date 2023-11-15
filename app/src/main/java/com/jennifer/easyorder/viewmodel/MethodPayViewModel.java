package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.MethodPay;
import com.jennifer.easyorder.model.Worker;

public class MethodPayViewModel extends ViewModel {

    private MutableLiveData<MethodPay> methodPayMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Worker> workerMutableLiveData = new MutableLiveData<>();


    public void setMethodPayDescription(MethodPay methodPay) {
        methodPayMutableLiveData.setValue(methodPay);
    }

    public LiveData<MethodPay> getMethodPayLiveData() {
        return methodPayMutableLiveData;
    }

    public void setWorker(Worker worker) {
        workerMutableLiveData.setValue(worker);
    }

    public LiveData<Worker> getWorkerLiveData() {
        return workerMutableLiveData;
    }


}
