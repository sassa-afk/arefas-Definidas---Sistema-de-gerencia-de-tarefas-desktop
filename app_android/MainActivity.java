package com.example.tarefasagente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Api api = new Api();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ExecutorService segPlano = Executors.newSingleThreadExecutor();

        EditText usuario = findViewById(R.id.usuario);
        EditText senha = findViewById(R.id.senha);
        Button posLogin = findViewById(R.id.posLogin);




        posLogin.setOnClickListener(v -> {

            String user = usuario.getText().toString();
            String pass = senha.getText().toString();

             if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {

                 segPlano.execute(() -> {
                     Api api = new Api();

                     Object[] resultado = api.autLogin(user, pass);

                      boolean isAtivo = (Boolean) resultado[2] ;
                      int userId = (int) resultado[1] ;

                     if (isAtivo == true ) {

                         SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); // <-------
                         SharedPreferences.Editor editor = sharedPreferences.edit(); // <-------
                         editor.putString("token", (String) resultado[0]); // <-------
                         editor.putInt("id", userId); // <-------
                         editor.putBoolean("ativo", isAtivo ); // <-------
                         editor.apply();

                         runOnUiThread(() -> {
                             Intent intent = new Intent(this , tarefasP1.class );
                             startActivity(intent);
                         });
                     }

                     else if (isAtivo == false && userId == 0  ) {

                         runOnUiThread(() -> {
                             Toast.makeText(MainActivity.this, "Usuário ou senha invalidos", Toast.LENGTH_LONG).show();
                         });

                     }
                     else {

                         runOnUiThread(() -> {
                             Toast.makeText(MainActivity.this, "Erro de conexão tente novamente mais tarde " + resultado[0], Toast.LENGTH_LONG).show();
                         });

                     }
                 });

             }
        });

    }
}