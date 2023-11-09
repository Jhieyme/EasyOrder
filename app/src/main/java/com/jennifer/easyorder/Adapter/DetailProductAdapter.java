package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jennifer.easyorder.databinding.OrderProductRowBinding;
import com.jennifer.easyorder.model.DetailOrder;
import java.util.List;

public class DetailProductAdapter extends RecyclerView.Adapter<DetailProductAdapter.DetailProductViewHolder> {
    private List<DetailOrder> detailOrderList;

    public DetailProductAdapter(List<DetailOrder> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    @NonNull
    @Override
    public DetailProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderProductRowBinding binding = OrderProductRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DetailProductAdapter.DetailProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailProductViewHolder holder, int position) {
            holder.bind(detailOrderList.get(position));
    }

    @Override
    public int getItemCount() {
        return detailOrderList.size();
    }

    public class DetailProductViewHolder extends RecyclerView.ViewHolder {
        private OrderProductRowBinding binding;

        public DetailProductViewHolder(@NonNull OrderProductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DetailOrder detailOrder) {

            binding.txtCantidad.setText("X " + String.valueOf(detailOrder.getCantidad()));
            binding.txtProduct.setText(detailOrder.getIdProductoNavigation().getNombre());
        }
    }
}
