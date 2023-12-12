package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.ItemTableBinding;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.viewmodel.TableViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ShowViewHolder> {

    private List<Table> tablesList;
    private TableViewModel tableViewModel;
    private Table tableSelected;
    private HashSet<Table> listTableAssigned;

    private int selectedPosition = RecyclerView.NO_POSITION; // Default value indicating no selection


    public TableAdapter(List<Table> tablesList, TableViewModel tableViewModel, Table tableSelected, HashSet<Table> listTableAssigned) {
        this.tablesList = tablesList;
        this.tableViewModel = tableViewModel;
        this.tableSelected = tableSelected;
        this.listTableAssigned = listTableAssigned;
    }

    // Método para actualizar el List de SearchView
    public void setFilterListTable(List<Table> filterListTable) {
        tablesList = new ArrayList<>(filterListTable);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TableAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTableBinding binding = ItemTableBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TableAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.ShowViewHolder holder, int position) {
        holder.bind(tablesList.get(position));
    }

    @Override
    public int getItemCount() {
        return tablesList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private ItemTableBinding binding;

        public ShowViewHolder(@NonNull ItemTableBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bind(Table table) {
            int numeroMesa = table.getNroMesa();
            binding.txtNumero.setText("N° " + numeroMesa);

            binding.clothingCard.setOnClickListener(v -> {
                tableViewModel.setSelectedTable(table);


                Toast.makeText(v.getContext(), "¡Seleccionaste el N° de Mesa: " + table.getNroMesa(), Toast.LENGTH_SHORT).show();

            });
            if (listTableAssigned != null) {
                for (Table table1 : listTableAssigned) {
                    if (table1.getNroMesa() == table.getNroMesa()) {
                        binding.imgTableState.setImageResource(R.drawable.mesaocupada);
                        binding.clothingCard.setEnabled(false);
                    }
                }
            }
        }
    }
}