package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.CategoryAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentCategoryBinding;
import com.jennifer.easyorder.model.Category;
import com.jennifer.easyorder.viewmodel.CategoryViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    private FragmentCategoryBinding binding;
    private RecyclerView recyclerView;
    private CategoryViewModel categoryViewModel;

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater, container, false);
        categoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        recyclerView = view.findViewById(R.id.rv_category);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        binding.rvCategory.setLayoutManager(layoutManager);

        ListCategories();
    }

    private void ListCategories() {

        RestaurantInterface categoryInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Category>> call = categoryInterface.getShowCategory();

        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> items = response.body();

                    CategoryAdapter rvCategoryAdapter = new CategoryAdapter(items, fragmentManager, categoryViewModel);
                    recyclerView.setAdapter(rvCategoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}