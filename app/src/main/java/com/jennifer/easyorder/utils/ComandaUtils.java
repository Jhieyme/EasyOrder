package com.jennifer.easyorder.utils;

import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.model.DetailOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComandaUtils {


    private RestaurantInterface comandaInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);


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
