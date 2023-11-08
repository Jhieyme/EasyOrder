package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.databinding.ViewProductBinding;
import com.jennifer.easyorder.model.NewProduct;

import java.util.List;

public class ModalDetailOrderAdapter extends RecyclerView.Adapter<ModalDetailOrderAdapter.ShowViewHolder> {


    private List<NewProduct> listNewProduct;

    private ModalDetailOrderAdapter(List<NewProduct> listNewProduct) {
        this.listNewProduct = listNewProduct;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewProductBinding binding = ViewProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ModalDetailOrderAdapter.ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        NewProduct newProduct = listNewProduct.get(position);
        holder.bind(newProduct);

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private ViewProductBinding binding;

        public ShowViewHolder(@NonNull ViewProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewProduct newProduct) {

            binding.txtNameProduct.setText(newProduct.getProduct().getNombre());
            binding.txtQuantity.setText(String.valueOf(newProduct.getQuantity()));
            

        }


    }

}
