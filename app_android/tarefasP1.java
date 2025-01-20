package com.example.tarefasagente;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class tarefasP1 extends AppCompatActivity {

    Api api = new Api() ;

    public void onReusme(){
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarefas_p1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.p1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ProcessoGerais processoGerais = new ProcessoGerais();
        processoGerais.acaoMenu(R.id.p1, this, MainActivity.class);

//==============================================================

        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", null);
        int idUser = sharedPreferences.getInt("id", 0);



         ExecutorService segundoPano = Executors.newSingleThreadExecutor();

        segundoPano.execute(()->{
            String dados = api.nomeUser( ""+idUser, token );
            TextView eu = findViewById(R.id.eu);
            runOnUiThread(() -> {

                eu.setText(dados);

            });
        });
        
        new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                         Thread.sleep(0);
                        runOnUiThread(() -> {

                            processoGerais.setAnexarImage(""+idUser , tarefasP1.this, findViewById(R.id.fotoPerfilHeader));

                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

//============================================================

        processoGerais.setAnexarImage(""+idUser , this, findViewById(R.id.fotoPerfil));

        Button tarefa = findViewById(R.id.verTarefas);
        tarefa.setOnClickListener(v->{
            Intent intent = new Intent(this, tarefasP2.class);
            startActivity(intent);

        });

        Button resultado = findViewById(R.id.resultados);
        resultado.setOnClickListener(  e -> {
                    Intent intent = new Intent(this, TarefasP5.class);
                    startActivity(intent);
                });

        Button trocarSenha = findViewById(R.id.senha);
        trocarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout layout = new LinearLayout(tarefasP1.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 40, 50, 10);

                final EditText senhaAtual = new EditText(tarefasP1.this);
                senhaAtual.setHint("Nova senha");
                senhaAtual.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                final EditText confirmarSenha = new EditText(tarefasP1.this);
                confirmarSenha.setHint("Confirmar senha");
                confirmarSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                layout.addView(senhaAtual);
                layout.addView(confirmarSenha);

                AlertDialog.Builder alert = new AlertDialog.Builder(tarefasP1.this);
                alert.setTitle("Trocar Senha");
                alert.setView(layout);

                alert.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String novaSenha = senhaAtual.getText().toString();
                        String senhaConfirmada = confirmarSenha.getText().toString();
                        if (novaSenha.equals(senhaConfirmada) && !novaSenha.isEmpty()) {

                             ExecutorService sg2 = Executors.newSingleThreadExecutor();

                            sg2.execute(()->{

                                String retApi  = api.mudarSenha( novaSenha , token ,""+idUser ) ;
                                runOnUiThread(() -> {
                                    Toast.makeText(tarefasP1.this, ""+retApi, Toast.LENGTH_SHORT).show();
                                });
                            });
                        } else {
                            Toast.makeText(tarefasP1.this, "As senhas são diferentes ou estão vazias.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(tarefasP1.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                alert.create().show();
            }
        });

        Button editarContato = findViewById(R.id.editarContato);
        editarContato.setOnClickListener(e -> {

            LinearLayout layout = new LinearLayout(tarefasP1.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(50, 40, 50, 10);

            final EditText trocarTelefone = new EditText(tarefasP1.this);
            trocarTelefone.setHint("Novo telefone");
            trocarTelefone.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            layout.addView(trocarTelefone);

            AlertDialog.Builder alert = new AlertDialog.Builder(tarefasP1.this);
            alert.setTitle("Trocar telefone de contato ");
            alert.setView(layout);



            alert.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    String trocarTelefoneDados = trocarTelefone.getText().toString();
                     if ( !trocarTelefoneDados.isEmpty()) {

                        ExecutorService sg2 = Executors.newSingleThreadExecutor();
                        sg2.execute(()->{

//                            String retApi  = api.mudarSenha( novaSenha , token ,""+idUser ) ; << ===================================
                             runOnUiThread(() -> {
                                Toast.makeText(tarefasP1.this, "Aplicar troca de senha aqui ", Toast.LENGTH_SHORT).show();
                            });
                        });
                    } else {
                        Toast.makeText(tarefasP1.this, "Dados do telefone estão vazios.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(tarefasP1.this, "Operação cancelada.", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            alert.create().show();

        });

        Button novaFoto = findViewById(R.id.trocarFoto);
        novaFoto.setOnClickListener(vi -> {

            processoGerais.setAnexarImage(""+idUser , tarefasP1.this, findViewById(R.id.fotoPerfilHeader));


            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
             intent.setType("image/*");

            startActivityForResult(intent, 1);

        });

//============================================================

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData(); // Obter o URI da imagem selecionada
            String filePath = getRealPathFromURI(uri); // Obter o caminho real

            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", null);
            int idUsuarioLogado = sharedPreferences.getInt("id", -1);

            new Thread (() -> {

                String retAPI = api.editarEnviarArquivo(""+idUsuarioLogado , filePath , token);

                runOnUiThread(() -> {

                    Toast.makeText(this, " " + retAPI, Toast.LENGTH_SHORT).show();
                    Intent  initin = new Intent( this , tarefasP1.class);
                    startActivity(initin);

                    tarefasP1.this.recreate();

                });


            }).start();




            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+filePath);
        }
    }

     private String getRealPathFromURI(Uri uri) {
        String result = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (idx != -1) {
                    result = cursor.getString(idx);
                }
            }
            cursor.close();
        }
        if (result == null) {
            result = uri.getPath();
        }
        return result;
    }



}