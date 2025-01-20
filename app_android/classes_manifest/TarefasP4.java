package com.example.tarefasagente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TarefasP4 extends AppCompatActivity {
    Api api = new Api() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarefas_p4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.p4), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

// -------------------------------------------------------------------------------------
        ProcessoGerais processoGerais = new ProcessoGerais();

//        .....................................................................................

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String auth = sharedPreferences.getString("token", null);
        int id = sharedPreferences.getInt("id", 0);
        boolean ativo = sharedPreferences.getBoolean("ativo", false);
//        String url = "http://192.168.0.114:3000/fotoPerfil?caminho_foto="+id;
        String url = api.setImgFoto(""+id) ;



        new Thread(new Runnable() {
            @Override
            public void run() {

                if ( TarefasP4.this != null) {

                    try {

                        Thread.sleep(1000 );
                        runOnUiThread(() -> {
//                            ImageView fotoPerfil = findViewById(R.id.fotoPerfilHeader);
//                            Glide.with(TarefasP4.this)
//                                    .load(url)
//                                    .placeholder(R.drawable.perfil)
//                                    .error(R.drawable.perfil)
//                                    .into(fotoPerfil);
                            processoGerais.setAnexarImage(""+id , TarefasP4.this, findViewById(R.id.fotoPerfilHeader));

                        });
                    } catch (InterruptedException e) {
                         Toast.makeText(TarefasP4.this, "Erro ao carregar dados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).start();
//============================================================


        processoGerais.acaoMenu(R.id.p4, this, TarefasP4.class);

//        Object[] dadosChamado = processoGerais.getC1(this) ;


// -------------------------------------------------------------------------------------


//     =========================
        BottomNavigationView bottomNavigationView  = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idItem = item.getItemId();
                Fragment selectedFragment = null;

                if (idItem == R.id.descricao_tarefa){

                    selectedFragment = new Fragmento1();


                }else if (idItem == R.id.atendimento_tarefa){

                    selectedFragment = new Fragmento2();


                }


                if (selectedFragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, selectedFragment);
                    fragmentTransaction.commit();
                }

                return true ;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.descricao_tarefa);

//     =========================


    }
}
