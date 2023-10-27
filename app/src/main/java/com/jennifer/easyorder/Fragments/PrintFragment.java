package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentPrintBinding;
import com.jennifer.easyorder.model.Voucher;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrintFragment extends Fragment {


    private FragmentPrintBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPrintBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RestaurantInterface voucherInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        Call<Voucher> call = voucherInterface.getVoucher(2);




        call.enqueue(new Callback<Voucher>() {
            @Override
            public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Voucher voucher = response.body();
                    setBinding(voucher);
                }
            }

            @Override
            public void onFailure(Call<Voucher> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    public void setBinding(Voucher voucher) {
        binding.txtDni.setText(voucher.getIdCliente().getDni());
        binding.txtFecha.setText((CharSequence) voucher.getFechaHora());


    }
}