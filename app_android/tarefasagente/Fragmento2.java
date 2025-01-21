package com.example.tarefasagente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;


public class Fragmento2 extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragmento2, container, false);

        ProcessoGerais processoGerais = new ProcessoGerais() ;
        Api api = new Api() ;

        Object[] dadosUser = processoGerais.getC1(getActivity()) ;

        for (int i = 0 ; i< dadosUser.length ; i++) {
            System.out.println(  i + " ------))" + dadosUser[i]);
        }


//********************************************************************************************



//********************************************************************************************

        LinearLayout linearLayoutGeral = view.findViewById(R.id.idLinearRespostas);

        LinearLayout.LayoutParams layoutParametros = createLayoutParams( 50 , 300 , 100 , 100 );
        LinearLayout.LayoutParams layoutParametrosComentarios = createLayoutParams( 50 , 50 , 50 , 50);
        LinearLayout.LayoutParams layoutParametros2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );


//********************************************************************************************

        View div1 = dividir(getContext() , layoutParametros2 );

        TextView descricao = createTextView(getContext(), "Descrição : \n\n" + dadosUser[10] , R.color.black, layoutParametros);

        View div2  = dividir(getContext() , layoutParametros2 );

        TextView t1 = createTextView(getContext(), "Comentarios : ", R.color.black, layoutParametrosComentarios);

        View div3  = dividir(getContext() , layoutParametros2 );
        TextView t2 = createTextView(getContext(), "Adicionar um novo comentario  : ", R.color.black, layoutParametrosComentarios);

        Button btn = btn(getContext(), layoutParametrosComentarios, "Enviar comentario");

        EditText comentario = new EditText(getContext()) ;
        comentario.setLayoutParams(layoutParametrosComentarios);
        comentario.setSingleLine(false);
        comentario.setHint("Digite seu comentário");


        TextView retornoComentarios = createTextView(getContext(), "", R.color.black, layoutParametrosComentarios);

//=============================================================================================
        new Thread(new Runnable() {
            @Override
            public void run() {

                Object[] dadosUser = processoGerais.getC1(getActivity()) ;

                String token = (String) dadosUser[0];
                String tarefa = (String) dadosUser[1];
                String idAgente = (String) dadosUser[2];

                Object [][] comentarios = api.getComentarios(  tarefa , idAgente , token );
                if(getContext() != null ){


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (comentarios[0][0].equals("")){
                                retornoComentarios.setText("Tarefa sem comentarios no momento ");
                            }
                            else{

                                String todosComentarios = "" ;

                                for (int i = 0 ; i < comentarios.length ; i++) {

                                    String dataFormatada = processoGerais.dataFormatada(  (String) comentarios[i][3].toString() ) ;
                                    todosComentarios = todosComentarios + "> Comentario realizado "+dataFormatada+" por "+comentarios[i][0]+" "+comentarios[i][1]+" :\n\n" +
                                            "Relato : "+comentarios[i][2] + "\n\n";

                                }

                                retornoComentarios.setText( todosComentarios );

                            }
                        }
                    });



                }


            }
        }).start();

//=============================================================================================
        btn.setOnClickListener(e ->{
//
            new Thread(new Runnable(){
                @Override
                public void run(){
                    if(getActivity() != null){
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
                                if(getContext() != null ){
//                                    getActivity().recreate();

                                    String dadosComentario = comentario.getText().toString() ;

//                                    String str = processoGerais.formatString(dadosComentario);
//                                    if(str == null ){
//                                        Toast.makeText(getContext(), "Sem mensagem, tente novamente " , Toast.LENGTH_SHORT).show();
//                                    }
//                                    else {
//                                        Toast.makeText(getContext(), ">> "+str , Toast.LENGTH_SHORT).show();
//
//                                    }

                                    Object[] dadosUser = processoGerais.getC1(getActivity()) ;

                                    String token = (String) dadosUser[0];
                                    String idAgente = (String) dadosUser[2];
                                    String [] vetorDados= {(String)dadosUser[1] , idAgente , dadosComentario , (String) dadosUser[3] , (String) dadosUser[7] } ;

                                    String  ret2 =  api.postAddComentarios( vetorDados  , idAgente , token  );

                                    if(getContext() != null ){

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), " "+ret2 , Toast.LENGTH_SHORT).show();

                                                getActivity().recreate();
                                            }
                                        });
                                    }
//                                    Toast.makeText(getContext(), ">> "+ret2 , Toast.LENGTH_SHORT).show();

                                    }

//                            }
//                        });
                    }

                }
            }).start();
        });

//********************************************************************************************


        linearLayoutGeral.addView(div1);
        linearLayoutGeral.addView(descricao);
        linearLayoutGeral.addView(div2);
        linearLayoutGeral.addView(t1);
        linearLayoutGeral.addView(retornoComentarios);
        linearLayoutGeral.addView(div3);
//        linearLayoutGeral.addView(t2);
        linearLayoutGeral.addView(comentario);

        linearLayoutGeral.addView(btn);




        return view;
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

        View dividir1 = new View(getContext());
        dividir1.setLayoutParams(layoutParams);
        dividir1.setBackgroundColor(getContext().getResources().getColor(R.color.black));

        return dividir1 ;
    }

    private Button btn (Context context, LinearLayout.LayoutParams layoutParams , String nomeBtn){

        Button btn = new Button(getContext()) ;
        btn.setLayoutParams(layoutParams);
        btn.setText(nomeBtn);

        return btn ;

    }

}