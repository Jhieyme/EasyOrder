package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Table;

import java.util.HashSet;

public class TableViewModel extends ViewModel {


    private MutableLiveData<Table> selectedTable = new MutableLiveData<>();

    private MutableLiveData<Table> selectedTableImg = new MutableLiveData<>();
    private MutableLiveData<Integer> selectedTableId = new MutableLiveData<>();


    // Lista de Mesas ocupada o no
    private MutableLiveData<HashSet<Table>> listTableAssigned = new MutableLiveData<>();


    public TableViewModel() {
        listTableAssigned = new MutableLiveData<>();
        listTableAssigned.setValue(new HashSet<>());
    }

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

    public void setSelectedTableId(int idTable) {
        selectedTableId.setValue(idTable);
    }

    public LiveData<Integer> getSelectedTableId() {
        return selectedTableId;
    }

    public void addTableAssigned(Table tableAsssigned) {
        HashSet<Table> tables = new HashSet<>(listTableAssigned.getValue());
        if (tables.contains(tableAsssigned)) {
            System.out.println("aaasda");
        } else {
            tables.add(tableAsssigned);
            listTableAssigned.setValue(tables);
            System.out.println("agregado");
        }

    }

    public void deleteTableAssigned(int idMesa) {
        HashSet<Table> tables = new HashSet<>(listTableAssigned.getValue());
        for (Table table : tables) {
            if (table.getIdMesa() == idMesa) {
                tables.remove(table);
                break;
            }
        }
        listTableAssigned.setValue(tables);
       
    }


    public LiveData<HashSet<Table>> getListTableAssigned() {
        return listTableAssigned;
    }
}
