package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.jennifer.easyorder.databinding.DetailProductRowBinding;
import com.jennifer.easyorder.model.NewProduct;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.ShowViewHolder> {


    private List<NewProduct> productList;
    public DetailOrderAdapter(List<NewProduct> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public DetailOrderAdapter.ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DetailProductRowBinding binding = DetailProductRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailOrderAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailOrderAdapter.ShowViewHolder holder, int position) {
        holder.bind(productList.get(position));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private DetailProductRowBinding binding;

        public ShowViewHolder(@NonNull DetailProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewProduct newProduct) {
            binding.txtNombrePlatillo.setText(newProduct.getProduct().getNombre());
            binding.txtCategorias.setText(newProduct.getProduct().getIdCategoriaNavigation().getDescripcion());
            binding.txtPrecio.setText(String.format("S/. %.2f", newProduct.getProduct().getPrecio()));
            binding.txtCnt.setText(String.valueOf(newProduct.getQuantity()));
            Glide.with(itemView.getContext()).load(newProduct.getProduct().getUrlImagen()).into(binding.imgProduct2);
        }
    }
}
