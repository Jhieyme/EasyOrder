package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.VoucherProductAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentVoucherBinding;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Voucher;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.utils.ShowAlertCustom;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;
import com.jennifer.easyorder.viewmodel.TableViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VoucherFragment extends Fragment {

    private FragmentVoucherBinding binding;
    private PaymentViewModel paymentViewModel;

    private RecyclerView recyclerView;
    private TableViewModel tableViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVoucherBinding.inflate(inflater, container, false);
        paymentViewModel = new ViewModelProvider(requireActivity()).get(PaymentViewModel.class);
        tableViewModel = new ViewModelProvider(requireActivity()).get(TableViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestaurantInterface voucherInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        recyclerView = view.findViewById(R.id.rvVoucherProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        Order order = paymentViewModel.getSelectedOrder().getValue();


        List<DetailOrder> detailOrderList = paymentViewModel.getSelectedDetailOrder().getValue();


        Customer customer = paymentViewModel.getSelectedCustomer().getValue();
        binding.txtCliente.setText(customer.getNombres() + " " + customer.getApellidos());
        binding.txtDni.setText(customer.getDni());


        Worker worker = paymentViewModel.getWorkerLiveData().getValue();
        binding.txtCajero.setText(worker.getNombres() + " " + worker.getApellidos());

        int codMethodPay = paymentViewModel.getMethodPayLiveData().getValue();
        int codOrder = order.getIdComanda();
        int codWorker = worker.getIdPersonal();
        int codCustomer = customer.getIdCliente();


        // Mostrar Recycler view
        VoucherProductAdapter voucherProductAdapter = new VoucherProductAdapter(detailOrderList);
        recyclerView.setAdapter(voucherProductAdapter);


        Date fechaActual = new Date();
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
        String parseFecha = fechaFormato.format(fechaActual);
        binding.txtFecha.setText(parseFecha);

        Date horaActual = new Date();
        SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm", new Locale("es", "PE"));
        String parseHora = horaFormato.format(horaActual);
        binding.txtHora.setText(parseHora);

        binding.btnImprimir.setOnClickListener(v -> {
            Date fechaActual2 = new Date();
            SimpleDateFormat fechaFormato2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String parseFecha2 = fechaFormato2.format(fechaActual2);
            Voucher voucher = new Voucher(codCustomer, codOrder, codWorker, codMethodPay, parseFecha2);

            Call<Voucher> add = voucherInterface.addBoleta(voucher);

            add.enqueue(new Callback<Voucher>() {
                @Override
                public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                    if (response.isSuccessful()) {

                        int idOrder = order.getIdComanda();
                        int idMesa = order.getIdMesa();
                        double total = order.getTotal();
                        String fechaHora = order.getFechaHora();
                        String estado = "Completado";


                        Order updatedOrder = new Order(idOrder, idMesa, total, estado, fechaHora);
                        Call<Order> put = voucherInterface.updateOrder(updatedOrder, order.getIdComanda());
                        put.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {


                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {

                            }
                        });

                        ShowAlertCustom alertCustom = new ShowAlertCustom();
                        alertCustom.showSuccesAlert(binding.getRoot().getContext(), VoucherFragment.this);


                        // Aqui debo actualizar la mesa
                        tableViewModel.deleteTableAssigned(idMesa);


//
                    }
                }

                @Override
                public void onFailure(Call<Voucher> call, Throwable t) {
                    System.out.println(t.getMessage());
                }
            });


        });
    }


}
