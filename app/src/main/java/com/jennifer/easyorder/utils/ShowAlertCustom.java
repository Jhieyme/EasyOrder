package com.jennifer.easyorder.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jennifer.easyorder.Fragments.CustomerFragment;
import com.jennifer.easyorder.Fragments.VoucherFragment;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.DetailOrder;
import com.jennifer.easyorder.model.Order;
import com.jennifer.easyorder.model.Voucher;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;
import com.jennifer.easyorder.viewmodel.TableViewModel;
import com.jennifer.easyorder.viewmodel.VoucherViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAlertCustom {


    public interface CallFunction {
        void execute();
    }

    public void showCustomAlert(Context context, String strTitle, String strCustom, Object object, PaymentViewModel paymentViewModel, Fragment fragmentCurrent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View customDialogView = inflater.inflate(R.layout.custom_alert_select, null);
        builder.setView(customDialogView);


        AppCompatTextView tvTitle = customDialogView.findViewById(R.id.tvTitle);
        AppCompatTextView tvCustom = customDialogView.findViewById(R.id.tvCustom);

        AppCompatButton btnContinue = customDialogView.findViewById(R.id.btnContinue);
        AppCompatButton btnCancel = customDialogView.findViewById(R.id.btnCancel);

        tvTitle.setText(strTitle);
        tvCustom.setText(strCustom);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        btnContinue.setOnClickListener(v -> {
            if (object instanceof Customer) {
                Customer customer = (Customer) object;
                paymentViewModel.selectedCustomer(customer);
                alertDialog.dismiss();
                FragmentManager fragmentManager = fragmentCurrent.getFragmentManager();
                VoucherFragment voucherFragment = new VoucherFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fcv_main, voucherFragment);
                transaction.addToBackStack(null);
                transaction.commit();


            } else if (object instanceof Worker) {
                Worker worker = (Worker) object;
                paymentViewModel.setWorker(worker);
                alertDialog.dismiss();
                FragmentManager fragmentManager = fragmentCurrent.getFragmentManager();
                CustomerFragment customerFragment = new CustomerFragment();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fcv_main, customerFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        btnCancel.setOnClickListener(v -> {

            alertDialog.cancel();
        });
    }

    public void showSuccesAlert(Context context, Fragment fragmentCurrent, int codMethodPay, int codOrder, int codWorker, int codCustomer, TableViewModel tableViewModel, RestaurantInterface voucherInterface, Order order, List<DetailOrder> detailOrderList, VoucherViewModel voucherViewModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View customDialogSuccess = inflater.inflate(R.layout.custom_success, null);
        builder.setView(customDialogSuccess);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        AppCompatButton btnContinue = customDialogSuccess.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            payOrder(codCustomer, codOrder, codWorker, codMethodPay, voucherInterface, order, tableViewModel, voucherViewModel);
            alertDialog.dismiss();
            View voucherLayout = inflater.inflate(R.layout.print_layout_voucher, null);


//            FragmentManager fragmentManager = fragmentCurrent.getFragmentManager();
//            CategoryFragment categoryFragment = new CategoryFragment();
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            transaction.replace(R.id.fcv_main, categoryFragment);
//            transaction.addToBackStack(null);
//            transaction.commit();

            PDFPrinter pdfPrinter = new PDFPrinter();

            voucherViewModel.getSettedVoucher().observe(fragmentCurrent.getViewLifecycleOwner(), voucherResponse -> {


                pdfPrinter.printToVoucher(order, voucherLayout, context, detailOrderList, voucherResponse);
            });


        });

    }

    private void payOrder(int codCustomer, int codOrder, int codWorker, int codMethodPay, RestaurantInterface voucherInterface, Order order, TableViewModel tableViewModel, VoucherViewModel voucherViewModel) {

        Date fechaActual2 = new Date();
        SimpleDateFormat fechaFormato2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String parseFecha2 = fechaFormato2.format(fechaActual2);
        Voucher voucher = new Voucher(codCustomer, codOrder, codWorker, codMethodPay, parseFecha2);

        Call<Voucher> add = voucherInterface.addBoleta(voucher);

        add.enqueue(new Callback<Voucher>() {
            @Override
            public void onResponse(Call<Voucher> call, Response<Voucher> response) {
                if (response.isSuccessful()) {

                    int idOrder = order.getIdComanda();
                    int idMesa = order.getIdMesa();
                    double total = order.getTotal();
                    String fechaHora = order.getFechaHora();
                    String estado = "Completado";



                    voucherViewModel.setVoucherResponseBody(response.body());




                    Order updatedOrder = new Order(idOrder, idMesa, total, estado, fechaHora);
                    Call<Order> put = voucherInterface.updateOrder(updatedOrder, order.getIdComanda());
                    put.enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {


                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });


                    // Aqui debo actualizar la mesa
                    tableViewModel.deleteTableAssigned(idMesa);


//
                }
            }

            @Override
            public void onFailure(Call<Voucher> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }
}
