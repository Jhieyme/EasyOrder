package com.jennifer.easyorder.Fragments;

import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.TableAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentTablesBinding;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.viewmodel.TableViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablesFragment extends Fragment {

    private FragmentTablesBinding binding;
    private RecyclerView recyclerView;
    private TableViewModel tableViewModel;
    private List<Table> itemsTable;
    private TableAdapter rvTableAdapter;
    private Table tableSelected;
    private HashSet<Table> listTablesAassigned;
    private SearchView searchViewTable;
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTablesBinding.inflate(inflater, container, false);

        // Se instancia cuando la vista se crea
        tableViewModel = new ViewModelProvider(requireActivity()).get(TableViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_table);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        binding.rvTable.setLayoutManager(layoutManager);
        tableViewModel.getSelectedTableImg().observe(getViewLifecycleOwner(), tableSelectedView -> {

            tableSelected = tableSelectedView;
        });

        tableViewModel.getListTableAssigned().observe(getViewLifecycleOwner(), tables -> {
            listTablesAassigned = tables;
        });

        // Validación de SearchView ---

        searchViewTable = view.findViewById(R.id.sv_searchTable);
        searchViewTable.clearFocus();
        searchViewTable.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListTable(newText);
                return true;
            }
        });

        //Lista de mesas --
        showTables();

    }

    private void showTables() {
        RestaurantInterface tableInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<List<Table>> call = tableInterface.getShowTable();

        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                if (response.isSuccessful()) {
                    itemsTable = response.body();
                    rvTableAdapter = new TableAdapter(itemsTable, tableViewModel, tableSelected, listTablesAassigned);
                    recyclerView.setAdapter(rvTableAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
            }
        });
    }

    private void filterListTable(String text) {
        List<Table> filterList = new ArrayList<>();
        for (Table item : itemsTable) {
            Integer numTable = item.getNroMesa();
            if (numTable.toString().contains(text)) {
                filterList.add(item);
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(getContext(), "Mesa no encontrada", Toast.LENGTH_SHORT).show();
        } else {
            rvTableAdapter.setFilterListTable(filterList);
        }
    }
}