package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Voucher;

public class VoucherViewModel extends ViewModel {


    private MutableLiveData<Voucher> returnedVoucherrBody = new MutableLiveData<>();


    public void setVoucherResponseBody(Voucher voucher) {
        returnedVoucherrBody.setValue(voucher);
    }

    public LiveData<Voucher> getSettedVoucher() {
        return returnedVoucherrBody;
    }
}
