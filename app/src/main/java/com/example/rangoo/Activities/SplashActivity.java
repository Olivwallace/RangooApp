package com.example.rangoo.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.Utils.SharedPreferecesSingleton;

public class SplashActivity extends AppCompatActivity {

    private static final int _DELAY_INTENT = 3500;

    @Override
    protected void onStart () {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(getString(R.string._COM_RANGO_PREFERENCES), MODE_PRIVATE);
        GoTo.setUID(preferences.getString("UID", ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPreferecesSingleton.getInstance(getApplicationContext()).getDarkMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(SharedPreferecesSingleton.getInstance(getBaseContext()).getLoggedIn()){
                    GoTo.homeView(SplashActivity.this);
                } else {
                    GoTo.signInView(SplashActivity.this);
                }
            }
        }, _DELAY_INTENT);

    }
}