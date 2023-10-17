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

import com.jennifer.easyorder.Adapter.CategoryAdapter;
import com.jennifer.easyorder.Adapter.ProductAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentCategoryBinding;
import com.jennifer.easyorder.databinding.FragmentProductBinding;
import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_product);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        binding.rvProduct.setLayoutManager(layoutManager);

        RestaurantInterface productInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Product>> call = productInterface.getShowProduct();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> items = response.body();
                ProductAdapter rvProductAdapter = new ProductAdapter(items);
                recyclerView.setAdapter(rvProductAdapter);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

}