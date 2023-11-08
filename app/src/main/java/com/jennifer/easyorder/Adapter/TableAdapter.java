package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.databinding.ItemTableBinding;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.viewmodel.TableViewModel;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ShowViewHolder> {

    private List<Table> tablesList;
    private TableViewModel tableViewModel;

    public TableAdapter(List<Table> tablesList, TableViewModel tableViewModel) {
        this.tablesList = tablesList;
        this.tableViewModel = tableViewModel;
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
            });
        }
    }
}