package com.jennifer.easyorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.jennifer.easyorder.databinding.ActivityLoginBinding;

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

            if (codigo.equals("admin") && contra.equals("1234")) {
                Intent intent = new Intent(LoginActivity.this, SplashActivity.class);
                intent.putExtra(MainActivity.EMAIL, binding.txtCodigo.getText().toString());
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}