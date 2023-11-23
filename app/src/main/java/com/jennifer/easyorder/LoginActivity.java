package com.jennifer.easyorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jennifer.easyorder.data.RestaurantInterface;
import com.jennifer.easyorder.data.RetrofitHelper;
import com.jennifer.easyorder.databinding.ActivityLoginBinding;
import com.jennifer.easyorder.model.Role;
import com.jennifer.easyorder.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private SharedPreferences sharedPreferences;
    public static String SESSION_PREFERENCE = "SESSION_PREFERENCE";
    public static String SESSION_ACTIVATED = "SESSION_ACTIVATED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(SESSION_PREFERENCE, MODE_PRIVATE);

        boolean isSessionActivated = sharedPreferences.getBoolean(LoginActivity.SESSION_ACTIVATED, false);

        if (isSessionActivated) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        binding.loginButton.setOnClickListener(v -> {

            sharedPreferences.edit().putBoolean(SESSION_ACTIVATED, true).apply();

            String codigo = binding.txtCodigo.getText().toString();
            String contra = binding.txtContra.getText().toString();

            RestaurantInterface loginInterface = RetrofitHelper.getInstance().create(RestaurantInterface.class);
            Call<List<User>> call = loginInterface.getShowUser();

            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    List<User> items = response.body();
                    for (User user : items) {
                        if (user.getNombre().equals(codigo) && user.getContra().equals(contra)) {
                            Role role = user.getIdRolNavigation();
                            if (role.getIdRol() == 1) {
                                Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                                intent.putExtra(MainActivity.EMAIL, codigo);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            snackbarValid();
                            binding.txtCodigo.setText("");
                            binding.txtContra.setText("");
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                }
            });

        });
    }

    private void snackbarValid(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraint);
        Snackbar snackbar = Snackbar.make(constraintLayout, "",Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(R.layout.custom_snackbar_error, null);

        TextView txtTitle = custom.findViewById(R.id.txt_TitleMessage);
        txtTitle.setText("Credenciales incorrectas");

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0,0,0,0);

        (custom.findViewById(R.id.txt_Ok)).setOnClickListener(v -> {
            snackbar.dismiss();
        });
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }
}