package com.example.rangoo.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rangoo.Adapter.HomeAdapter;
import com.example.rangoo.Interfaces.ConfirmCallback;
import com.example.rangoo.Model.Food;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.databinding.ActivityHomeListBinding;

import java.util.ArrayList;

public class HomeListActivity extends AppCompatActivity {

    ActivityHomeListBinding binding;
    HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        homeAdapter = new HomeAdapter(getUserList());
        binding.recyclerView.setAdapter(homeAdapter);

        ItemTouchHelper helper = new ItemTouchHelper(new HomeListTouchHandler(0, ItemTouchHelper.LEFT));
        helper.attachToRecyclerView(binding.recyclerView);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        binding.btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        binding.btnAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoTo.weekMenu(HomeListActivity.this);
            }
        });
    }

    protected ArrayList<Food> getUserList(){
        ArrayList<Food> arrayList = new ArrayList<>();
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        arrayList.add(new Food("id2", "Arroz Doce", "Arroz doce aromatizado e com uma pitada adicional de canela por cima.", "O arroz doce feito com arroz de grão curto, leite, açúcar, canela e casca de limão para dar sabor extra.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2FarrozDoce.jpeg?alt=media&token=cddd2e36-e657-4da2-b0fa-b552f4b9e706" ));
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        arrayList.add(new Food("id2", "Arroz Doce", "Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima. Arroz doce aromatizado e com uma pitada adicional de canela por cima.", "O arroz doce feito com arroz de grão curto, leite, açúcar, canela e casca de limão para dar sabor extra.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2FarrozDoce.jpeg?alt=media&token=cddd2e36-e657-4da2-b0fa-b552f4b9e706" ));
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        return arrayList;
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

                        if(homeAdapter.getList().size() < 5) binding.btnAddMore.setVisibility(View.VISIBLE);
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