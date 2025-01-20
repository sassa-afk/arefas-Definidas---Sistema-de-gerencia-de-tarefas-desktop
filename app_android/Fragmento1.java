package com.example.tarefasagente;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Fragmento1 extends Fragment {

    private String valorStatus ;

    public void onReusme(){
        super.onResume();
    }

    ProcessoGerais processoGerais = new ProcessoGerais();
    Api api = new Api();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragmento1, container, false);

        Object[] dadosUser = processoGerais.getC1(getActivity()) ;

//
//        for (int i = 0 ; i< dadosUser.length ; i++){
//            System.out.println("janela posicao "+i+" ------))" + dadosUser[i] );
//
//        }

        String token = (String) dadosUser[0];
        String tarefa = (String) dadosUser[1];
        String idAgente = (String) dadosUser[2];

        LinearLayout linearLayoutGeral = view.findViewById(R.id.idLinearDescricao);

//*************************************************************************************************

        LinearLayout.LayoutParams layoutParametros = createLayoutParams( 200 , 400 , 100 , 100 );

        TextView numeroTarefa = createTextView(getContext(), "Tarefa "+dadosUser[1], R.color.black, layoutParametros);
        numeroTarefa.setTextSize(25);

        LinearLayout.LayoutParams parametro2 = createLayoutParams( 50 , 20 , 100 , 50 );
        TextView abriuTarefa = createTextView(getContext(), "Aberto  por "+dadosUser[8]+" "+dadosUser[9], R.color.black, parametro2);

        TextView  dataAbertura = createTextView(getContext(), "Chamado aberto :\n"+dadosUser[5], R.color.black, parametro2);

        TextView  previsaoFim = createTextView(getContext(), "Previsão de conclusão :\n"+dadosUser[6], R.color.black, parametro2);

        TextView  titulo = createTextView(getContext(), "Titulo da tarefa : \n"+dadosUser[4], R.color.black, parametro2);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );


        View dividir1 = dividir(getContext() , layoutParams );

        View dividir2 = dividir(getContext() , layoutParams );

        View dividir3 = dividir(getContext() , layoutParams );

        TextView prioridade = null ;
        String prio = (String) dadosUser[7] ;

        if (prio.equals("Baixa")) {
            prioridade = createTextView(getContext(), "Prioridade "+dadosUser[7], R.color.white, parametro2);

        }
        else if (prio.equals("Media")) {
            prioridade = createTextView(getContext(), "Prioridade "+dadosUser[7], R.color.amarela, parametro2);

        }
        else if (prio.equals("Alta")) {
            prioridade = createTextView(getContext(), "Prioridade "+dadosUser[7], R.color.laranja, parametro2);

        }
        else if (prio.equals("Urgente")) {
            prioridade = createTextView(getContext(), "Prioridade "+dadosUser[7], R.color.vermelha, parametro2);

        }
        else {
            prioridade = createTextView(getContext(), "Prioridade "+dadosUser[7], R.color.black, parametro2);

        }

        Button salvar = new Button(getContext()) ;
        salvar.setLayoutParams(parametro2);
        salvar.setText("Salvar");

//*************************************************************************************************
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                     Object[] retApi = api.getTarefasUnica(token, idAgente, tarefa);

                     if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 TextView descricao = createTextView(getContext(), "Descrição : \n\n" + retApi[9], R.color.black, parametro2);
                                linearLayoutGeral.addView(descricao);

                                linearLayoutGeral.addView(dividir3);

                                String[] tiposStatus = {"Aberto", "Validando", "Atendendo"};
                                Spinner spinnerStatus = createSpinner(getContext(), tiposStatus, parametro2);

                                if(retApi[2].equals(tiposStatus[0])){
                                     spinnerStatus.setSelection(0);
                                }
                                if(retApi[2].equals(tiposStatus[1])){
                                    spinnerStatus.setSelection(1);
                                }
                                if(retApi[2].equals(tiposStatus[2])){
                                    spinnerStatus.setSelection(2);
                                }

                                linearLayoutGeral.addView(spinnerStatus);

                                linearLayoutGeral.addView(salvar);

                                spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                        String statusAtual = parentView.getSelectedItem().toString();
                                        valorStatus = statusAtual;
                                     }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                        Toast.makeText(getContext(), "Nada selecionado", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 Toast.makeText(getContext(), "Erro ao carregar dados: " + e.getMessage()+". Tente mais tarde ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
//*************************************************************************************************


        linearLayoutGeral.addView(numeroTarefa);
        linearLayoutGeral.addView(dividir1);
        linearLayoutGeral.addView(abriuTarefa);
        linearLayoutGeral.addView(dataAbertura);
        linearLayoutGeral.addView(previsaoFim);
        linearLayoutGeral.addView(titulo);
        linearLayoutGeral.addView(prioridade);
        linearLayoutGeral.addView(dividir2);
//*************************************************************************************************

        salvar.setOnClickListener( e -> {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getContext(), "Tarefa salva com novo status "+valorStatus , Toast.LENGTH_SHORT).show();

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    String retornoNovaStatus = api.getNovoStatus(  Integer.parseInt(tarefa) , token , valorStatus , idAgente ) ;

                                } catch (Exception e) {
                                     if (getActivity() != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), "Erro ao carregar dados: " + e.getMessage()+". Tente mais tarde ", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }
                        }).start();
                        getActivity().recreate();
//                        Intent intent = new Intent(getActivity(), TarefasP4.class);
//                        startActivity(intent);

                    }
                });
            }

        });

//*************************************************************************************************


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

}
