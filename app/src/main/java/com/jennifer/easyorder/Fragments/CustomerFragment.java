package com.jennifer.easyorder.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jennifer.easyorder.Adapter.CustomerAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.data.RetrofitReniec;
import com.jennifer.easyorder.databinding.FragmentCustomerBinding;
import com.jennifer.easyorder.model.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerFragment extends Fragment {

    private FragmentCustomerBinding binding;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_customer);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvCustomer.setLayoutManager(layoutManager);

        RestaurantInterface customerInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
        RestaurantInterface dniInterface = RetrofitReniec.getInstance().create(RestaurantInterface.class);
        Call<List<Customer>> call = customerInterface.getShowCustomer();

        //Animacion del modal

        Button btnSearchDni = binding.btnSearchDni;
        ImageView icCustomer = binding.icAddCustomer;
        LinearLayout myKonten = binding.mykonten;
        LinearLayout overbox = binding.overbox;
        Context context = getActivity();
        Animation fromsmall = AnimationUtils.loadAnimation(context, R.anim.fromsmall);
        Animation fromnothing = AnimationUtils.loadAnimation(context, R.anim.fromnothing);
        Animation foricon = AnimationUtils.loadAnimation(context, R.anim.foricon);
        Animation togo = AnimationUtils.loadAnimation(context, R.anim.togo);

        myKonten.setAlpha(0);
        overbox.setAlpha(0);
        icCustomer.setVisibility(View.GONE);

        // Abrir modal
        binding.btnViewModel.setOnClickListener(v -> {
            icCustomer.setVisibility(View.VISIBLE);
            icCustomer.startAnimation(foricon);
            overbox.setAlpha(1);
            overbox.startAnimation(fromnothing);
            myKonten.setAlpha(1);
            myKonten.startAnimation(fromsmall);

        });

        // Cerrar modal

        binding.btnClose.setOnClickListener(v -> {
            overbox.startAnimation(togo);
            myKonten.startAnimation(togo);
            icCustomer.startAnimation(togo);
            icCustomer.setVisibility(View.GONE);

            ViewCompat.animate(myKonten).setStartDelay(1000).alpha(0).start();
            ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();

        });

        // Buscar dni desde la api - Reniec

        binding.btnSearchDni.setOnClickListener(v -> {
            String txtdni = binding.txtDni.getText().toString();
            if(txtdni != null){
                Call<Customer> dni = dniInterface.getCustomer(txtdni);
                dni.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        Customer customer = response.body();
                        binding.txtName.setText(customer.getNombres());
                        binding.txtApellido.setText(customer.getApellidoPaterno() + " " + customer.getApellidoMaterno());
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
            }
        });

        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> items = response.body();
                CustomerAdapter rvCustomerAdapter = new CustomerAdapter(items);
                recyclerView.setAdapter(rvCustomerAdapter);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });
    }
}