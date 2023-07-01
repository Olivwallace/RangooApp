package com.example.rangoo.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rangoo.Adapter.HomeAdapter;
import com.example.rangoo.Model.Food;
import com.example.rangoo.R;
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

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        binding.btnOpenDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    protected ArrayList<Food> getUserList(){
        ArrayList<Food> arrayList = new ArrayList<>();
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        arrayList.add(new Food("id2", "Arroz Doce", "Arroz doce aromatizado e com uma pitada adicional de canela por cima.", "O arroz doce feito com arroz de grão curto, leite, açúcar, canela e casca de limão para dar sabor extra.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2FarrozDoce.jpeg?alt=media&token=cddd2e36-e657-4da2-b0fa-b552f4b9e706" ));
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        arrayList.add(new Food("id2", "Arroz Doce", "Arroz doce aromatizado e com uma pitada adicional de canela por cima.", "O arroz doce feito com arroz de grão curto, leite, açúcar, canela e casca de limão para dar sabor extra.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2FarrozDoce.jpeg?alt=media&token=cddd2e36-e657-4da2-b0fa-b552f4b9e706" ));
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        arrayList.add(new Food("id2", "Arroz Doce", "Arroz doce aromatizado e com uma pitada adicional de canela por cima.", "O arroz doce feito com arroz de grão curto, leite, açúcar, canela e casca de limão para dar sabor extra.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2FarrozDoce.jpeg?alt=media&token=cddd2e36-e657-4da2-b0fa-b552f4b9e706" ));
        arrayList.add(new Food("id1","Franguinho", " Frango cozido em um molho saboroso a base de tomate e outras combinações de ingredientes.", "O frango ao molho foi feito com pedaços de frango, temperados com sal, pimenta e outros temperos a gosto, cozidos em um molho que contém ingredientes como tomate, cebola, alho, ervas, especiarias.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2Ffranguinho.png?alt=media&token=811d5335-f23c-48a2-bf9d-b610d0983adb"));
        arrayList.add(new Food("id2", "Arroz Doce", "Arroz doce aromatizado e com uma pitada adicional de canela por cima.", "O arroz doce feito com arroz de grão curto, leite, açúcar, canela e casca de limão para dar sabor extra.", "https://firebasestorage.googleapis.com/v0/b/rangooapp-16032.appspot.com/o/imagens%2FarrozDoce.jpeg?alt=media&token=cddd2e36-e657-4da2-b0fa-b552f4b9e706" ));
        return arrayList;
    }

}