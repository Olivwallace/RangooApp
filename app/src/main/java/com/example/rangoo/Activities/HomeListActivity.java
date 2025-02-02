package com.example.rangoo.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoo.Adapter.HomeAdapter;
import com.example.rangoo.Interfaces.AdapterListener;
import com.example.rangoo.Interfaces.ConfirmCallback;
import com.example.rangoo.Interfaces.SaveDataCallback;
import com.example.rangoo.Model.Food;
import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.Utils.SharedPreferecesSingleton;
import com.example.rangoo.databinding.ActivityHomeListBinding;
import com.example.rangoo.databinding.NavHeaderBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeListActivity extends AppCompatActivity {

    ActivityHomeListBinding binding;
    HomeAdapter homeAdapter;
    ArrayList<Food> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        binding = ActivityHomeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userList = getIntent().getExtras().getParcelableArrayList(getString(R.string.USER_LIST));
        if(userList.size() < 5) binding.btnAddMore.setVisibility(View.VISIBLE);
        if(userList.size() == 0) GoTo.homeView(HomeListActivity.this);

        homeAdapter = new HomeAdapter(userList);
        binding.recyclerView.setAdapter(homeAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new HomeListTouchHandler(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT));
        helper.attachToRecyclerView(binding.recyclerView);

        adapterListener();
        buttonsListener();
        drawerNavigation();

    }

    protected void adapterListener(){
        homeAdapter.setAdapterListener(new AdapterListener() {
            @Override
            public void onCardClick(Food item) {
                GoTo.detailsView(HomeListActivity.this, item);
            }

            @Override
            public void onAddClick(Food item) {
                // Não aplicavel nesse contexto.
            }
        });
    }

    @Override
    public void recreate() {
        super.recreate();
        getIntent().putExtra(getString(R.string.USER_LIST), homeAdapter.getList());
    }

    protected void buttonsListener(){
        binding.btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.weekMenu(HomeListActivity.this, userList);
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
                        GoTo.profileView(HomeListActivity.this);
                        break;
                    case R.id.nav_home:
                        recreate();
                        break;
                    case R.id.nav_about:
                        GoTo.aboutView(HomeListActivity.this);
                        break;
                    case R.id.mode_define:
                        SharedPreferecesSingleton.getInstance(getApplicationContext())
                                .setDarkMode(!SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode());
                        recreate();
                        break;
                    case R.id.nav_exit:
                        FirebaseNetwork.signOut();
                        GoTo.signInView(HomeListActivity.this);
                        break;
                    default:
                }

                binding.drawerLayout.close();
                return false;
            }

        });

    }


    protected void confirmDialog(ConfirmCallback callback) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.remove_item);
        dialog.setMessage(R.string.confirm_remove_item);

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

    /***
     * Classe auxiliar para auxiliar nos eventos em lista.
     */
    private class HomeListTouchHandler extends ItemTouchHelper.SimpleCallback {

        public HomeListTouchHandler(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();

            homeAdapter.notifyItemMoved(from, to);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();

            confirmDialog(new ConfirmCallback() {
                @Override
                public void onConfirm(boolean status) {
                    if (status){
                        homeAdapter.getList().remove(position);
                        homeAdapter.notifyItemRemoved(position);
                        homeAdapter.notifyItemRangeChanged(position, homeAdapter.getItemCount());

                        if(homeAdapter.getList().size() < 5){
                            ArrayList<String> userList = new ArrayList<>();
                            for(Food f: homeAdapter.getList()){
                                userList.add(f.getIdFood());
                            }

                            FirebaseNetwork.saveListUser(
                                    SharedPreferecesSingleton.getInstance(getApplicationContext()).getUserID(), userList, new SaveDataCallback() {
                                        @Override
                                        public void onSuccess(boolean success) {
                                            //
                                        }

                                        @Override
                                        public void onError(String error) {
                                            //
                                        }
                                    });
                            recreate();
                        }
                        else binding.btnAddMore.setVisibility(View.INVISIBLE);

                    } else {
                        homeAdapter.notifyItemChanged(position);
                    }
                }

                @Override
                public void onError(String status) {}
            });
        }
    }
}