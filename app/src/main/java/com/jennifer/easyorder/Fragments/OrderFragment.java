package com.jennifer.easyorder.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jennifer.easyorder.Adapter.OrderAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentOrderBinding;
import com.jennifer.easyorder.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {
    private FragmentOrderBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_order);
        GridLayoutManager gridLayout = new GridLayoutManager(view.getContext(), 2);
        binding.rvOrder.setLayoutManager(gridLayout);

        RestaurantInterface orderInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Order>> call = orderInterface.getShowOrder();

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<Order> item = response.body();
                    OrderAdapter rvOrderAdapter = new OrderAdapter(item);
                    recyclerView.setAdapter(rvOrderAdapter);
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
}