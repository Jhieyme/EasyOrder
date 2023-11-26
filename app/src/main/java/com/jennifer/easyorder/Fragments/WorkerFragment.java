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

import com.jennifer.easyorder.Adapter.WorkerAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentWorkerBinding;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkerFragment extends Fragment {

    private FragmentWorkerBinding binding;
    private RecyclerView recyclerView;
    private PaymentViewModel paymentViewModel;
    private List<Worker> itemsWorker;
    private WorkerAdapter rvWorkerAdapter;
    private SearchView searchViewWorker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWorkerBinding.inflate(inflater, container, false);
        paymentViewModel = new ViewModelProvider(requireActivity()).get(PaymentViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_worker);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvWorker.setLayoutManager(layoutManager);

//        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.et_search);
//        List<String> suggestions = Arrays.asList("Luis", "Banana", "Cereza", "Dátil", "Uva"); // Lista de sugerencias

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, suggestions);
//        autoCompleteTextView.setAdapter(adapter);

        searchViewWorker = view.findViewById(R.id.sv_searchWorker);
        searchViewWorker.clearFocus();

        searchViewWorker.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListWorker(newText);
                return true;
            }
        });

        RestaurantInterface workerInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Worker>> call = workerInterface.getShowWorker();

        call.enqueue(new Callback<List<Worker>>() {

            @Override
            public void onResponse(Call<List<Worker>> call, Response<List<Worker>> response) {
                itemsWorker = response.body();
                rvWorkerAdapter = new WorkerAdapter(itemsWorker, paymentViewModel, WorkerFragment.this);
                recyclerView.setAdapter(rvWorkerAdapter);
            }

            @Override
            public void onFailure(Call<List<Worker>> call, Throwable t) {
            }
        });
    }

    private void filterListWorker(String text) {
        List<Worker> filterList = new ArrayList<>();
        for (Worker item : itemsWorker) {
            String nomWorker = item.getNombres();
            if (nomWorker.toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(getContext(), "Personal no encontrado", Toast.LENGTH_SHORT).show();
        } else {
            rvWorkerAdapter.setFilterListWorker(filterList);
        }
    }

}