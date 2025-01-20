package com.example.tarefasagente;

import static android.text.TextUtils.substring;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.ReturnThis;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Initializable;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
public class ProcessoGerais    {


    public void acaoMenu(int idDoAtivyAtual, @NonNull AppCompatActivity activity, Class<? extends AppCompatActivity> activityIndicado) {

        DrawerLayout drawerLayout = activity.findViewById(idDoAtivyAtual);
        NavigationView navigationView = activity.findViewById(R.id.nav_view);
        ImageButton buttonDrawerToggle = activity.findViewById(R.id.botaoID);

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int idItem = item.getItemId();

                if (idItem == R.id.perfil) {
                    Intent intentMenu = new Intent( activity, tarefasP1.class);
                    activity.startActivity(intentMenu);

//                     Toast.makeText(activity, "Clicou no item perfil " + idItem, Toast.LENGTH_SHORT).show();


                }
                else if(idItem == R.id.tarefas){
                    Intent intentMenu = new Intent( activity, tarefasP2.class);
                    activity.startActivity(intentMenu);

//                    Toast.makeText(activity, "Clicou no item tarefas " + idItem, Toast.LENGTH_SHORT).show();


                }
                else if(idItem == R.id.resultados){
                    Intent intentMenu = new Intent(activity, TarefasP5.class);
                    activity.startActivity(intentMenu);

                }
                else if(idItem == R.id.logout){

                    Intent intentMenu = new Intent(activity, MainActivity.class);
                    intentMenu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    activity.startActivity(intentMenu);

                 }
                drawerLayout.close();
                return false;
            }
        });

    }

    public void indicaTarefasAgente2(Context context, LinearLayout principalLayout, Object[] valoresTarefa , String token, String idAgente) {

        String dataCompleta = (String) valoresTarefa[4];

        String ano  = dataCompleta.substring(0, 4) ;
        String mes = dataCompleta.substring(5, 7) ;
        String dia = dataCompleta.substring(8, 10) ;

        CardView cardView0 = new CardView(context);
        LinearLayout.LayoutParams cardLayoutParams0 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                650
        );
        cardView0.setLayoutParams(cardLayoutParams0);
        cardView0.setRadius(20);
        cardView0.setCardElevation(4);
        cardView0.setCardBackgroundColor(context.getResources().getColor(R.color.geral));


        LinearLayout cardLinearLayout0 = new LinearLayout(context);
        cardLinearLayout0.setOrientation(LinearLayout.HORIZONTAL);
        cardLinearLayout0.setPadding(20, 80, 16, 16);

// ----------------------------------------------------------------------------------------------------
// Primeiro CardView interno (cardView)

        CardView cardView = new CardView(context);
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                250,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        cardView.setLayoutParams(cardLayoutParams);
        cardView.setRadius(60);
        cardView.setCardElevation(4);
        cardView.setCardBackgroundColor(context.getResources().getColor(R.color.teal_200));

        LinearLayout cardContent1 = new LinearLayout(context);
        cardContent1.setOrientation(LinearLayout.VERTICAL);
        cardContent1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));

        String prio = (String) valoresTarefa [6] ;

        if (prio.equals("Baixa")) {
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.bege));
        }
        else if (prio.equals("Media")) {
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.amarela));
        }
        else if (prio.equals("Alta")) {
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.laranja));
        }
        else if (prio.equals("Urgente")) {
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.vermelha));
        }
        else {
            cardView.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
        }

// =======================  componentes ================================

        LinearLayout.LayoutParams textParms = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParms.setMargins(40, 100, 0, 0);

        TextView textMes = new TextView(context);
        textMes.setText(ano);
        textMes.setTextSize(20);
        textMes.setTextColor(context.getResources().getColor(R.color.geral));
        textMes.setLayoutParams(textParms);

//**************************************************

        LinearLayout.LayoutParams dv1Params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                5
        );
        dv1Params.setMargins(0, 20, 0, 0);

        View dv1 = new View(context);
        dv1.setLayoutParams(dv1Params);
        dv1.setBackgroundColor(context.getResources().getColor(R.color.geral));

//**************************************************


        LinearLayout.LayoutParams textParmsAno = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParmsAno.setMargins(40, 100, 0, 0);

        TextView textAno = new TextView(context);
        textAno.setText(dia+"/"+mes);
        textAno.setTextSize(20);
        textAno.setTextColor(context.getResources().getColor(R.color.geral));
        textAno.setLayoutParams(textParmsAno);


//**************************************************


        cardContent1.addView(textMes);
        cardContent1.addView(dv1);
        cardContent1.addView(textAno);

        cardView.addView(cardContent1);

// ----------------------------------------------------------------------------------------------------
// Segundo CardView interno (cardView2)
        CardView cardView2 = new CardView(context);
        LinearLayout.LayoutParams cardLayoutParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        cardLayoutParams2.setMargins(30, 0, 0, 0);

        cardView2.setLayoutParams(cardLayoutParams2);
        cardView2.setRadius(20);
        cardView2.setCardElevation(4);

         if (prio.equals("Baixa")) {
            cardView2.setCardBackgroundColor(context.getResources().getColor(R.color.bege));
        }
        else if (prio.equals("Media")) {
            cardView2.setCardBackgroundColor(context.getResources().getColor(R.color.amarela));
        }
        else if (prio.equals("Alta")) {
            cardView2.setCardBackgroundColor(context.getResources().getColor(R.color.laranja));
        }
        else if (prio.equals("Urgente")) {
            cardView2.setCardBackgroundColor(context.getResources().getColor(R.color.vermelha));
        }
        else {
            cardView2.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
        }

        LinearLayout cardLinearLayout = new LinearLayout(context);
        cardLinearLayout.setOrientation(LinearLayout.VERTICAL);
        cardLinearLayout.setPadding(16, 16, 16, 16);

// =======================  componentes ================================

        TextView textTitulo = new TextView(context);
        textTitulo.setText("Tarefa: " +  (String) valoresTarefa[0]); // Número da tarefa
        textTitulo.setTextSize(14);
        textTitulo.setTextColor(context.getResources().getColor(R.color.geral));
        textTitulo.setPadding(10, 2, 10, 10);

        TextView textDescricao = new TextView(context);
        if (((String) valoresTarefa[3]).length() >= 85) {
            textDescricao.setText("Titulo: " + ((String) valoresTarefa[3]).substring(0, 85) + " ...");
        } else {
            textDescricao.setText("Titulo: " + (String) valoresTarefa[3]);
        }
        textDescricao.setTextSize(14);
        textDescricao.setTextColor(context.getResources().getColor(R.color.geral));
        textDescricao.setPadding(10, 4, 10, 10);

        TextView textStatus = new TextView(context);
        textStatus.setText("Status: " + (String) valoresTarefa[2]); // Status da tarefa
        textStatus.setTextSize(14);
        textStatus.setTextColor(context.getResources().getColor(R.color.geral));
        textStatus.setPadding(10, 6, 10, 10);

        TextView textPrioridade = new TextView(context);
        textPrioridade.setText("Prioridade: " + prio); // Prioridade da tarefa
        textPrioridade.setTextSize(14);
        textPrioridade.setTextColor(context.getResources().getColor(R.color.geral));
        textPrioridade.setPadding(10, 8, 0, 0);

         View divisor1 = new View(context);
        LinearLayout.LayoutParams divisorLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 2);
        divisor1.setLayoutParams(divisorLayoutParams);
        divisor1.setBackgroundColor(context.getResources().getColor(R.color.purple_700));

         Button btnAcessar = new Button(context);
        btnAcessar.setText("Acessar Tarefa");
        btnAcessar.setBackgroundColor(context.getResources().getColor(R.color.black));
        btnAcessar.setTextColor(context.getResources().getColor(R.color.white));

        cardLinearLayout.addView(textTitulo);
        cardLinearLayout.addView(textDescricao);
        cardLinearLayout.addView(textStatus);
        cardLinearLayout.addView(textPrioridade);
        cardLinearLayout.addView(divisor1);
        cardLinearLayout.addView(btnAcessar);

         cardView2.addView(cardLinearLayout);

         btnAcessar.setOnClickListener(e-> {

             setDadosTarefa(context , valoresTarefa  , token , idAgente );
             Intent intent = new Intent(context, TarefasP4.class );
             context.startActivity(intent);

         });





// =======================

// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------


        cardLinearLayout0.addView(cardView);
        cardLinearLayout0.addView(cardView2);
        cardView0.addView(cardLinearLayout0);
        principalLayout.addView(cardView0);



    }

    public void setAnexarImage (  String id ,Context context , ImageView fotoPerfil  ){

        Api api = new Api ();
        String url = api.setImgFoto(""+id) ;
         try{
             Glide.with( context )
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.perfil)
                    .error(R.drawable.perfil)
                    .into(fotoPerfil);
        }
        catch (Exception e){
            System.out.println("Erro ao carregar imagem: " + e.getMessage());
        }
    }

//   <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public void setDadosTarefa(@NonNull Context context, Object[] dadosChamado, String token , String idAgente ) {


        SharedPreferences sharedPreferences = context.getSharedPreferences("nTarefa", Context.MODE_PRIVATE);
        SharedPreferences.Editor tarefa = sharedPreferences.edit();
        tarefa.putString("token", token);

         if (dadosChamado[0] instanceof String) {
            tarefa.putString("id", (String) dadosChamado[0]);
        }
         else {
            tarefa.putString("id", String.valueOf(dadosChamado[0]));
        }

        tarefa.putString("id_agente" , idAgente );
        tarefa.putString ("status" , (String) dadosChamado[2]);
        tarefa.putString ("titulo" , (String) dadosChamado[3]);
        tarefa.putString ("data_criacao" , (String) dadosChamado[4]);
        tarefa.putString ("data_fim" , (String) dadosChamado[5]);
        tarefa.putString ("prioridade" , (String) dadosChamado[6]);
        tarefa.putString ("nome" , (String) dadosChamado[7]);
        tarefa.putString ("sobrenome" , (String) dadosChamado[8]);
        tarefa.putString ("descricao" , (String) dadosChamado[9]);


        tarefa.apply();
    }

    public Object[] getC1(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("nTarefa", Context.MODE_PRIVATE);

         String token = sharedPreferences.getString("token", null);
        String id = sharedPreferences.getString("id", null);
        String agente = sharedPreferences.getString("id_agente", null);
        String status = sharedPreferences.getString("status", null);
        String titulo = sharedPreferences.getString("titulo", null);
        String dataCriacao = sharedPreferences.getString("data_criacao", null);
        String dataFim = sharedPreferences.getString("data_fim", null);
        String prioridade = sharedPreferences.getString("prioridade", null);
        String nome = sharedPreferences.getString("nome", null);
        String sobrenome = sharedPreferences.getString("sobrenome", null);
        String descricao = sharedPreferences.getString("descricao", null);

         return new Object[]{token, id, agente, status, titulo, dataCriacao, dataFim, prioridade, nome, sobrenome, descricao};
    }

// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public void separadoVIew(Context context, LinearLayout principalLayout ){

         CardView cardView = new CardView(context);
        LinearLayout.LayoutParams cardLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 10);
        cardLayoutParams.setMargins(10, 200, 10, 10);
        cardView.setLayoutParams(cardLayoutParams);
        cardView.setRadius(0);
        cardView.setCardElevation(0);

        View dividir1 = new View(context);
        LinearLayout.LayoutParams dividirParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );
        dividir1.setLayoutParams(dividirParams);
        dividir1.setBackgroundColor(context.getResources().getColor(R.color.white));

        cardView.addView(dividir1);

        principalLayout.addView(cardView);


        // ==================================






    }



    public String dataFormatada ( String inicio ){

        String data = inicio.substring(0, 10) ;

         String [] dataVetor = data.split("-");

        String dia = dataVetor[2] ;
        String mes = dataVetor[1] ;
        String ano = dataVetor[0] ;




        String hora = inicio.substring(11, 19) ;
        hora = hora.replaceAll("T","").replaceAll("Z","") ;

        String tudo = dia+"/"+mes+"/"+ano ;


        return  tudo+" as "+hora ;
    }

    public boolean validarCampos (String str ){

         if( str == null || str.isEmpty()){
             System.out.println(str+"                           "+str);
            return false ;
         }

         int cont = 0 ;
         String [] vStr = str.split("");
         for(int i = 0 ; i < vStr.length ; i++){
             if(vStr[i].equals(" ")){
               cont = cont + 1 ;
             }
         }

        if(str.length() >= cont ){
            return false ;
        }

        return true ;
     }

     public String formatString (String str ){

         boolean valida = validarCampos(str) ;

        if(valida == false){
            return null ;
        }

        return str.replace("\t","  ").replace("\n"," ");

     }

}
