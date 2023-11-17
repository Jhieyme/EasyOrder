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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Fragments.OrderFragment;
import com.jennifer.easyorder.Fragments.WorkerFragment;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.ItemOrderBinding;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ShowViewHolder> {

    private List<Order> orderList;
    private OrderFragment orderFragment;

    private List<DetailOrder> detailOrderList;

    private PaymentViewModel paymentViewModel;

    private boolean selectedMethodPay = false;

    public OrderAdapter(List<Order> orderList, List<DetailOrder> detailOrderList, OrderFragment orderFragment, PaymentViewModel paymentViewModel) {
        this.orderList = orderList;
        this.detailOrderList = detailOrderList;
        this.orderFragment = orderFragment;
        this.paymentViewModel = paymentViewModel;
    }


    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bind(orderList.get(position), holder);

//        int idComanda = orderList.get(position).getIdComanda();
//
//        holder.binding.btnViewModelDetail.setOnClickListener(v -> {
//            RestaurantInterface productDetailInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
//            Call<List<DetailOrder>> callDetail = productDetailInterface.getShowDetail(idComanda);
//            callDetail.enqueue(new Callback<List<DetailOrder>>() {
//                @Override
//                public void onResponse(Call<List<DetailOrder>> call, Response<List<DetailOrder>> response) {
//                    if (response.isSuccessful()) {
//                        List<DetailOrder> item= response.body();
//                        List<DetailOrder> orderDetailsList = new ArrayList<>();
//
//                        for (DetailOrder detailOrder : item) {
//                            int idComandaD = detailOrder.getIdComandaNavigation().getIdComanda();
//
//                            if (idComandaD == idComanda ) {
//                                orderDetailsList.add(detailOrder);
//                            }
//                        }
//
////                        DetailProductAdapter detailProductAdapter = new DetailProductAdapter(orderDetailsList);
////                        RecyclerView rvDetailProduct = holder.itemView.findViewById(R.id.rvDetailProduct);
////                        LinearLayoutManager layoutManager = new LinearLayoutManager(orderFragment.getContext(), LinearLayoutManager.VERTICAL, false);
////                        System.out.println(layoutManager);
////                        rvDetailProduct.setLayoutManager(layoutManager);
////                        rvDetailProduct.setAdapter(detailProductAdapter);
//
//                        View customDialogView = LayoutInflater.from(orderFragment.getContext()).inflate(R.layout.custom_modal_order_detail, null);
//                        RecyclerView rvDetailProduct = customDialogView.findViewById(R.id.rvDetailProduct);
//                        LinearLayoutManager layoutManager = new LinearLayoutManager(orderFragment.getContext(), LinearLayoutManager.VERTICAL, false);
//                        rvDetailProduct.setLayoutManager(layoutManager);
//
//                        DetailProductAdapter detailProductAdapter = new DetailProductAdapter(orderDetailsList);
//                        rvDetailProduct.setAdapter(detailProductAdapter);
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(orderFragment.requireContext());
//                        builder.setView(customDialogView);
//
//                        AlertDialog alertDialog = builder.create();
//                        alertDialog.show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<List<DetailOrder>> call, Throwable t) {
//                }
//            });
//        });
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

        public void bind(Order order, ShowViewHolder holder) {
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


            // Obtenemos el idComanda del objeto Comanda
            int idComanda = order.getIdComanda();

            // Creamos una lista del detalle comanda de esa Comanda
            List<DetailOrder> detailOrders = new ArrayList<>();


            // Iteramos cada detalleComandaITEM ded la lista detailOrderList que tiene cada comanda
            for (DetailOrder detailOrderItem : detailOrderList) {

                // Del Item que estamos recorriendo en la primera iteración lo comparamos con el idComanda de arriba
                if (detailOrderItem.getIdComanda() == idComanda) {
                    // Si la condición se cumple se agrega a la lista creada
                    detailOrders.add(detailOrderItem);
                }
            }
            // RecyclerView - Adapter para mostrar el detalle comanda de cada comanda (esto se genera por cada Comanda que existe)
            DetailProductAdapter detailProductAdapter = new DetailProductAdapter(detailOrders);
            RecyclerView rvDetailProduct = holder.itemView.findViewById(R.id.rvDetailProduct);
            LinearLayoutManager layoutManager = new LinearLayoutManager(orderFragment.getContext(), LinearLayoutManager.VERTICAL, false);
            rvDetailProduct.setLayoutManager(layoutManager);
            rvDetailProduct.setAdapter(detailProductAdapter);

            binding.btnPrint.setOnClickListener(v -> {

                // TODO

            });


            binding.btnPagar.setOnClickListener(v -> {
                showMethodPay(order, detailOrders);
            });

        }

        // Método que muestra el alert del tipo de pago
        public void showMethodPay(Order order, List<DetailOrder> detailOrders) {
            // Inicializamos el Alert Builder
            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
            LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
            View customDialogView = inflater.inflate(R.layout.custom_method_pay, null);
            builder.setView(customDialogView);

            // Inicializamos los Widgets
            Button btnEfectivo = customDialogView.findViewById(R.id.btn_efectivo);
            Button btnTarjeta = customDialogView.findViewById(R.id.btn_tarjeta);
            ImageButton btnNext = customDialogView.findViewById(R.id.btn_next);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            btnEfectivo.setOnClickListener(v -> {
                orderFragment.showNotify();
                selectedMethodPay = true;
                // Aqui se setea solo el id xd (en realidad deberia hacer un get a la tabla Tipo de Pago, pero esa vaina que sea en EasyOrder3)
                paymentViewModel.setMethodPayDescription(1);

            });

            btnTarjeta.setOnClickListener(v -> {
                orderFragment.showNotify();
                selectedMethodPay = true;
                // Lo mismo aqui jeje
                paymentViewModel.setMethodPayDescription(2);
            });

            btnNext.setOnClickListener(v -> {
                if (selectedMethodPay) {

                    // Aqui si el metodo de pago fue seleccionado se setea con el ViewModel la COMANDA y su DETALLE COMANDA
                    paymentViewModel.setSelectOrder(order);
                    paymentViewModel.setSelectedDetailOrder(detailOrders);
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
