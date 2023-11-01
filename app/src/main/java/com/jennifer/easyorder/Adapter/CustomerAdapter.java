package com.jennifer.easyorder.Adapter;

import static com.jennifer.easyorder.R.color.buttonBg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.R;
import com.jennifer.easyorder.databinding.ItemCustomerBinding;
import com.jennifer.easyorder.model.Customer;


import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ShowViewHolder> {
  private List<Customer> customersList;

  public CustomerAdapter(List<Customer> customersList) {
    this.customersList = customersList;
  }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      ItemCustomerBinding binding = ItemCustomerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
      return new ShowViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
      holder.bind(customersList.get(position));
    }

    @Override
    public int getItemCount() {
      return customersList.size();
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

          if (customer.getNombres() != null && !customer.getNombres().isEmpty()) {
            Bitmap image = generateImage(customer.getNombres());
            binding.imgCustomer.setImageBitmap(image);
          }
        }
    }

    // Generar imagen con inicial
    private Bitmap generateImage(String text) {
        int width = 100;
        int height = 100;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint();
        //paint.setColor(Color.GRAY);
        paint.setColor(Color.rgb(185, 201, 193));
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawText(text.substring(0, 1), 40, 60, paint);
        return bitmap;
    }
}
