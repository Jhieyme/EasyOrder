package com.jennifer.easyorder.data;

import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.NewCustomer;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.model.User;
import com.jennifer.easyorder.model.Voucher;
import com.jennifer.easyorder.model.Worker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestaurantInterface {


    // ------------------ Usuarios ------------------ //
    @GET("Usuario")
    Call<List<User>> getShowUser();

    // ------------------ Categorias ------------------ //
    @GET("Categoria")
    Call<List<Category>> getShowCategory();


    // ------------------ Productos ------------------ //
    @GET("Producto")
    Call<List<Product>> getShowProduct();

    // ------------------ Comandas ------------------ //
    @GET("Comanda")
    Call<List<Order>> getShowOrder();

    @POST("Comanda")
    Call<Order> addOrder(@Body Order orderBody);


    // ------------------ Mesas ------------------ //
    @GET("Mesa")
    Call<List<Table>> getShowTable();

    // ------------------ Personal --------------- //
    @GET("Personal")
    Call<List<Worker>> getShowWorker();

    // ------------------ Cliente ---------------  //
    @GET("Cliente")
    Call<List<Customer>> getShowCustomer();

    @GET("{dni}?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImFuZHJlc3BhcmphdEBnbWFpbC5jb20ifQ.A4kk76bROCy44OoQZZqfLo27rG3rqg1HK6GXZUcjvuI")
    Call<NewCustomer> getCustomer(@Path("dni") String dni);

    @POST("Cliente")
    Call<Customer> addCustomer(@Body Customer customerBody);


    // ------------------ Boleta ---------------  //
    @GET("Boleta/{id}")
    Call<Voucher> getVoucher(@Path("id") int id);
}