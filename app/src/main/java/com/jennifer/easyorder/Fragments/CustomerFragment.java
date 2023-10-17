package com.jennifer.easyorder.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jennifer.easyorder.Adapter.CustomerAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentCustomerBinding;
import com.jennifer.easyorder.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerFragment extends Fragment {

    private FragmentCustomerBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_customer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvCustomer.setLayoutManager(layoutManager);

        RestaurantInterface customerInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Customer>> call = customerInterface.getShowCustomer();

        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> items = response.body();
                CustomerAdapter rvCustomerAdapter = new CustomerAdapter(items);
                recyclerView.setAdapter(rvCustomerAdapter);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }
}