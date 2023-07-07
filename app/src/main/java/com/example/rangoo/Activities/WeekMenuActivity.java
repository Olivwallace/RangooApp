package com.example.rangoo.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;

import com.example.rangoo.Adapter.ListAdapter;
import com.example.rangoo.Interfaces.AdapterListener;
import com.example.rangoo.Interfaces.ConfirmCallback;
import com.example.rangoo.Interfaces.GetListCallback;
import com.example.rangoo.Interfaces.SaveDataCallback;
import com.example.rangoo.Model.Food;
import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.Utils.SharedPreferecesSingleton;
import com.example.rangoo.databinding.ActivityWeekMenuBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class WeekMenuActivity extends AppCompatActivity {

    ActivityWeekMenuBinding binding;
    ListAdapter listAdapter;
    ArrayList<Food> userList;

    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeekMenuBinding.inflate(getLayoutInflater());
        if(SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(binding.getRoot());

        userList = getIntent().getExtras().getParcelableArrayList(getString(R.string.USER_LIST));
        UID = getIntent().getExtras().getString(getString(R.string.USER_ID));

        FirebaseNetwork.getWeekMenu(new GetListCallback() {
            @Override
            public void onSuccess(ArrayList<Food> list) {
                listAdapter = new ListAdapter(list);
                binding.recyclerView.setAdapter(listAdapter);

                listAdapterListener();
                buttonsListener();
                drawerNavigation();
            }

            @Override
            public void onError(String error) {
                Log.d("Erro", error);
            }
        });
    }

    protected void listAdapterListener(){
        listAdapter.setAdapterListener(new AdapterListener() {
            @Override
            public void onCardClick(Food item) {
                GoTo.detailsView(WeekMenuActivity.this, item);
            }

            @Override
            public void onAddClick(Food item) {
                if(userList.size() < 5){
                    userList.add(item);
                    Snackbar.make(findViewById(android.R.id.content), "Item adicionado a sua lista!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getColor(R.color.vegan)).show();
                }

                if(userList.size() == 5){
                    confirmDialog(new ConfirmCallback() {
                        @Override
                        public void onConfirm(boolean status) {
                            saveList();
                        }

                        @Override
                        public void onError(String status) {
                            /* Não necessário nesse contexto*/
                        }
                    });
                }
            }
        });
    }

    protected void buttonsListener(){
        binding.btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    protected void drawerNavigation(){
        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.item_profile:
                        GoTo.profileView(WeekMenuActivity.this);
                        break;
                    case R.id.nav_home:
                        GoTo.homeView(WeekMenuActivity.this);
                        break;
                    case R.id.nav_about:
                        GoTo.aboutView(WeekMenuActivity.this);
                        break;
                    case R.id.mode_define:
                        SharedPreferecesSingleton.getInstance(getApplicationContext())
                                .setDarkMode(!SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode());
                        recreate();
                        break;
                    case R.id.nav_exit:
                        FirebaseNetwork.signOut();
                        GoTo.signInView(WeekMenuActivity.this);
                        break;
                    default:
                }

                binding.drawerLayout.close();
                return false;
            }
        });


    }

    protected void saveList(){
        ArrayList<String> lista = new ArrayList<>();

        for (Food i : userList){
            lista.add(i.getIdFood());
        }

        FirebaseNetwork.saveListUser(UID, lista, new SaveDataCallback() {
            @Override
            public void onSuccess(boolean success) {
                GoTo.homeView(WeekMenuActivity.this);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    protected void confirmDialog(ConfirmCallback callback) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Lista Completa");
        dialog.setMessage("Confimar Escolhas?");

        dialog.setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onConfirm(true);
                dialogInterface.dismiss();
            }
        });

        dialog.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onConfirm(false);
                dialogInterface.dismiss();
            }
        });

        dialog.create().show();
    }
}