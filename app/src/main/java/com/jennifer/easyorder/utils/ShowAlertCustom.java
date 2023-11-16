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

import com.jennifer.easyorder.Fragments.CategoryFragment;
import com.jennifer.easyorder.Fragments.CustomerFragment;
import com.jennifer.easyorder.Fragments.VoucherFragment;
import com.jennifer.easyorder.R;
import com.jennifer.easyorder.model.Customer;
import com.jennifer.easyorder.model.Worker;
import com.jennifer.easyorder.viewmodel.PaymentViewModel;

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

    public void showSuccesAlert(Context context, Fragment fragmentCurrent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View customDialogSuccess = inflater.inflate(R.layout.custom_success, null);
        builder.setView(customDialogSuccess);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        AppCompatButton btnContinue = customDialogSuccess.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> {
            alertDialog.dismiss();
            FragmentManager fragmentManager = fragmentCurrent.getFragmentManager();
            CategoryFragment categoryFragment = new CategoryFragment();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fcv_main, categoryFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

    }
}
