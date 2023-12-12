package com.jennifer.easyorder.data;

import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.CustomerRENIEC;
import com.jennifer.easyorder.model.CustomerWorker;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.MethodPay;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.model.User;
import com.jennifer.easyorder.model.Voucher;
import com.jennifer.easyorder.model.Worker;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("Comanda/{id}")
    Call<Order> updateOrder(@Body Order orderBody, @Path("id") int id);

    @DELETE("Comanda/{id}")
    Call<Order> deleteOrder(@Path("id") int id);


    // ------------------ Detalle Comanda ------------ //
    @GET("DetalleComanda")
    Call<List<DetailOrder>> getDetail();


    @POST("DetalleComanda")
    Call<DetailOrder> addDetailOrder(@Body DetailOrder detailOrder);

    @DELETE("DetalleComanda/EliminarPorComanda/{id}")
    Call<DetailOrder> deleteDetailOrders(@Path("id") int id);


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
    Call<CustomerRENIEC> getCustomer(@Path("dni") String dni);


    @POST("Cliente")
    Call<Customer> addCustomer(@Body Customer customerBody);

    @PUT("Cliente/{id}")
    Call<Customer> updateCustomer(@Path("id") int id, @Body Customer customer);

    @DELETE("Cliente/{id}")
    Call<Customer> deleteCustomer(@Path("id") int id);


    // --------------- Metodo de Pago -------------- //
    @GET("TipoPago")
    Call<List<MethodPay>> getMethodPay();

    // ------------------ Boleta ---------------  //
    @POST("Boleta")
    Call<Voucher> addBoleta(@Body Voucher voucherBody);

    @GET("Boleta/Info-IdBoleta/{idBoleta}")
    Call<CustomerWorker> getByIdBoleta(@Path("idBoleta") int id);
}