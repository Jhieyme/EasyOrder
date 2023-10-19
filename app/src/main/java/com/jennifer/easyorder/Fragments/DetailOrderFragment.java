package com.jennifer.easyorder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.FragmentDetailOrderBinding;

public class DetailOrderFragment extends Fragment {


  public FragmentDetailOrderBinding binding;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_detail_order, container, false);


  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    Bundle bundle = getArguments();
    if (bundle != null) {
      String quantity = bundle.getString("test", "0"); // "0" es el valor predeterminado si no se encuentra la clave
      System.out.println(quantity);
    }


  }


}