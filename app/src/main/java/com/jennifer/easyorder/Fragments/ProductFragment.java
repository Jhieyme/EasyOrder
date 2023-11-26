package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.ProductAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentProductBinding;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.viewmodel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {

    private FragmentProductBinding binding;
    private RecyclerView recyclerView;
    private ProductViewModel productViewModel;
    private List<Product> itemsProduct;
    private ProductAdapter rvProductAdapter;
    private SearchView searchViewProduct;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_product);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        searchViewProduct = view.findViewById(R.id.sv_searchProduct);
        searchViewProduct.clearFocus();

        searchViewProduct.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListProduct(newText);
                return true;
            }
        });

        showProducts();
    }

    private void showProducts() {
        RestaurantInterface productInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Product>> call = productInterface.getShowProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemsProduct = response.body();
                    List<Product> itemsToAdapter = new ArrayList<>();
                    for (Product product : itemsProduct) {
                        if (product.isActivo()) {
                            itemsToAdapter.add(product);
                        }
                    }
                    rvProductAdapter= new ProductAdapter(itemsToAdapter, productViewModel);
                    recyclerView.setAdapter(rvProductAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    private void filterListProduct(String text) {
        List<Product> filterList = new ArrayList<>();
        for (Product item : itemsProduct) {
            String nomProduct = item.getNombre();
            if (nomProduct.toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(getContext(), "Producto no encontrado", Toast.LENGTH_SHORT).show();
        } else {
            rvProductAdapter.setFilterListProduct(filterList);
        }
    }
}

