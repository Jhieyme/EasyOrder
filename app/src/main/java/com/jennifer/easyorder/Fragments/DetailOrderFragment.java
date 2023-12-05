package com.jennifer.easyorder.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.DetailOrderAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.FragmentDetailOrderBinding;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.utils.ComandaUtils;
import com.jennifer.easyorder.viewmodel.OrderViewModel;
import com.jennifer.easyorder.viewmodel.ProductViewModel;
import com.jennifer.easyorder.viewmodel.TableViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrderFragment extends Fragment {

    private FragmentDetailOrderBinding binding;
    private List<NewProduct> listFragment = new ArrayList<>();
    private Table tableSelected;
    private RecyclerView recyclerView;
    private OrderViewModel orderViewModel;

    private ProductViewModel productViewModel;
    private TableViewModel tableViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailOrderBinding.inflate(inflater, container, false);
        orderViewModel = new ViewModelProvider(requireActivity()).get(OrderViewModel.class);
        tableViewModel = new ViewModelProvider(requireActivity()).get(TableViewModel.class);
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //  Listando en el adapter el listFragment que obtengo del ProductAdapter
        recyclerView = view.findViewById(R.id.rvDetailOrder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        DetailOrderAdapter adapter = new DetailOrderAdapter(listFragment);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        double total = 0;
        TextView txtTotal = binding.txtTotalDetailOrder.findViewById(R.id.txtTotalDetailOrder);
        txtTotal.setText("S/. " + String.valueOf(0));


        for (NewProduct newProduct : listFragment) {

            // Calculando el subtotal y total recorriendo los items de listFragment
            double price = newProduct.getProduct().getPrecio();
            int quantity = newProduct.getQuantity();
            double pricexQuantity = price * quantity;
            total += pricexQuantity;
        }

        if (tableSelected != null) {
            binding.txtNumberTable.setText(String.valueOf(tableSelected.getNroMesa()));
        } else {
            binding.txtNumberTable.setText(" :'( ");
        }

        txtTotal.setText("S/. " + String.valueOf(total));


        double finalTotal = total;
        binding.btnGenerarComanda.setOnClickListener(v -> {
            // Formato de fecha de acuerdo a la API
            Date fechaActual = new Date();
            SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String parseFecha = fechaFormato.format(fechaActual);

            // Creación de nuevo objeto Order SIN ID
            Order newOrder = new Order(parseFecha, "En Proceso", finalTotal, tableSelected.getIdMesa());

            // Instancia de clase Utils para los metodos CRUD
            ComandaUtils utils = new ComandaUtils(orderViewModel);

            RestaurantInterface comandaInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
            Call<Order> callApi = comandaInterface.addOrder(newOrder);
            callApi.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {
                    if (response.isSuccessful()) {
                        //Mensaje de exito
                        Order orderResponse = response.body();

                        for (NewProduct np : listFragment) {
                            double price = np.getProduct().getPrecio();
                            int quantity = np.getQuantity();
                            double pricexQuantity = price * quantity;

                            // Aqui creo la cantidad de detalles comanda (Producto) tiene la orden
                            DetailOrder newDetailOrder = new DetailOrder(np.getQuantity(), pricexQuantity, pricexQuantity, orderResponse.getIdComanda(), np.getProduct().getIdProducto());
                            utils.postDetailOrder(newDetailOrder);
                        }
                    }
                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {
                    try {
                        throw new Throwable(t.getCause());
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            // Función para obtener el response body de la solicitud POST


            // Aqui setteo mediante ViewModel la mesa seleccionada
            tableViewModel.setSelectedTableImg(tableSelected);
            showSuccessAlert(DetailOrderFragment.this);

            // Aqui debo borrar los valores en el modal
            HashSet<NewProduct> emptyList = new HashSet<>();
            productViewModel.selectedListNewProduct(emptyList);
            tableViewModel.setSelectedTable(null);

            //Testeo de marcar ocupado más de una mesa!

            tableViewModel.addTableAssigned(tableSelected);


        });
    }

    private void showSuccessAlert(Fragment fragmentCurrent) {
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View customDialogView = inflater.inflate(R.layout.custom_success, null);
        builder.setView(customDialogView);


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        AppCompatButton btnContinue = customDialogView.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            alertDialog.dismiss();
            FragmentManager fragmentManager = fragmentCurrent.getFragmentManager();
            OrderFragment orderFragment = new OrderFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fcv_main, orderFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }


    public void putArgs(Bundle args) {
        HashSet<NewProduct> listProduct = (HashSet<NewProduct>) args.getSerializable("LIST");
        listFragment = new ArrayList<>(listProduct);
        tableSelected = (Table) args.getSerializable("MESA");


    }


}


