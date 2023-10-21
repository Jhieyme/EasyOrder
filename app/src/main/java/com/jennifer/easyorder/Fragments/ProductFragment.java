package com.jennifer.easyorder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.ProductAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentProductBinding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment implements ProductAdapter.selectedProducts {

    private FragmentProductBinding binding;
    private RecyclerView recyclerView;

    public interface OnProductSelectedListener {
        void onProductSelected(List<NewProduct> selectedProducts);
    }
    private OnProductSelectedListener callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            callback = (OnProductSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnProductSelectedListener");
        }
    }

    @Override
    public void onClickSelectedProducts(List<NewProduct> listNewProducts) {
        callback.onProductSelected(listNewProducts);
    }

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvProduct.setLayoutManager(layoutManager);

        RestaurantInterface productInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Product>> call = productInterface.getShowProduct();


        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> items = response.body();
                    ProductAdapter adapter = new ProductAdapter(items, ProductFragment.this);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }
}

