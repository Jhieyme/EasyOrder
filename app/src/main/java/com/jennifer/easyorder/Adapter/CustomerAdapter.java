package com.jennifer.easyorder.Adapter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.jennifer.easyorder.Fragments.CustomerFragment;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.ItemCustomerBinding;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.utils.ShowAlertCustom;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ShowViewHolder> {
    private List<Customer> customersList;
    private PaymentViewModel paymentViewModel;
    private CustomerFragment customerFragment;
    private ShowAlertCustom alertCustom = new ShowAlertCustom();
    ;


    public CustomerAdapter(List<Customer> customersList, PaymentViewModel paymentViewModel, CustomerFragment customerFragment) {
        this.customersList = customersList;
        this.paymentViewModel = paymentViewModel;
        this.customerFragment = customerFragment;


    }

    public void updateCustomers(List<Customer> newCustomers) {
        customersList = newCustomers;
        notifyDataSetChanged();
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

            if (customer.isActivo() == true) {
                binding.txtStatus.setText("Habilitado");
            } else {
                binding.txtStatus.setText("Deshabilitado");
            }

            if (customer.getNombres() != null && !customer.getNombres().isEmpty()) {
                Bitmap image = generateImage(customer.getNombres());
                binding.imgCustomer.setImageBitmap(image);
            }

            binding.btnEdit.setOnClickListener(v -> {
                showMethodPut(customer);
            });

            binding.rlCustomer.setOnLongClickListener(v -> {
                showMethodDelete(customer);
                return false;
            });


            binding.imgCustomer.setOnClickListener(v -> {

                String nombreUPPER = customer.getNombres();
                String[] nombresList = nombreUPPER.split("\\s+");
                StringBuilder result = new StringBuilder();


                for (String word : nombresList) {

                    result.append(Character.toUpperCase(word.charAt(0)));
                    result.append(word.substring(1).toLowerCase());
                    result.append(" ");


                }

                String strCustom = result.toString();
                alertCustom.showCustomAlert(customerFragment.getContext(), "Seleccionaste al cliente", strCustom, customer, paymentViewModel, customerFragment);
                ;
            });


        }


        // Método para actualizar el estado del cliente
        public void showMethodPut(Customer customer) {
            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
            LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
            View customDialogView = inflater.inflate(R.layout.custom_status, null);
            builder.setView(customDialogView);

            Button btnSi = customDialogView.findViewById(R.id.btn_yes);
            Button btnNo = customDialogView.findViewById(R.id.btn_no);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            int id = customer.getIdCliente();
            String dni = customer.getDni();
            String nombres = customer.getNombres();
            String apellidos = customer.getApellidos();
            boolean activo = customer.isActivo();

            Customer updatedCustomer = new Customer(id, dni, nombres, apellidos, activo);

            btnSi.setOnClickListener(v -> {
                RestaurantInterface customerInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);

                if (activo == false) {
                    updatedCustomer.setActivo(true);
                } else {
                    updatedCustomer.setActivo(false);
                }

                Call<Customer> put = customerInterface.updateCustomer(customer.getIdCliente(), updatedCustomer);
                put.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        if (response.isSuccessful()){
                            System.out.println(response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
//                Call<List<Customer>> callUpdate = customerInterface.getShowCustomer();
//                callUpdate.enqueue(new Callback<List<Customer>>() {
//                    @Override
//                    public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
//                        List<Customer> updatedCustomers = response.body();
//                        if (updatedCustomers != null) {
//                            updateCustomers(updatedCustomers);
//                            System.out.println(response.body());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Customer>> call, Throwable t) {
//                    }
//                });
                customerFragment.showNotifyPut();
                alertDialog.dismiss();
            });

            btnNo.setOnClickListener(v -> {
                alertDialog.dismiss();
            });
        }


        // Método para eliminar un cliente
        public void showMethodDelete(Customer customer) {
            AlertDialog.Builder builder = new AlertDialog.Builder(binding.getRoot().getContext());
            LayoutInflater inflater = LayoutInflater.from(binding.getRoot().getContext());
            View customDialogView = inflater.inflate(R.layout.custom_delete, null);
            builder.setView(customDialogView);

            Button btnSi = customDialogView.findViewById(R.id.btn_yes);
            Button btnNo = customDialogView.findViewById(R.id.btn_no);

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            btnSi.setOnClickListener(v -> {
                RestaurantInterface customerInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);

                Call<Customer> delete = customerInterface.deleteCustomer(customer.getIdCliente());
                delete.enqueue(new Callback<Customer>() {
                    @Override
                    public void onResponse(Call<Customer> call, Response<Customer> response) {
                        System.out.println(response.code());
                    }

                    @Override
                    public void onFailure(Call<Customer> call, Throwable t) {

                    }
                });
                customerFragment.showNotifyDelete();
                alertDialog.dismiss();
                int position = getAdapterPosition();
                customersList.remove(position);
                notifyItemRemoved(position);
            });

            btnNo.setOnClickListener(v -> {
                alertDialog.dismiss();
            });
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
        paint.setColor(Color.LTGRAY);
        //paint.setColor(Color.rgb(153, 162, 173));
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint);
        paint.setColor(Color.WHITE);
        //paint.setColor(Color.rgb(153, 162, 173));
        paint.setTextSize(40);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawText(text.substring(0, 1), 40, 60, paint);
        return bitmap;
    }
}