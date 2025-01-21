package com.example.tarefasagente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class HeaderActivity extends AppCompatActivity {
    Api api = new Api() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.header);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.header), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String auth = sharedPreferences.getString("token", null);
        int id = sharedPreferences.getInt("id", 0);
        boolean ativo = sharedPreferences.getBoolean("ativo", false);


        ImageView fotoPerfil = findViewById(R.id.fotoPerfilHeader);
//
//
//        String url = api.setImgFoto(""+id)  ;
//
////        String url = "http://192.168.0.114:3000/fotoPerfil?caminho_foto="+id;
//
//
//        try{
//            try {
//                Glide.with(getApplicationContext())
//                        .load(url)
//                        .placeholder(R.drawable.perfil)
//                        .error(R.drawable.tarefas)
//                        .into(fotoPerfil);
//            } catch (Exception e) {
//                System.out.println("Erro ao carregar imagem: " + e.getMessage());
//            }
//        } catch (Exception e){
//            fotoPerfil.setImageResource(R.drawable.perfil);
//        }

//        String url = "https://img.freepik.com/fotos-premium/operarios-trabalhando-no-laptop-na-fabrica-de-bebidas_107420-75827.jpg";


    }
}