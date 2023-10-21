package com.jennifer.easyorder.data;

import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.model.User;
import com.jennifer.easyorder.model.Worker;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestaurantInterface {

    @GET("Usuario")
    Call<List<User>> getShowUser();

    @POST("Comanda")
    Call<Order> addOrder();

    @GET("Categoria")
    Call<List<Category>> getShowCategory();

    //-------------------------------------------------
    @GET("Producto")
    Call<List<Product>> getShowProduct();

    //-------------------------------------------------
    @GET("Mesa")
    Call<List<Table>> getShowTable();

    //-------------------------------------------------
    @GET("Personal")
    Call<List<Worker>> getShowWorker();

    //-------------------------------------------------
    @GET("Cliente")
    Call<List<Customer>> getShowCustomer();

    //-------------------------------------------------
    @GET("{dni}?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFuZHJlc3BhcmphdEBnbWFpbC5jb20ifQ.A4kk76bROCy44OoQZZqfLo27rG3rqg1HK6GXZUcjvuI")
    Call<Customer> getCustomer(@Path("dni") String dni);


}
