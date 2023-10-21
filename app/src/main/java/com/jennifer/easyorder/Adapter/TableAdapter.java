package com.jennifer.easyorder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.databinding.ItemTableBinding;
import com.jennifer.easyorder.model.Table;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ShowViewHolder> {

    private List<Table> tables;
    private selectedTable listener;

    public interface selectedTable {
        void onAttach(@NonNull Context context);

        void onClickSelectedMesa(Table table);
    }

    public TableAdapter(List<Table> tables, selectedTable listener) {
        this.tables = tables;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TableAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTableBinding binding = ItemTableBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TableAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.ShowViewHolder holder, int position) {
        holder.bind(tables.get(position));

        holder.itemView.setOnClickListener(v -> {
            Table tableSelected = tables.get(position);
            listener.onClickSelectedMesa(tableSelected);
        });
    }

    @Override
    public int getItemCount() {
        return tables.size();
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
        }
    }
}