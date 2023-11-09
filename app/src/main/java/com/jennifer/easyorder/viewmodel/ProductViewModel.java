package com.jennifer.easyorder.viewmodel;

import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.NewProduct;

import java.util.HashSet;

public class ProductViewModel extends ViewModel {


    private MutableLiveData<HashSet<NewProduct>> selectedListNewProduct = new MutableLiveData<>();

    public ProductViewModel() {
        selectedListNewProduct = new MutableLiveData<>();
        selectedListNewProduct.setValue(new HashSet<>());
    }


    public void addProduct(NewProduct newProduct, View view) {
        HashSet<NewProduct> products = new HashSet<>(selectedListNewProduct.getValue());
        if (products.contains(newProduct)) {
            Toast.makeText(view.getContext(), "¡Este producto ya ha sido seleccionado!", Toast.LENGTH_SHORT).show();
        } else {
            products.add(newProduct);
            selectedListNewProduct.setValue(products);
            Toast.makeText(view.getContext(), "¡Seleccionaste un platillo!", Toast.LENGTH_SHORT).show();
        }


    }

    public void deleteProduct(NewProduct newProduct, View view) {
        HashSet<NewProduct> products = new HashSet<>(selectedListNewProduct.getValue());
        products.remove(newProduct);
        selectedListNewProduct.setValue(products);
        Toast.makeText(view.getContext(), "¡Eliminaste un platillo!", Toast.LENGTH_SHORT).show();

    }

    public void selectedListNewProduct(HashSet<NewProduct> listNewProduct) {
        selectedListNewProduct.setValue(listNewProduct);
    }

    public LiveData<HashSet<NewProduct>> getSelectedListNewProduct() {
        return selectedListNewProduct;
    }
}
