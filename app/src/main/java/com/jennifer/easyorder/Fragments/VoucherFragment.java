package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentVoucherBinding;
import com.jennifer.easyorder.model.Voucher;
import com.jennifer.easyorder.viewmodel.VoucherViewModel;

public class VoucherFragment extends Fragment {

    private FragmentVoucherBinding binding;

    private VoucherViewModel voucherViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentVoucherBinding.inflate(inflater, container, false);
        voucherViewModel = new ViewModelProvider(requireActivity()).get(VoucherViewModel.class);
        voucherViewModel.getSelectedCustomer().observe(getViewLifecycleOwner(), item -> {
            System.out.println(item.getDni());
        });

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RestaurantInterface voucherInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);


    }

    public void setBinding(Voucher voucher) {
        binding.txtNroBoleta.setText("Nro Boleta: #" + voucher.getIdBoleta());
        binding.txtDni.setText(voucher.getIdClienteNavigation().getDni());
        binding.txtCliente.setText(voucher.getIdClienteNavigation().getNombres() + " " + voucher.getIdClienteNavigation().getApellidos());
        binding.txtCajero.setText(voucher.getIdWorkerNavigation().getNombres() + " " + voucher.getIdWorkerNavigation().getApellidos());
    }
}
