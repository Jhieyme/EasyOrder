package com.jennifer.easyorder.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.databinding.ItemCustomerBinding;
import com.jennifer.easyorder.model.Customer;


import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ShowViewHolder> {
  private List<Customer> customers;

  public CustomerAdapter(List<Customer> customers) {
    this.customers = customers;
  }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ItemCustomerBinding binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
      return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
      holder.bind(customers.get(position));
    }

    @Override
    public int getItemCount() {
      return customers.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {
        private ItemCustomerBinding binding;
        public ShowViewHolder(@NonNull ItemCustomerBinding binding) {
          super(binding.getRoot());
          this.binding = binding;
        }

        public void bind(Customer customer) {
          binding.txtName.setText(customer.getNombres());
          binding.txtLastname.setText(customer.getApellidos());
          binding.txtDni.setText(customer.getDni());
        }
    }
}
