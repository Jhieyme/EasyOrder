package com.jennifer.easyorder.Adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemProduct2Binding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ShowViewHolder> {


  private List<Product> products;
  private List<NewProduct> listProduct = new ArrayList<>();

  public ProductAdapter(List<Product> products) {
    this.products = products;


  }


  @NonNull
  @Override
  public ProductAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemProduct2Binding binding = ItemProduct2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
    return new ProductAdapter.ShowViewHolder(binding);
  }

  @SuppressLint("ResourceType")
  @Override
  public void onBindViewHolder(@NonNull ProductAdapter.ShowViewHolder holder, int position) {
    holder.bind(products.get(position));


    holder.binding.imgProduct2.setOnClickListener(view -> {
      String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
      Product product = products.get(position);
      NewProduct newProduct = new NewProduct(product, Integer.parseInt(currentQuantity));

      boolean pExists = false;

      for (NewProduct np : listProduct) {
        if (np.getProduct().equals(newProduct.getProduct())) {
          pExists = true;
          break;
        }
      }


      if (!pExists) {
        listProduct.add(newProduct);
        Toast.makeText(view.getContext(), "¡Seleccionaste un platillo!", Toast.LENGTH_SHORT).show();
      } else {
        Toast.makeText(view.getContext(), "Este platillo ya está en la lista.", Toast.LENGTH_SHORT).show();
      }





    });


    holder.binding.btnAdd.setOnClickListener(view -> {
      String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
      int newQnt = Integer.parseInt(currentQuantity) + 1;
      if (newQnt > 10) {
        Toast.makeText(view.getContext(), "¡No puedes seleccionar más de 10 platillos", Toast.LENGTH_SHORT).show();
      } else {
        holder.binding.txtCnt.setText(String.valueOf(newQnt));
      }


    });
    holder.binding.btnMinus.setOnClickListener(view -> {
      String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
      int newQnt = Integer.parseInt(currentQuantity) - 1;
      if (newQnt < 0) {
        holder.binding.txtCnt.setText(String.valueOf(0));
      } else {
        holder.binding.txtCnt.setText(String.valueOf(newQnt));
      }

    });


  }

  @Override
  public int getItemCount() {
    return products.size();
  }

  public class ShowViewHolder extends RecyclerView.ViewHolder {
    private ItemProduct2Binding binding;

    public ShowViewHolder(@NonNull ItemProduct2Binding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    public void bind(Product product) {
      binding.txtName.setText(product.getNombre());
      binding.txtDescription.setText(product.getDescripcion());
      binding.txtPrecio.setText(String.format("S/. %.2f", product.getPrecio()));
      Glide.with(itemView.getContext()).load(product.getUrlImagen()).into(binding.imgProduct2);
    }
  }
}