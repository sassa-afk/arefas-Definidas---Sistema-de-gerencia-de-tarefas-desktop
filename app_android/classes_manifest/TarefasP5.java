package com.example.tarefasagente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class TarefasP5 extends AppCompatActivity {

    Api api = new Api() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarefas_p5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_tarefas_p5), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tx1 = findViewById(R.id.tx1);
        TextView tx2 = findViewById(R.id.tx2);
        TextView tx3 = findViewById(R.id.tx3);
        TextView tx4 = findViewById(R.id.tx4);
        TextView tx5 = findViewById(R.id.tx5);


        ProcessoGerais processoGerais = new ProcessoGerais();
        processoGerais.acaoMenu(R.id.activity_tarefas_p5, this, TarefasP5.class);


        // ==============================================================

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 0);


        //============================================================

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(() -> {
                        processoGerais.setAnexarImage(""+id , TarefasP5.this, findViewById(R.id.fotoPerfilHeader));
                    });
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();



        //============================================================

        new Thread( new  Runnable(){

            @Override
            public void run(){

            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            String token  = sharedPreferences.getString("token", null);
            int id = sharedPreferences.getInt("id", 0);


                Object [] ret = api.getIndicadores( token ,""+id,""+id );


                int totaisTarefas = 0 ;
                for(int j = 0 ; j < ret.length ; j++){
                    totaisTarefas = totaisTarefas + Integer.parseInt(ret[j].toString());

                    System.out.println("\n"+j + "  >>> "+ ret[j] );

                }

                tx1.setText(">  Totais de Tarefas : "+totaisTarefas);
                tx2.setText(">  Tarefas abertas : "+ret[0]);
                tx3.setText(">  Tarefas em atendimento : "+ret[1]);
                tx4.setText(">  Tarefas concluidas : "+ret[2]);
                tx5.setText(">  Tarefas em validação : "+ret[3]);








            }
        }).start();



        //============================================================




    }

    private TextView createTextView(Context context, String text, int color, LinearLayout.LayoutParams layoutParams) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(layoutParams);
        textView.setText(text);
        textView.setTextColor(ContextCompat.getColor(context, color));
        return textView;
    }

    private LinearLayout.LayoutParams createLayoutParams( int left , int top, int largura , int altura) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(left, top, largura, altura);
        return layoutParams;

    }

    private Spinner createSpinner(Context context, String[] tiposStatus, LinearLayout.LayoutParams layoutParams ) {
        Spinner spinnerOP = new Spinner(context);
        ArrayAdapter<String> adapterStatus = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                tiposStatus);
        spinnerOP.setAdapter(adapterStatus);
        spinnerOP.setLayoutParams(layoutParams);

        return spinnerOP ;

    }

    private View dividir(Context context, LinearLayout.LayoutParams layoutParams) {

        View dividir1 = new View( context );
        dividir1.setLayoutParams(layoutParams);
        dividir1.setBackgroundColor( context.getResources().getColor(R.color.black));

        return dividir1 ;
    }
}
