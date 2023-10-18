package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.ItemProduct2Binding;
import com.jennifer.easyorder.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ShowViewHolder>{

    private List<Product> products;
    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProduct2Binding binding = ItemProduct2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProductAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ShowViewHolder holder, int position) {
        holder.bind(products.get(position));
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
            Glide.with(itemView.getContext()).load(product.getUrlImagen()).into(binding.imgProduct);
        }
    }
}