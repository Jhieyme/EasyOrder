package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Table;

public class TableViewModel extends ViewModel {


    private MutableLiveData<Table> selectedTable = new MutableLiveData<>();

    private MutableLiveData<Table> selectedTableImg = new MutableLiveData<>();


    public void setSelectedTable(Table table) {
        selectedTable.setValue(table);
    }

    public LiveData<Table> getSelectedTable() {
        return selectedTable;
    }


    public void setSelectedTableImg(Table table) {
        selectedTableImg.setValue(table);
    }

    public LiveData<Table> getSelectedTableImg() {
        return selectedTableImg;
    }
}
