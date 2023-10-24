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
import com.jennifer.easyorder.Adapter.WorkerAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentCustomerBinding;
import com.jennifer.easyorder.databinding.FragmentWorkerBinding;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.Worker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerFragment extends Fragment {

    private FragmentWorkerBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_worker);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvWorker.setLayoutManager(layoutManager);

        RestaurantInterface workerInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Worker>> call = workerInterface.getShowWorker();

        call.enqueue(new Callback<List<Worker>>() {

            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                List<Worker> items = response.body();
                WorkerAdapter rvWorkerAdapter = new WorkerAdapter(items);
                recyclerView.setAdapter(rvWorkerAdapter);
            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
            }
        });
    }

}