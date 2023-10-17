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

import com.jennifer.easyorder.Adapter.ProductAdapter;
import com.jennifer.easyorder.Adapter.TableAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentProductBinding;
import com.jennifer.easyorder.databinding.FragmentTablesBinding;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.model.Table;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablesFragment extends Fragment {

    private FragmentTablesBinding binding;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTablesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_table);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        binding.rvTable.setLayoutManager(layoutManager);

        RestaurantInterface tableInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Table>> call = tableInterface.getShowTable();

        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                List<Table> items = response.body();
                TableAdapter rvTableAdapter = new TableAdapter(items);
                recyclerView.setAdapter(rvTableAdapter);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
            }
        });
    }
}