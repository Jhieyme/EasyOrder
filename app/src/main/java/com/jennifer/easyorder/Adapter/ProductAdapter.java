package com.jennifer.easyorder.Adapter;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemProduct2Binding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ShowViewHolder> {

    private List<Product> productsList;
    private List<NewProduct> listNewProducts = new ArrayList<>();
    private selectedProducts listener;

    public interface selectedProducts {
        void onAttach(@NonNull Context context);

        void onClickSelectedProducts(List<NewProduct> listNewProducts);
    }

    public ProductAdapter(List<Product> productsList, selectedProducts listener) {
        this.productsList = productsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProduct2Binding binding = ItemProduct2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ProductAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bind(productsList.get(position));

        holder.binding.imgProduct2.setOnClickListener(view -> {
            // Añadir productos con cantidad a lista para el detalle de orden
            addProductToList(holder, position, view);
        });

        holder.binding.btnAdd.setOnClickListener(view -> {
            // Aumentamos la cantidad de un producto
            addQntToOneProduct(holder, view);
        });

        holder.binding.btnMinus.setOnClickListener(view -> {

            // Reducimos la cantidad de un producto
            decreaseQntToOneProduct(holder, view);
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
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

    public void addProductToList(ShowViewHolder holder, int position, View view) {
        String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
        Product product = productsList.get(position);
        NewProduct newProduct = new NewProduct(product, Integer.parseInt(currentQuantity));
        boolean pExists = false;
        for (NewProduct np : listNewProducts) {
            if (np.getProduct().equals(newProduct.getProduct())) {
                pExists = true;
                break;
            }
        }
        if (!pExists && newProduct.getQuantity() > 0) {
            listNewProducts.add(newProduct);
            listener.onClickSelectedProducts(listNewProducts);
            Toast.makeText(view.getContext(), "¡Seleccionaste un platillo!", Toast.LENGTH_SHORT).show();
        } else {
            if (newProduct.getQuantity() == 0) {
                Toast.makeText(view.getContext(), "La cantidad minima es 1.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "Este platillo ya está en la lista.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void addQntToOneProduct(ShowViewHolder holder, View view) {

        String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
        int newQnt = Integer.parseInt(currentQuantity) + 1;
        if (newQnt > 10) {
            Toast.makeText(view.getContext(), "¡No puedes seleccionar más de 10 platillos", Toast.LENGTH_SHORT).show();
        } else {
            holder.binding.txtCnt.setText(String.valueOf(newQnt));
        }

    }


    public void decreaseQntToOneProduct(ShowViewHolder holder, View view) {
        String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
        int newQnt = Integer.parseInt(currentQuantity) - 1;
        if (newQnt < 0) {
            holder.binding.txtCnt.setText(String.valueOf(0));
        } else {
            holder.binding.txtCnt.setText(String.valueOf(newQnt));
        }
    }
}