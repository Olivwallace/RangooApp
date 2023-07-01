package com.example.rangoo.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rangoo.Model.Food;
import com.example.rangoo.R;

import org.jetbrains.annotations.NotNull;

public class ListViewHolder extends RecyclerView.ViewHolder {

    TextView name, resume;

    public ListViewHolder(@NotNull View view){
        super(view);
    }

    public void bind(Food food){
        name = itemView.findViewById(R.id.cardList_name);
        resume = itemView.findViewById(R.id.cardList_resume);
    }

}
