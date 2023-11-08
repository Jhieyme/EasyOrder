package com.jennifer.easyorder.utils;

import com.jennifer.easyorder.MainActivity;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.viewmodel.OrderViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComandaUtils {


    private RestaurantInterface comandaInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
    private OrderViewModel orderViewModel;
    public ComandaUtils(OrderViewModel orderViewModel) {
        this.orderViewModel = orderViewModel;

    }


    public void postComanda(Order comanda) {

        Call<Order> callApi = comandaInterface.addOrder(comanda);
        callApi.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    //Mensaje de exito
                    Order orderResponse = response.body();

                    orderViewModel.setOrderResponseBody(orderResponse);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                try {
                    throw new Throwable(t.getCause());
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public void postDetailOrder(DetailOrder detailOrder) {
        Call<DetailOrder> callApi = comandaInterface.addDetailOrder(detailOrder);

        callApi.enqueue(new Callback<DetailOrder>() {
            @Override
            public void onResponse(Call<DetailOrder> call, Response<DetailOrder> response) {
                if (response.isSuccessful()) {
                    // Mensaje de exito
                    System.out.println("add detail order");
                }
            }

            @Override
            public void onFailure(Call<DetailOrder> call, Throwable t) {
                try {
                    throw new Throwable(t.getCause());
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
