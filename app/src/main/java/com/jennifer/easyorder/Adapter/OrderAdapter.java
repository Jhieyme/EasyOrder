package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Fragments.OrderFragment;
import com.jennifer.easyorder.Fragments.WorkerFragment;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.ItemOrderBinding;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ShowViewHolder> {

    private List<Order> orderList;
    private OrderFragment orderFragment;

    private boolean selectedMethodPay = false;

    public OrderAdapter(List<Order> orderList, OrderFragment orderFragment) {
        this.orderList = orderList;
        this.orderFragment = orderFragment;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Order oderItem = orderList.get(position);
        holder.bind(oderItem);
        Integer idComanda = oderItem.getIdComanda();




    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {

        private ItemOrderBinding binding;

        public ShowViewHolder(@NonNull ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Order order) {
            binding.txtNumero.setText("#0" + String.valueOf(order.getIdComanda()));

            Table table = order.getIdMesaNavigation();
            if (table != null) {
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
                showMethodPay();
            });
        }

        // Método que muestra el alert del tipo de pago
        public void showMethodPay() {
            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
            LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
            View customDialogView = inflater.inflate(R.layout.custom_method_pay, null);
            builder.setView(customDialogView);

            Button btnEfectivo = customDialogView.findViewById(R.id.btn_efectivo);
            Button btnTarjeta = customDialogView.findViewById(R.id.btn_tarjeta);
            ImageButton btnNext = customDialogView.findViewById(R.id.btn_next);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            btnEfectivo.setOnClickListener(v -> {
                orderFragment.showNotify();
                selectedMethodPay = true;
            });

            btnTarjeta.setOnClickListener(v -> {
                orderFragment.showNotify();
                selectedMethodPay = true;
            });

            btnNext.setOnClickListener(v -> {
                if (selectedMethodPay) {
                    showFragment();
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(orderFragment.getContext(), "Selecciona un método de pago", Toast.LENGTH_SHORT).show();
                }

            });
        }

        private void showFragment() {
            FragmentManager fragmentManager = orderFragment.getFragmentManager();
            WorkerFragment workerFragment = new WorkerFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fcv_main, workerFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
}
