package com.jennifer.easyorder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.jennifer.easyorder.Fragments.CashierFragment;
import com.jennifer.easyorder.Fragments.CategoryFragment;
import com.jennifer.easyorder.Fragments.CustomerFragment;
import com.jennifer.easyorder.Fragments.DetailOrderFragment;
import com.jennifer.easyorder.Fragments.OrderFragment;
import com.jennifer.easyorder.Fragments.PrintFragment;
import com.jennifer.easyorder.Fragments.ProductFragment;
import com.jennifer.easyorder.Fragments.TablesFragment;
import com.jennifer.easyorder.Fragments.WorkerFragment;
import com.jennifer.easyorder.databinding.ActivityMainBinding;
import com.jennifer.easyorder.databinding.ModallayoutBinding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ProductFragment.OnProductSelectedListener, TablesFragment.OnMesaSelectedListener {

    private ActivityMainBinding binding;
    private ModallayoutBinding bindingModal;
    private SharedPreferences sharedPreferences;
    public static String EMAIL = "EMAIL";
    private FragmentManager fragmentManager;
    private List<NewProduct> listProduct = new ArrayList<>();
    private Table table = new Table(1, 1);
    private DetailOrderFragment detailOrderFragment = new DetailOrderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        sharedPreferences = getSharedPreferences(LoginActivity.SESSION_PREFERENCE, MODE_PRIVATE);
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.open_nav, R.string.close_nav);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navigationDrawer.setNavigationItemSelectedListener(this);
        binding.bottomNavigation.setBackground(null);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.bottom_category) {
                openFragment(new CategoryFragment());
                binding.toolbar.setTitle("Categorias");
            } else if (itemId == R.id.bottom_table) {
                openFragment(new TablesFragment());
                binding.toolbar.setTitle("Mesas");
            } else if (itemId == R.id.bottom_menu) {
                openFragment(new ProductFragment());
                binding.toolbar.setTitle("Menú");
            } else if (itemId == R.id.bottom_order) {
                openFragment(new OrderFragment());
            }
            return true;
        });

        fragmentManager = getSupportFragmentManager();
        openFragment(new CategoryFragment());

        binding.fab.setOnClickListener(view -> {

            showBottomDialog();
        });
    }

    // Modal de DetalleProducto
    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.modallayout);
        bindingModal = ModallayoutBinding.inflate(LayoutInflater.from(this));
        dialog.setContentView(bindingModal.getRoot());
        LinearLayout linearProduct = dialog.findViewById(R.id.linearProduct);

        for (NewProduct np : listProduct) {
            View productView = getLayoutInflater().inflate(R.layout.view_product, null);
            TextView txtNameProduct = productView.findViewById(R.id.txtNameProduct);
            TextView txtQuantity = productView.findViewById(R.id.txtQuantity);
            ImageButton removeProduct = productView.findViewById(R.id.btnRemoveProduct);
            ImageButton addQuantity = productView.findViewById(R.id.btnAddQt);
            ImageButton minusQuantity = productView.findViewById(R.id.btnMinusQt);

            removeProduct.setOnClickListener(view -> {
                int index = -1;
                for (int i = 0; i < listProduct.size(); i++) {
                    if (listProduct.get(i).getProduct().getNombre().equals(np.getProduct().getNombre())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    listProduct.remove(index);
                    //Toast.makeText(view.getContext(), "Quitaste este platillo!", Toast.LENGTH_SHORT).show();
                    showNotify();
                    linearProduct.removeView(productView);
                }

            });
            addQuantity.setOnClickListener(view -> {
                String currentQuantity = (String) txtQuantity.getText().toString();
                int newQnt = Integer.parseInt(currentQuantity) + 1;
                if (newQnt > 10) {
                    Toast.makeText(view.getContext(), "¡No puedes seleccionar más de 10 platillos", Toast.LENGTH_SHORT).show();
                } else {
                    np.setQuantity(newQnt);
                    txtQuantity.setText(String.valueOf(newQnt));
                }
            });

            minusQuantity.setOnClickListener(view -> {
                String currentQuantity = (String) txtQuantity.getText().toString();
                int newQnt = Integer.parseInt(currentQuantity) - 1;
                if (newQnt <= 0) {
                    np.setQuantity(1);
                    txtQuantity.setText(String.valueOf(1));
                    Toast.makeText(view.getContext(), "¡No puedes seleccionaar 0 en todo caso eliminalo xd", Toast.LENGTH_SHORT).show();
                } else {
                    np.setQuantity(newQnt);
                    txtQuantity.setText(String.valueOf(newQnt));
                }
            });
            txtNameProduct.setText(np.getProduct().getNombre());
            txtQuantity.setText(String.valueOf(np.getQuantity()));
            linearProduct.addView(productView);
        }

        //Table
        int numberTable = table.getNroMesa();
        TextView txtTable = dialog.findViewById(R.id.txtNumberTable);
        txtTable.setText(String.valueOf(numberTable));

        // Generar Comanda;
        Button btnComanda = dialog.findViewById(R.id.btnGenerarComanda);
        btnComanda.setOnClickListener(v -> {

            // Aqui estoy enviando los datos al Fragment DetailOrderFragment
            Bundle data = new Bundle();
            data.putSerializable("LIST", (Serializable) listProduct);
            data.putInt("NRO_MESA", numberTable);
            detailOrderFragment.putArgs(data);
            openFragment(detailOrderFragment);
            dialog.cancel();

        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public void onProductSelected(List<NewProduct> selectedProducts) {
        listProduct = selectedProducts;
    }

    @Override
    public void onTableSelected(Table tableSelected) {
        table = tableSelected;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_category) {
            openFragment(new CategoryFragment());
            binding.toolbar.setTitle("Categorias");
        } else if (itemId == R.id.nav_customer) {
            openFragment(new CustomerFragment());
            binding.toolbar.setTitle("Clientes");
        } else if (itemId == R.id.nav_worker) {
            openFragment(new WorkerFragment());
            binding.toolbar.setTitle("Personal");
        } else if (itemId == R.id.nav_order) {
            openFragment(new OrderFragment());
            binding.toolbar.setTitle("Comandas");
        } else if (itemId == R.id.nav_print) {
            openFragment(new PrintFragment());
        } else if (itemId == R.id.nav_logout) {
            showDialog();
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fcv_main, fragment);
        fragmentTransaction.commit();
    }

    private void showNotify() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_message, null);
        final PopupWindow popup = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setAnimationStyle(R.style.PopupAnimation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popup.showAtLocation(layout, Gravity.LEFT | Gravity.TOP, 0, 330);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popup.dismiss();
                    }
                }, 2000);
            }
        }, 100);
        TextView message = layout.findViewById(R.id.txt_message);
        message.setText("¡Quitaste este platillo!");
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cerrar Sesión");
        builder.setMessage("¿Estás seguro que deseas cerrar sesión?");
        builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void logout() {
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}