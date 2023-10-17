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
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentCategoryBinding;
import com.jennifer.easyorder.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_category);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        binding.rvCategory.setLayoutManager(layoutManager);

        RestaurantInterface categoryInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Category>> call = categoryInterface.getShowCategory();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> items = response.body();
                CategoryAdapter rvCategoryAdapter = new CategoryAdapter(items);
                recyclerView.setAdapter(rvCategoryAdapter);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
            }
        });
    }

}