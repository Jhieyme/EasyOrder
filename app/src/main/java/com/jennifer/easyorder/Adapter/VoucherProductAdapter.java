package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.databinding.VoucherproductRowBinding;
import com.jennifer.easyorder.model.DetailOrder;

import java.util.List;

public class VoucherProductAdapter extends RecyclerView.Adapter<VoucherProductAdapter.ShovViewHolder> {


    // Esta lista viene seteada del ViewModel (Order Adapter)
    private List<DetailOrder> detailOrderList;


    public VoucherProductAdapter(List<DetailOrder> detailOrderList) {
        this.detailOrderList = detailOrderList;
    }

    @NonNull
    @Override
    public ShovViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VoucherproductRowBinding binding = VoucherproductRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ShovViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShovViewHolder holder, int position) {
        DetailOrder detailOrder = detailOrderList.get(position);
        holder.bind(detailOrder);
    }

    @Override
    public int getItemCount() {
        return detailOrderList.size();
    }

    public class ShovViewHolder extends RecyclerView.ViewHolder {
        private VoucherproductRowBinding binding;


        public ShovViewHolder(@NonNull VoucherproductRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void bind(DetailOrder detailOrder) {
            int cantidad = detailOrder.getCantidad();
            double precio = detailOrder.getIdProductoNavigation().getPrecio();
            double importe = detailOrder.getImporte();


            binding.tvNombre.setText(detailOrder.getIdProductoNavigation().getNombre());
            binding.tvCantidad.setText("x" + cantidad);
            binding.tvPrecio.setText("S/. " + precio);
            binding.tvImporte.setText("S/. " + importe);
        }
    }

}
