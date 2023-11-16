package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.OrderAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentOrderBinding;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;

    private PaymentViewModel paymentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        paymentViewModel = new ViewModelProvider(requireActivity()).get(PaymentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_order);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        binding.rvOrder.setLayoutManager(layoutManager);


        RestaurantInterface orderInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Order>> call = orderInterface.getShowOrder();
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Order> itemsOrders = response.body(); // Lista de comandas

                    Call<List<DetailOrder>> callDetail = orderInterface.getDetail();
                    callDetail.enqueue(new Callback<List<DetailOrder>>() {
                        @Override
                        public void onResponse(Call<List<DetailOrder>> call, Response<List<DetailOrder>> response) {
                            List<DetailOrder> itemsDetailsOrders = response.body(); // Lista de detalle comanda
                            List<Order> ordersFiltered = new ArrayList<>();
                            for (Order order : itemsOrders) {
                                if (order.getEstado().equals("En Proceso")) {
                                    ordersFiltered.add(order);
                                }
                            }
                            orderAdapter = new OrderAdapter(ordersFiltered, itemsDetailsOrders, OrderFragment.this, paymentViewModel);
                            recyclerView.setNestedScrollingEnabled(false);
                            recyclerView.setAdapter(orderAdapter);
                        }

                        @Override
                        public void onFailure(Call<List<DetailOrder>> call, Throwable t) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    // Método para mostrar mensaje de exito
    public void showNotify() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_message, null);
        final PopupWindow popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setAnimationStyle(R.style.PopupAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popup.showAtLocation(layout, Gravity.LEFT | Gravity.TOP, 0, 330);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popup.dismiss();
                    }
                }, 2000);
            }
        }, 100);
        TextView message = layout.findViewById(R.id.txt_message);
        message.setText("Seleccionaste el metodo de pago");
    }
}