package com.example.tarefasagente;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.SystemBarStyle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URL;
import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutionException;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Api   {

    private String rota = "http://192.168.0.114:3000/";
//    private String rota = "http://192.168.100.245:3000/";

    public String setImgFoto (String id ){
        return this.rota + "fotoPerfil?caminho_foto="+id;
    }

    private String postNormal(String body , String rota ) {
         try {
            URL url = new URL(rota);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);

             try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = body.getBytes();
                os.write(input, 0, input.length);
            } catch (Exception err) {
                 return  ""+1;
            }

             if (conexao.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder res = new StringBuilder();
                String str;

                while ((str = in.readLine()) != null) {
                    res.append(str);
                }

                in.close();
                return res.toString();
            } else {
                return "Status - " + conexao.getResponseCode() + " " + conexao.getResponseMessage();
            }
        } catch (IOException error) {
            error.printStackTrace();
            return "Erro " + error;
        }
    }

    @NonNull
    private String getPadraoAuth(String novaRota, String token) {
        HttpURLConnection conexao = null;
        BufferedReader in = null;

        try {
            URL url = new URL(novaRota);
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setRequestProperty("x-access-token", token);
            conexao.setDoOutput(false);

            if (conexao.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return response.toString();
            } else {
                return conexao.getResponseCode() + " " + conexao.getResponseMessage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "-> Erro: " + e.getMessage()+"     "+e;
        }
        finally {
            try {
                if (in != null) {

                    in.close();
                    conexao.disconnect();


                }
            } catch (IOException e) {
                conexao.disconnect();
                e.printStackTrace();
            }
            if (conexao != null) {

                conexao.disconnect();
            }
        }
    }

    private String postAuth(String token , String novaRota , String body ){

        try{
            URL url = new URL (novaRota);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept","application/json");
            conexao.setRequestProperty("x-access-token", token );

            conexao.setDoOutput(true);

            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = body.getBytes();
                os.write(input, 0, input.length);
            }

            if(conexao.getResponseCode() == 200 ) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                StringBuilder res = new StringBuilder();
                String str;
                while ((str = in.readLine()) != null) {
                    res.append(str);
                }

                in.close();
                return res.toString();
            }
            else {
                return "Erro "+conexao.getResponseCode()+" - "+ conexao.getResponseMessage();
            }

        }catch(IOException error){
            error.printStackTrace();
            return "Erro "+error ;
        }

    }



//    ==============================================================================

    public Object[] autLogin(String user, String pass) {
    String body = "{\"nome_usuario\": \"" + user + "\", \"senha\": \"" + pass + "\"}";
    String rota = this.rota+"authAgente" ;
    String retApi = postNormal( body , rota );

    try {
        JSONObject jsonObj = new JSONObject(retApi);


         Object[] ret = new Object[3];

        ret[0] = jsonObj.optString("auth");
        ret[1] = jsonObj.optInt("user_id");
        ret[2] = jsonObj.optBoolean("ativo");

        return ret;

    } catch (Exception err) {
         String erro = "Erro, tente novamente mais tarde " + err ;
        return new Object[] {
                erro,
                -1,
                false
            };


    }
 }

    public Object [][] getTarefas( String token , int id , String status , String prioridade ){


            String novaROta = this.rota+"filtroTarefasAgente?prioridade="+prioridade+"&id_agente="+id+"&status="+status+"&id="+id ;

         String retApi = getPadraoAuth( novaROta ,  token);

            Object [][] retorno  = {{}};
        try{

            JSONObject jsonObject = new JSONObject(retApi);
            Object message =  jsonObject.get("message");

            if(message instanceof JSONObject){
                JSONObject dados = (JSONObject) message ;

                retorno = new Object[1][10];

                retorno[0][0] = dados.optString("taref_id","");
                retorno[0][1] = String.valueOf(dados.optInt("id_user_criado_tarefa_fk"));
                retorno[0][2] = dados.optString("status","");
                retorno[0][3] = dados.optString("titulo","");
                retorno[0][4] = dados.optString("data_tarefa_criada","");
                retorno[0][5] = dados.optString("tempo_estimado_fim_tarefa","");
                retorno[0][6] = dados.optString("prioridade","");
                retorno[0][7] = dados.optString("nome","");
                retorno[0][8] = dados.optString("sobrenome","");
                retorno[0][9] = dados.optString("descricao","");

             }
            else if(message instanceof JSONArray){

                JSONArray msgArry = (JSONArray) message ;
                retorno = new Object[msgArry.length()][10];
                for (int i = 0; i < msgArry.length(); i++) {

                    JSONObject dados = msgArry.getJSONObject(i);
                    retorno[i][0] = dados.optString("taref_id","");
                    retorno[i][1] = String.valueOf(dados.optInt("id_user_criado_tarefa_fk"));
                    retorno[i][2] = dados.optString("status","");
                    retorno[i][3] = dados.optString("titulo","");
                    retorno[i][4] = dados.optString("data_tarefa_criada","");
                    retorno[i][5] = dados.optString("tempo_estimado_fim_tarefa","");
                    retorno[i][6] = dados.optString("prioridade","");
                    retorno[i][7] = dados.optString("nome","");
                    retorno[i][8] = dados.optString("sobrenome","");
                    retorno[i][9] = dados.optString("descricao","");

                }



            }
            else{
                 return null ;
            }


            return retorno ;

        }
        catch (Exception err ){
            return null ;
        }
    }

    public Object[] getTarefasUnica(String token, String id, String idTarefa) {
        String novaRota = this.rota + "tarefasUnica?id=" + id + "&tarefa=" + idTarefa;

        String retApi = getPadraoAuth ( novaRota, token);

        System.out.println("\n\n\n- tarefas id 2  -------------))" + retApi + "\n\n\n");

        try {

            JSONObject jsonObject = new JSONObject(retApi);
            Object message =  jsonObject.get("message");

            JSONObject dados = (JSONObject) message;

            Object[] retorno = new Object[10];

            retorno[0] = dados.optString("taref_id", "");
            retorno[1] = String.valueOf(dados.optInt("id_user_criado_tarefa_fk", 0));
            retorno[2] = dados.optString("status", "");
            retorno[3] = dados.optString("titulo", "");
            retorno[4] = dados.optString("data_tarefa_criada", "");
            retorno[5] = dados.optString("tempo_estimado_fim_tarefa", "");
            retorno[6] = dados.optString("prioridade", "");
            retorno[7] = dados.optString("nome", "");
            retorno[8] = dados.optString("sobrenome", "");
            retorno[9] = dados.optString("descricao", "");


            return retorno;

        } catch (JSONException e) {

            System.out.println("\n\n\nErro ao processar JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public  String getNovoStatus ( int tarefa  , String token , String status , String idUsuarioLogado ){
        String novaRota = this.rota + "salvarFinalTarefa?id=" + idUsuarioLogado ;
        String body = "{" +
                "\"status\": \"" + status + "\", " +
                "\"id_tarefa\": " + tarefa +
                "}";
        String str  = postAuth (token , novaRota , body) ;
        return  str;

    }

    public String [][] getComentarios (String id_tarefa , String idUsuarioLogado , String token ){

        String novaRota = this.rota+"dadosComentariosTarefas?id="+idUsuarioLogado+"&id_tarefa="+id_tarefa;

        String retApi = getPadraoAuth(novaRota , token );

        if(retApi.equals("{\"message\":\" Tarefa sem comentario\"}")){

            return new String [][] {{"",""," Tarefa sem comentarios no momento ",""}} ;
        }

        try{

            JSONObject jResponse = new JSONObject(retApi);
            Object message = jResponse.get("message");

            String[][] matrizRetorno;

            if (message instanceof JSONObject) {
                System.out.println(">>> chamo matriz simles");
                JSONObject userObj = (JSONObject) message;

                matrizRetorno = new String[1][5];
                matrizRetorno[0][0] = userObj.optString("nome", "");
                matrizRetorno[0][1] = userObj.optString("sobrenome", "");
                matrizRetorno[0][2] = userObj.optString("comentario", "Sem comentarios");
                matrizRetorno[0][3] = userObj.optString("data_comentario", "");
                matrizRetorno[0][4] = userObj.optString("id_user_comentado", "0");

                return matrizRetorno ;

            }

            if (message instanceof JSONArray) {
                System.out.println(">>> chamo matriz arry ");

                JSONArray messageArray = (JSONArray) message;
                matrizRetorno = new String[messageArray.length()][5];
                System.out.println("++++++++++++++++++>>>> "+messageArray.length() );

                for (int i = 0; i < messageArray.length(); i++) {

                    JSONObject userObj = messageArray.getJSONObject(i);
                    matrizRetorno[i][0] = userObj.optString("nome", "");
                    matrizRetorno[i][1] = userObj.optString("sobrenome", "");
                    matrizRetorno[i][2] = userObj.optString("comentario", "");
                    matrizRetorno[i][3] = userObj.optString("data_comentario", "");
                    matrizRetorno[i][4] = userObj.optString("id_user_comentado", "0");



                }

                return matrizRetorno ;

            }


        }catch(Exception err){

            return new String [][] {{"","","Sem comentarios (Erro catch) ",""}} ;

        }

        return new String [][] {{"","","Tarefa sem comentarios ",""}} ;
    }

    public String postAddComentarios(String [] vetorDados , String idUsuarioLogado , String token ){

        String novaRota = this.rota + "addComentarios?id=" + idUsuarioLogado ;
        String body = "{" +
                "\"id_tarefa\": " + vetorDados[0] + ", " +
                "\"id_user_comentado\": " + vetorDados[1] + ", " +
                "\"comentario\": \"" + vetorDados[2] + "\", " +
                "\"status\": \"" + vetorDados[3] + "\", " +
                "\"prioridade\": \"" + vetorDados[4] + "\"" +
                "}";

        String retApi = postAuth(token , novaRota, body);

        return retApi;


    }

    public Object [] getIndicadores (String token , String idUsuarioLogado , String id_agente ){

        String novaRota = this.rota+"indicadiresTaredas?id="+idUsuarioLogado+"&id_agente="+id_agente ;
        String retApi = getPadraoAuth( novaRota ,token  );


        try{
            JSONObject jsonObj = new JSONObject(retApi);
            JSONArray messageArray = jsonObj.getJSONArray("message");

            Object [] totals = new Object [messageArray.length()];

            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject item = messageArray.getJSONObject(i);
                totals[i] = Integer.parseInt(item.getString("total"));
            }




            return totals  ;
        }catch (Exception err){
            return null ;
        }

    }

     public String  mudarSenha ( String senha, String token , String idUsuarioLogado   ){

        String rota = this.rota + "trocarSenha?id="+idUsuarioLogado;
        String body = "{\"user_id\": \"" + idUsuarioLogado + "\", \"senha\": \"" + senha + "\"}";
        String repostaAPI  = postAuth (token , rota , body) ;
        return repostaAPI ;

    }



//-------------------------------------------------------------------------------------------

    private String postGeralComArquivo(String novaRota, File arquivo, String nomeFoto , String token ) {
        String boundary = "----WebKitFormBoundary" + System.currentTimeMillis();
        String linhaSeparadora = "--" + boundary;
        String fimLinhaSeparadora = "--" + boundary + "--";

        try {
            URL url = new URL(novaRota);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            conexao.setRequestProperty("x-access-token", token);
            conexao.setDoOutput(true);

            try (OutputStream os = conexao.getOutputStream();
                 PrintWriter writer = new PrintWriter(os, true)) {

                writer.append(linhaSeparadora).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"nome_foto\"\r\n\r\n");
                writer.append(nomeFoto).append("\r\n");
                writer.flush();

                writer.append(linhaSeparadora).append("\r\n");
                writer.append("Content-Disposition: form-data; name=\"imagem\"; filename=\"" + arquivo.getName() + "\"\r\n");


                String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                        MimeTypeMap.getFileExtensionFromUrl(arquivo.getName())
                ); // <<<<<<<<<<<<<<<<<<<<
                writer.append("Content-Type: " + mimeType + "\r\n\r\n"); // ,<<<<<<<<<<<<<<<<<

                writer.flush();

                try (FileInputStream fis = new FileInputStream(arquivo)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }
                os.flush();
                writer.append("\r\n");

                writer.append(fimLinhaSeparadora).append("\r\n");
                writer.flush();
            }

            if (conexao.getResponseCode() == 200) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } else {
                return "Erro: " + conexao.getResponseCode() + " - " + conexao.getResponseMessage();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return "Erro: " + e.getMessage();
        }

    }

    public String editarEnviarArquivo(String idUsuarioLogado , String caminhoArquivo , String token ) {

    String novaRota = this.rota + "uploadFotos?id="+idUsuarioLogado;
    File arquivo = new File(caminhoArquivo);

    if (!arquivo.exists()) {
        return "Arquivo não encontrado!";
    }

    String nomeFoto = String.valueOf(idUsuarioLogado);
    String retorno = postGeralComArquivo(novaRota, arquivo, nomeFoto , token );
    return retorno;
}

    public String nomeUser (String id , String token ){

        String novaRota = this.rota + "dadosAgenteLogado?id="+id+"&id_agente="+id ;

        try{

            JSONObject jsonObject = new JSONObject(getPadraoAuth(novaRota, token ));
            Object message =  jsonObject.get("message");
            JSONObject dados = (JSONObject) message;




            return "Olá, "+dados.optString("nome", "agente ")+" "+dados.optString("sobrenome", "");
        }catch(Exception err ){
            return "ERRO >>> "+err ;

        }

    }



}


