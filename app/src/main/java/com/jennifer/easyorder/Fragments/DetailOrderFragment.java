package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Adapter.DetailOrderAdapter;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.FragmentDetailOrderBinding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DetailOrderFragment extends Fragment {


    private FragmentDetailOrderBinding binding;

    private List<NewProduct> listFragment = new ArrayList<>();
    private Table tableSelected;


    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDetailOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //  Listando en el adapter el listFragment que obtengo del ProductAdapter
        recyclerView = view.findViewById(R.id.rvDetailOrder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvDetailOrder.setLayoutManager(layoutManager);
        DetailOrderAdapter adapter = new DetailOrderAdapter(listFragment);
        recyclerView.setAdapter(adapter);


        double subTotal = 0;
        double total = 0;


        TextView txtSubTotal = binding.txtSubTotalDetail.findViewById(R.id.txtSubTotalDetail);
        TextView txtTotal = binding.txtTotalDetailOrder.findViewById(R.id.txtTotalDetailOrder);

        binding.txtSubTotalDetail.setText("S/. " + String.valueOf(0));
        binding.txtTotalDetailOrder.setText("S/. " + String.valueOf(0));


        for (NewProduct newProduct : listFragment) {

            // Calculando el subtotal y total recorriendo los items de listFragment
            double price = newProduct.getProduct().getPrecio();
            int quantity = newProduct.getQuantity();
            double pricexQuantity = price * quantity;
            subTotal += pricexQuantity;
        }


        txtSubTotal.setText("S/. " + String.valueOf(subTotal));
        txtTotal.setText("S/. " + String.valueOf(subTotal + 2));


    }


    public void putArgs(Bundle args) {
        HashSet<NewProduct> listProduct = (HashSet<NewProduct>) args.getSerializable("LIST");
        listFragment = new ArrayList<>(listProduct);
        tableSelected = (Table) args.getSerializable("MESA");


    }


}


