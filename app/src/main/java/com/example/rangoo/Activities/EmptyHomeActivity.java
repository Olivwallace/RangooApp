package com.example.rangoo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.Utils.SharedPreferecesSingleton;
import com.example.rangoo.databinding.ActivityEmptyHomeBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class EmptyHomeActivity extends AppCompatActivity {

    ActivityEmptyHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmptyHomeBinding.inflate(getLayoutInflater());
        if(SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(binding.getRoot());

        binding.btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.btnWeekmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.weekMenu(EmptyHomeActivity.this, new ArrayList<>());
            }
        });

        drawerNavigation();
    }

    protected void goToPerfilView(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    protected void drawerNavigation(){
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.item_profile:
                        GoTo.profileView(EmptyHomeActivity.this);
                        break;
                    case R.id.nav_home:
                        recreate();
                        break;
                    case R.id.nav_about:
                        GoTo.aboutView(EmptyHomeActivity.this);
                        break;
                    case R.id.mode_define:
                        SharedPreferecesSingleton.getInstance(getApplicationContext())
                                .setDarkMode(!SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode());
                        recreate();
                        break;
                    case R.id.nav_exit:
                        FirebaseNetwork.signOut();
                        GoTo.signInView(EmptyHomeActivity.this);
                        break;
                    default:
                }

                binding.drawerLayout.close();
                return false;
            }

        });

    }



}