package com.jennifer.easyorder;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.jennifer.easyorder.Adapter.ModalDetailOrderAdapter;
import com.jennifer.easyorder.Fragments.CategoryFragment;
import com.jennifer.easyorder.Fragments.CustomerFragment;
import com.jennifer.easyorder.Fragments.DetailOrderFragment;
import com.jennifer.easyorder.Fragments.OrderFragment;
import com.jennifer.easyorder.Fragments.ProductFragment;
import com.jennifer.easyorder.Fragments.TablesFragment;
import com.jennifer.easyorder.Fragments.VoucherFragment;
import com.jennifer.easyorder.Fragments.WorkerFragment;
import com.jennifer.easyorder.animate.ProductViewAnimate;
import com.jennifer.easyorder.databinding.ActivityMainBinding;
import com.jennifer.easyorder.databinding.ModallayoutBinding;
import com.jennifer.easyorder.model.NewProduct;
import com.jennifer.easyorder.model.Table;
import com.jennifer.easyorder.viewmodel.ProductViewModel;
import com.jennifer.easyorder.viewmodel.TableViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private ModallayoutBinding bindingModal;
    private SharedPreferences sharedPreferences;
    public static String EMAIL = "EMAIL";
    private FragmentManager fragmentManager;

    private DetailOrderFragment detailOrderFragment = new DetailOrderFragment();

    // ViewModel
    private HashSet<NewProduct> listProduct = new HashSet<>();

    private ProductViewModel productViewModel;
    private TableViewModel tableViewModel;
    private Table tableSelected;

    // RecyclerView - Modal
    private RecyclerView recyclerViewModal;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        sharedPreferences = getSharedPreferences(LoginActivity.SESSION_PREFERENCE, MODE_PRIVATE);
        setContentView(binding.getRoot());
        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.teal_200, R.color.black);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Fragment fragmentActual = getSupportFragmentManager().findFragmentById(R.id.fcv_main);
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Class<?> fragmentClass = fragmentActual.getClass();

                        Fragment nuevoFragmento;
                        try {
                            nuevoFragmento = (Fragment) fragmentClass.newInstance();

                            if (nuevoFragmento instanceof DetailOrderFragment || nuevoFragmento instanceof VoucherFragment) {
                                binding.swipe.clearAnimation();
                            } else {
                                fragmentTransaction.replace(R.id.fcv_main, nuevoFragmento);
                                fragmentTransaction.commit();
                            }


                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InstantiationException e) {
                            throw new RuntimeException(e);
                        }


                        binding.swipe.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        // Instanciamos los ViewModel cuando se crea el activity;
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        tableViewModel = new ViewModelProvider(this).get(TableViewModel.class);
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
                binding.toolbar.setTitle("Comandas");
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


        // Usamos esa variable instanciada para poder obtener del LiveData la lista productos setteada;
        productViewModel.getSelectedListNewProduct().observe(this, list -> {
            listProduct = list;
        });


        // Lo mismo aqui xd
        tableViewModel.getSelectedTable().observe(this, table -> {
            tableSelected = table;

        });

        // Setteando texto en caso no haya una mesa seleccionada
        if (tableSelected != null) {
            bindingModal.txtNumberTable.setText(String.valueOf(tableSelected.getNroMesa()));
        } else {
            bindingModal.txtNumberTable.setText(" :( ");

        }


        // Mostrar layout por si no hay productos en lista
        if (listProduct.size() == 0) {
            View noProductsView = LayoutInflater.from(bindingModal.getRoot().getContext()).inflate(R.layout.noproducts_modal, null);
            LinearLayout linearProduct = bindingModal.linearProduct;
            linearProduct.addView(noProductsView);
            linearProduct.removeView(bindingModal.rvModal);

        }


        // Mostrando en Modal el RecyclerView
        recyclerViewModal = bindingModal.rvModal;
        LinearLayoutManager layoutModalManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ProductViewAnimate productViewAnimate = new ProductViewAnimate();
        bindingModal.rvModal.setLayoutManager(layoutModalManager);
        List<NewProduct> listNewProductoToList = new ArrayList<>(listProduct);
        ModalDetailOrderAdapter modalDetailOrderAdapter = new ModalDetailOrderAdapter(listNewProductoToList, productViewModel);
        recyclerViewModal.setItemAnimator(productViewAnimate);
        recyclerViewModal.setAdapter(modalDetailOrderAdapter);


        // Generar Comanda;
        Button btnComanda = dialog.findViewById(R.id.btnGenerarComanda);
        btnComanda.setOnClickListener(v -> {

            // Aqui estoy enviando los datos al Fragment DetailOrderFragment
            Bundle data = new Bundle();
            data.putSerializable("LIST", (Serializable) listProduct);
            data.putSerializable("MESA", (Serializable) tableSelected);
            if (listProduct.size() != 0) {
                if (tableSelected != null) {
                    detailOrderFragment.putArgs(data);
                    openFragment(detailOrderFragment);
                } else {
                    snackbarWarningTable();
                }
            } else {
                snackbarWarningProduct();
            }
            dialog.cancel();

        });

        // Dialogo
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 720);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    private void snackbarWarningTable() {
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        Snackbar snackbar = Snackbar.make(relativeLayout, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(R.layout.custom_snackbar_warning, null);

        TextView txtTitle = custom.findViewById(R.id.txtTitle);
        txtTitle.setText("Selecciona una mesa por favor.");

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);

        (custom.findViewById(R.id.txtOk)).setOnClickListener(v -> {
            snackbar.dismiss();
        });
        snackbarLayout.addView(custom, 0);
        snackbar.show();
    }

    private void snackbarWarningProduct() {
        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        Snackbar snackbar = Snackbar.make(relativeLayout, "", Snackbar.LENGTH_LONG);
        View custom = getLayoutInflater().inflate(R.layout.custom_snackbar_warning, null);

        TextView txtTitle = custom.findViewById(R.id.txtTitle);
        txtTitle.setText("Selecciona un producto por favor.");

        snackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        snackbarLayout.setPadding(0, 0, 0, 0);

        (custom.findViewById(R.id.txtOk)).setOnClickListener(v -> {
            snackbar.dismiss();
        });
        snackbarLayout.addView(custom, 0);
        snackbar.show();
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
        } else if (itemId == R.id.nav_logout) {
            showDialogLogout();
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

    private void showDialogLogout() {
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