package com.jennifer.easyorder.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemProduct2Binding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.viewmodel.ProductViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ShowViewHolder> {

    private List<Product> productsList;

    private ProductViewModel productViewModel;
    private List<NewProduct> listNewProduct;

    public ProductAdapter(List<Product> productsList, ProductViewModel productViewModel) {
        this.productsList = productsList;
        this.productViewModel = productViewModel;
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

        holder.binding.imgProduct.setOnClickListener(view -> {
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
            if (product.isActivo()) {
                binding.txtName.setText(product.getNombre());
                binding.txtDescription.setText(product.getDescripcion());
                binding.txtPrecio.setText(String.format("S/. %.2f", product.getPrecio()));
                Glide.with(itemView.getContext()).load(product.getUrlImagen()).into(binding.imgProduct);
            }
        }
    }

    public void addProductToList(ShowViewHolder holder, int position, View view) {
        String currentQuantity = (String) holder.binding.txtCnt.getText().toString();
        Product product = productsList.get(position);
        NewProduct newProduct = new NewProduct(product, Integer.parseInt(currentQuantity));

        if (newProduct.getQuantity() <= 0) {
            Toast.makeText(view.getContext(), "La cantidad minima es 1.", Toast.LENGTH_SHORT).show();
        } else {
            productViewModel.addProduct(newProduct, view);
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