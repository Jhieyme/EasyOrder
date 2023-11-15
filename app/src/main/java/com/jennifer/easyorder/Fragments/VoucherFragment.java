package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentVoucherBinding;
import com.jennifer.easyorder.viewmodel.CustomerViewModel;
import com.jennifer.easyorder.viewmodel.MethodPayViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;


public class VoucherFragment extends Fragment {

    private FragmentVoucherBinding binding;

    private CustomerViewModel customerViewModel;

    private MethodPayViewModel methodPayViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentVoucherBinding.inflate(inflater, container, false);


        customerViewModel = new ViewModelProvider(requireActivity()).get(CustomerViewModel.class);
        methodPayViewModel = new ViewModelProvider(requireActivity()).get(MethodPayViewModel.class);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestaurantInterface voucherInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);


        customerViewModel.getSelectedCustomer().observe(getViewLifecycleOwner(), customer -> {
            binding.txtDni.setText(customer.getDni());
            binding.txtCliente.setText(customer.getNombres() + " " + customer.getApellidos());
        });

        methodPayViewModel.getWorkerLiveData().observe(getViewLifecycleOwner(), worker -> {
            binding.txtCajero.setText(worker.getNombres() + " " + worker.getApellidos());
        });

        methodPayViewModel.getMethodPayLiveData().observe(getViewLifecycleOwner(), methodPay1 -> {
            // TODO
        });

        Date fechaActual = new Date();
        SimpleDateFormat fechaFormato = new SimpleDateFormat("dd/MM/yyyy");
        String parseFecha = fechaFormato.format(fechaActual);
        binding.txtFecha.setText(parseFecha);

        Date horaActual = new Date();
        SimpleDateFormat horaFormato = new SimpleDateFormat("HH:mm");
        String parseHora = horaFormato.format(horaActual);
        binding.txtHora.setText(parseHora);

        binding.btnImprimir.setOnClickListener(v -> {
            showCustomAlert();
        });


    }

    public void showCustomAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
        LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
        View customDialogView = inflater.inflate(R.layout.custom_alert_select, null);
        builder.setView(customDialogView);


        AppCompatButton btnContinue = customDialogView.findViewById(R.id.btnContinue);


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


}
