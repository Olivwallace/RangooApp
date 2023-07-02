package com.example.rangoo.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rangoo.Adapter.ListAdapter;
import com.example.rangoo.Interfaces.GetDataCallback;
import com.example.rangoo.Interfaces.GetListCallback;
import com.example.rangoo.Model.Food;
import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.databinding.ActivityWeekMenuBinding;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class WeekMenuActivity extends AppCompatActivity {

    FirebaseNetwork firebase;
    ActivityWeekMenuBinding binding;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeekMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebase = new FirebaseNetwork();

        getWeekMenu(new GetListCallback() {
            @Override
            public void onSuccess(ArrayList<Food> list) {
                Log.d("ENTROU", list.get(0).getName());
                listAdapter = new ListAdapter(list);
                binding.recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onError(String error) {
                Log.d("Erro", error);
            }
        });
    }

    protected void getWeekMenu(GetListCallback callback) {
        firebase.getWeekMenu(new GetDataCallback() {
            @Override
            public void onSuccess(DataSnapshot data) {
                ArrayList<Food> foodsList = new ArrayList<>();

                for(DataSnapshot item: data.getChildren()){
                    foodsList.add(new Food(
                            item.getKey(),
                            item.child("name").getValue(String.class),
                            item.child("resumo").getValue(String.class),
                            item.child("description").getValue(String.class),
                            item.child("imagem").getValue(String.class)
                    ));
                }

                callback.onSuccess(foodsList);
            }

            @Override
            public void onError(String error) {
                Log.e("ERRO", error);
                callback.onError(error);
            }
        });
    }
}