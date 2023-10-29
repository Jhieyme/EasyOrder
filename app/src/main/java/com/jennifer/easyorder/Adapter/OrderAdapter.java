package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.ItemOrderBinding;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ShowViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList){
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bind(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {

        private ItemOrderBinding binding;
        public ShowViewHolder(@NonNull ItemOrderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.txtNumero.setText("#0" + String.valueOf(order.getIdComanda()));

            Table table = order.getIdMesaNavigation();
            if (table != null){
                binding.txtMesa.setText("N° de mesa: " + String.valueOf(table.getNroMesa()));
            }

            String dateTime = order.getFechaHora();
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date fecha = inputFormat.parse(dateTime);
                //SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                SimpleDateFormat outputFormat = new SimpleDateFormat("E, dd HH:mm a", new Locale("es", "PE"));
                String dateTimeFormat = outputFormat.format(fecha);

                binding.txtFecha.setText(dateTimeFormat);

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            binding.btnPagar.setOnClickListener(v -> {
                showMethod();
            });
        }
        public void showMethod(){
            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
            LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
            View customDialogView = inflater.inflate(R.layout.custom_dialog, null);
            builder.setView(customDialogView);
            builder.show();
        }
    }
}
