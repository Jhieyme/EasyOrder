package com.jennifer.easyorder.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemProductBinding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Product;
import com.jennifer.easyorder.viewmodel.ProductViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ShowViewHolder> {


    // Esta lista viene de la API
    private List<Product> productsList;
    private ProductViewModel productViewModel;


    public ProductAdapter(List<Product> productsList, ProductViewModel productViewModel) {
        this.productsList = productsList;
        this.productViewModel = productViewModel;
    }


    @NonNull
    @Override
    public ProductAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        Product productItem = productsList.get(position);
        holder.bind(productItem);

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
        private ItemProductBinding binding;

        public ShowViewHolder(@NonNull ItemProductBinding binding) {
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

        Product product = productsList.get(position);
        NewProduct newProduct = new NewProduct(product, 1);
        productViewModel.addProduct(newProduct, view);

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