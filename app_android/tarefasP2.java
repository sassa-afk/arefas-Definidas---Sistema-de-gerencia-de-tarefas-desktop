package com.example.tarefasagente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class tarefasP2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarefas_p3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.p3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Api api = new Api();

        ProcessoGerais processoGerais = new ProcessoGerais()   ;
        processoGerais.acaoMenu(R.id.p3, this, tarefasP2.class);

//============================================================


        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        String token = sharedPreferences.getString("token", null);
        int idAgente = sharedPreferences.getInt("id", 0);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(1000);
                    runOnUiThread(() -> {

                        processoGerais.setAnexarImage(""+idAgente , tarefasP2.this, findViewById(R.id.fotoPerfilHeader));


                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
//============================================================
//============================================================

        Button pesquisar = findViewById(R.id.botaoFiltrar);
        pesquisar.setOnClickListener(rr ->{
            Spinner satatus = findViewById(R.id.statusTarefa);
            String valorStatus = satatus.getSelectedItem().toString();

            Spinner prioridade = findViewById(R.id.criticidadeTarefa);
            String valorPrioridade = prioridade.getSelectedItem().toString();

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            executorService.execute(()-> {

                Object[][] retApi = api.getTarefas(token, idAgente, valorStatus, valorPrioridade);





                if (retApi == null) {
                    runOnUiThread(() -> {
                        Toast.makeText(tarefasP2.this, "Não foi localizada as tarefas do filtro", Toast.LENGTH_SHORT).show();
                    });
                }
                else{
                    runOnUiThread(() -> {
                        LinearLayout rootLayouts = findViewById(R.id.idLinearLayoutEditarVerPedidos);
                        rootLayouts.removeAllViews();
                            processoGerais.separadoVIew(this, rootLayouts);

                    });
                    for (int i = 0; i < retApi.length; i++) {



                        LinearLayout rootLayouts = findViewById(R.id.idLinearLayoutEditarVerPedidos);
                         final Object [] valoresTarefa = { retApi[i][0], retApi[i][1], retApi[i][2], retApi[i][3], retApi[i][4], retApi[i][5], retApi[i][6], retApi[i][7], retApi[i][8] , retApi[i][9] };

                        runOnUiThread(() -> {


                            SharedPreferences userId = getSharedPreferences("user", Context.MODE_PRIVATE);
                            int idUser = userId.getInt("id", 0 );
                            String id_user = ""+idUser ;

                            processoGerais.indicaTarefasAgente2(this, rootLayouts,  valoresTarefa , token , id_user );



                        });


                    }
                    runOnUiThread(() -> {
                        LinearLayout rootLayouts = findViewById(R.id.idLinearLayoutEditarVerPedidos);
                         processoGerais.separadoVIew(this, rootLayouts);

                    });
            }

            });


        });




    }
}