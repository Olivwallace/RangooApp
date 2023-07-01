package com.example.rangoo.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.rangoo.Interfaces.AuthCallback;
import com.example.rangoo.Interfaces.GetUserCallback;
import com.example.rangoo.Interfaces.UploadImageCallback;
import com.example.rangoo.Interfaces.UriImageCallback;
import com.example.rangoo.Network.FirebaseNetwork;
import com.example.rangoo.R;
import com.example.rangoo.Utils.GoTo;
import com.example.rangoo.databinding.ActivityProfileBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private FirebaseNetwork firebase;
    private SharedPreferences preferences;

    @Override
    protected void onStart () {
        super.onStart();
        preferences = getSharedPreferences(getString(R.string._COM_RANGO_PREFERENCES), MODE_PRIVATE);
        getDataUser(preferences.getString("UID", ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebase = new FirebaseNetwork();

        binding.ibtnImageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateImage();
            }
        });

        binding.btnRecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(findViewById(android.R.id.content), "Solicitação de redefinição de senha enviada para seu email.", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getColor(R.color.pumpkin)).show();
            }
        });

        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebase.signOut(new AuthCallback() {
                    @Override
                    public void onSuccess(String id) {
                        GoTo.signInView(ProfileActivity.this);
                    }

                    @Override
                    public void onError(String error) {
                        Snackbar.make(findViewById(android.R.id.content), "Error ao efetuar signOut, tente novamente", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(getColor(R.color.baron)).show();
                    }
                });

            }
        });
    }

    protected void getDataUser(String UID){
        firebase.getDataUser(UID, new GetUserCallback() {

            @Override
            public void onSuccess(DataSnapshot data) {

                // Carrega imagem do perfil do usuário
                firebase.getUriImageUser(UID, new UriImageCallback() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(ProfileActivity.this)
                                .load(uri)
                                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                                .error(R.drawable.perfil_image_user)
                                .into(binding.ibtnImageUser);
                    }

                    @Override
                    public void onError(String error) {
                        Log.d("FALHA", error);
                    }
                });


                // Carrega dados do usuário
                binding.nameUser.setText(data.child("name").getValue(String.class));
                binding.emailUser.setText(data.child("email").getValue(String.class));
                binding.birthdayUser.setText(data.child("birthday").getValue(String.class));
                binding.addressUser.setText(data.child("address").getValue(String.class));
                binding.phoneUser.setText(data.child("phone").getValue(String.class));
            }

            @Override
            public void onError(String error) {
                Log.e("ERRO DATA_USER: ", error);
            }
        });
    }

    protected void updateImage(){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivity.launch(intent);
    }

    protected void uploadImage(Uri image) {
        firebase.uploadImageUser(image, preferences.getString("UID", ""), new UploadImageCallback() {
            @Override
            public void onSuccess(boolean success) {
                Snackbar.make(findViewById(android.R.id.content), "Imagem alterada com sucesso!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getColor(R.color.vegan)).show();
            }

            @Override
            public void onError(String error) {
                Log.e("FALHA UPLOAD IMAGE: ", error);
                Snackbar.make(findViewById(android.R.id.content), "Falha ao realizar salvamento da imagem!", Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(getColor(R.color.baron)).show();
            }
        });
    }

    //----------------------- Activity Result
    ActivityResultLauncher<Intent> startActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                    binding.ibtnImageUser.setImageURI(null);
                    binding.ibtnImageUser.setImageURI(result.getData().getData());
                    uploadImage(result.getData().getData());
                }else{
                    Log.d("ERRO: ", "" + result.getResultCode());
                    Snackbar.make(findViewById(android.R.id.content), "Falha ao realizar salvamento da imagem!", Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(getColor(R.color.baron)).show();
                }
            }
    );
}