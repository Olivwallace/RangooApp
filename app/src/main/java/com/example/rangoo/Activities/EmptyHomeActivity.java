package com.example.rangoo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.rangoo.R;

public class EmptyHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_home);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ImageButton btn_openDrawer = findViewById(R.id.btn_open_drawer);

        btn_openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}