package com.jennifer.easyorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

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
import com.jennifer.easyorder.Fragments.OrderFragment;
import com.jennifer.easyorder.Fragments.PrintFragment;
import com.jennifer.easyorder.Fragments.ProductFragment;
import com.jennifer.easyorder.Fragments.TablesFragment;
import com.jennifer.easyorder.Fragments.WorkerFragment;
import com.jennifer.easyorder.databinding.ActivityMainBinding;
import com.jennifer.easyorder.databinding.ItemProduct2Binding;
import com.jennifer.easyorder.model.NewProduct;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

  private ActivityMainBinding binding;
  private ItemProduct2Binding bindingProduct;
  private SharedPreferences sharedPreferences;
  public static String EMAIL = "EMAIL";
  private FragmentManager fragmentManager;

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
      } else if (itemId == R.id.bottom_table) {
        openFragment(new TablesFragment());
      } else if (itemId == R.id.bottom_menu) {
        openFragment(new ProductFragment());
      } else if (itemId == R.id.bottom_cashier) {
        openFragment(new CashierFragment());
      }
      return true;
    });

    fragmentManager = getSupportFragmentManager();
    openFragment(new CategoryFragment());

    binding.fab.setOnClickListener(view -> {


    });


  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.nav_category) {
      openFragment(new CategoryFragment());
    } else if (itemId == R.id.nav_customer) {
      openFragment(new CustomerFragment());
    } else if (itemId == R.id.nav_worker) {
      openFragment(new WorkerFragment());
    } else if (itemId == R.id.nav_order) {
      openFragment(new OrderFragment());
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