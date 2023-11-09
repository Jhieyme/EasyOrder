package com.jennifer.easyorder.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.CustomerAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.data.RetrofitReniec;
import com.jennifer.easyorder.databinding.FragmentCustomerBinding;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.NewCustomer;
import com.jennifer.easyorder.viewmodel.VoucherViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerFragment extends Fragment {

    private FragmentCustomerBinding binding;
    private RecyclerView recyclerView;
    private VoucherViewModel voucherViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        voucherViewModel = new ViewModelProvider(requireActivity()).get(VoucherViewModel.class);
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

        // Buscar dni desde la api - Reniec

        binding.btnSearchDni.setOnClickListener(v -> {
            String txtdni = binding.txtDni.getText().toString();

            if (txtdni != null) {
                Call<NewCustomer> dni = dniInterface.getCustomer(txtdni);
                dni.enqueue(new Callback<NewCustomer>() {
                    @Override
                    public void onResponse(Call<NewCustomer> call, Response<NewCustomer> response) {
                        NewCustomer customer = response.body();
                        binding.txtName.setText(customer.getNombres());
                        binding.txtApellido.setText(customer.getApellidoPaterno() + " " + customer.getApellidoMaterno());
                    }

                    @Override
                    public void onFailure(Call<NewCustomer> call, Throwable t) {

                    }
                });
            } else {
                Toast.makeText(getContext(), "El DNI debe tener 8 digitos", Toast.LENGTH_SHORT).show();
            }
        });

        //Lista de todos los CLientes
        call.enqueue(new Callback<List<Customer>>() {

            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> items = response.body();
                CustomerAdapter rvCustomerAdapter = new CustomerAdapter(items, voucherViewModel, CustomerFragment.this);
                recyclerView.setAdapter(rvCustomerAdapter);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {

            }
        });


        //Animacion del modal -------------------------------------------------------------------

        ImageView icCustomer = binding.icAddCustomer;
        LinearLayout myKonten = binding.mykonten;
        LinearLayout overbox = binding.overbox;
        ImageButton close = binding.btnClose;
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
            binding.txtName.setText("");
            binding.txtApellido.setText("");
            binding.txtDni.setText("");

            ViewCompat.animate(myKonten).setStartDelay(1000).alpha(0).start();
            ViewCompat.animate(overbox).setStartDelay(1000).alpha(0).start();

        });

        //Aqui hago la solicitud POST

        binding.btnAddCustomer.setOnClickListener(v -> {

            String nombres = binding.txtName.getText().toString();
            String apellidos = binding.txtApellido.getText().toString();
            String dni = binding.txtDni.getText().toString();

            if (nombres != null && apellidos != null && dni != null) {
                Customer newCustomer = new Customer(
                        dni, nombres, apellidos, true);

                Call<Customer> add = customerInterface.addCustomer(newCustomer);
                add.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response.code());
                            showNotifyAdd();
                            binding.txtName.setText("");
                            binding.txtApellido.setText("");
                            binding.txtDni.setText("");
                            close.performClick();
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {
                    }
                });
            }
        });
    }

    // Métodos para mostrar mensaje de exito
    public void showNotifyPut() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_message, null);
        final PopupWindow popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setAnimationStyle(R.style.PopupAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popup.showAtLocation(layout, Gravity.LEFT | Gravity.TOP, 0, 330);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popup.dismiss();
                    }
                }, 2000);
            }
        }, 100);
        TextView message = layout.findViewById(R.id.txt_message);
        message.setText("Estado actualizado exitosamente");
    }

    private void showNotifyAdd() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_message, null);
        final PopupWindow popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setAnimationStyle(R.style.PopupAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popup.showAtLocation(layout, Gravity.LEFT | Gravity.TOP, 0, 330);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popup.dismiss();
                    }
                }, 2000);
            }
        }, 100);
        TextView message = layout.findViewById(R.id.txt_message);
        message.setText("Cliente agregado exitosamente");
    }

    public void showNotifyDelete() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_message, null);
        final PopupWindow popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setAnimationStyle(R.style.PopupAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popup.showAtLocation(layout, Gravity.LEFT | Gravity.TOP, 0, 330);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popup.dismiss();
                    }
                }, 2000);
            }
        }, 100);
        TextView message = layout.findViewById(R.id.txt_message);
        message.setText("Cliente eliminado exitosamente");
    }
}