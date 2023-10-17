package com.jennifer.easyorder.data;

import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.model.Worker;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestaurantInterface {

    @GET("Categoria")
    Call<List<Category>> getShowCategory();

    //-------------------------------------------------
    @GET("Producto")
    Call<List<Product>> getShowProduct();

    //-------------------------------------------------
    @GET("Mesa")
    Call<List<Table>> getShowTable();

    //-------------------------------------------------
    @GET("Cliente")
    Call<List<Customer>> getShowCustomer();

    //-------------------------------------------------
    @GET("Personal")
    Call<List<Worker>> getShowWorker();

}
