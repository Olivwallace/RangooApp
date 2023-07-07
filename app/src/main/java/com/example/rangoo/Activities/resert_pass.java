package com.example.rangoo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.Utils.StringUtils;
import com.example.rangoo.databinding.ActivityResertPassBinding;
import com.google.android.material.snackbar.Snackbar;

public class resert_pass extends AppCompatActivity {

    ActivityResertPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResertPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.signinLogin.getText().length() > 1 && StringUtils.isValidEmail(binding.signinLogin.getText().toString())){
                    FirebaseNetwork.recuperaSenha(binding.signinLogin.getText().toString());
                    Snackbar.make(findViewById(android.R.id.content), R.string.solicitacao_redefinicao_enviada, Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getColor(R.color.pumpkin)).show();
                    GoTo.signInView(resert_pass.this);
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "Email Inválido", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getColor(R.color.pumpkin)).show();
                }
            }
        });
    }
}