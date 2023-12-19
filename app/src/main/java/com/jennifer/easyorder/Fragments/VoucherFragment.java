package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jennifer.easyorder.Adapter.VoucherProductAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentVoucherBinding;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.utils.ShowAlertCustom;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;
import com.jennifer.easyorder.viewmodel.TableViewModel;
import com.jennifer.easyorder.viewmodel.VoucherViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class VoucherFragment extends Fragment {

    private FragmentVoucherBinding binding;
    private PaymentViewModel paymentViewModel;

    private RecyclerView recyclerView;
    private TableViewModel tableViewModel;

    private VoucherViewModel voucherViewModel;

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVoucherBinding.inflate(inflater, container, false);
        paymentViewModel = new ViewModelProvider(requireActivity()).get(PaymentViewModel.class);
        tableViewModel = new ViewModelProvider(requireActivity()).get(TableViewModel.class);
        voucherViewModel = new ViewModelProvider(requireActivity()).get(VoucherViewModel.class);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestaurantInterface voucherInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        recyclerView = view.findViewById(R.id.rvVoucherProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);


        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
        toolbar = getActivity().findViewById(R.id.toolbar);
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


        // Fecha
        Date fechaActual = new Date();
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
        String parseFecha = fechaFormato.format(fechaActual);
        binding.txtFecha.setText(parseFecha);

        // Hora
        Date horaActual = new Date();
        SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm", new Locale("es", "PE"));
        String parseHora = horaFormato.format(horaActual);
        binding.txtHora.setText(parseHora);

        // Bucle para definir el total
        double total = 0;
        for (DetailOrder detailOrder : detailOrderList) {
            total += detailOrder.getImporte();
        }


        binding.tvTotal.setText(String.valueOf("TOTAL: " + String.format("S/. %.2f", total)));


        binding.btnImprimir.setOnClickListener(v -> {
            ShowAlertCustom alertCustom = new ShowAlertCustom();
            alertCustom.showSuccesAlert(binding.getRoot().getContext(), VoucherFragment.this, codMethodPay, codOrder, codWorker, codCustomer, tableViewModel, voucherInterface, order, detailOrderList, voucherViewModel, toolbar, bottomNavigationView);

        });
    }


}
