package com.jennifer.easyorder.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jennifer.easyorder.model.Category;

public class CategoryViewModel extends ViewModel {

    private MutableLiveData<Category> categoryObject = new MutableLiveData<>();
    public void setCategoryObject(Category category) {
        categoryObject.setValue(category);
    }
    public LiveData<Category> getCategoryObject() {
        return categoryObject;
    }

}
