package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.databinding.ViewProductBinding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.viewmodel.ProductViewModel;

import java.util.List;

public class ModalDetailOrderAdapter extends RecyclerView.Adapter<ModalDetailOrderAdapter.ShowViewHolder> {


    private List<NewProduct> listNewProduct;
    private ProductViewModel productViewModel;

    public ModalDetailOrderAdapter(List<NewProduct> listNewProduct, ProductViewModel productViewModel) {
        this.listNewProduct = listNewProduct;
        this.productViewModel = productViewModel;
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
        return listNewProduct.size();
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


            binding.btnRemoveProduct.setOnClickListener(view -> {
                int position = getAdapterPosition();
                notifyItemRemoved(position);
                productViewModel.deleteProduct(newProduct, view);
                listNewProduct.remove(position);
            });

            binding.btnAddQt.setOnClickListener(view -> {
                String currentQuantity = (String) binding.txtQuantity.getText().toString();
                int newQnt = Integer.parseInt(currentQuantity) + 1;
                if (newQnt > 10) {
                    Toast.makeText(view.getContext(), "¡No puedes seleccionar más de 10 platillos", Toast.LENGTH_SHORT).show();
                } else {
                    newProduct.setQuantity(newQnt);
                    binding.txtQuantity.setText(String.valueOf(newQnt));
                }
            });

            binding.btnMinusQt.setOnClickListener(view -> {
                String currentQuantity = (String) binding.txtQuantity.getText().toString();
                int newQnt = Integer.parseInt(currentQuantity) - 1;
                if (newQnt <= 0) {
                    newProduct.setQuantity(1);
                    binding.txtQuantity.setText(String.valueOf(1));
                    Toast.makeText(view.getContext(), "¡No puedes seleccionaar 0 en todo caso eliminalo", Toast.LENGTH_SHORT).show();
                } else {
                    newProduct.setQuantity(newQnt);
                    binding.txtQuantity.setText(String.valueOf(newQnt));
                }
            });


        }


    }

}
