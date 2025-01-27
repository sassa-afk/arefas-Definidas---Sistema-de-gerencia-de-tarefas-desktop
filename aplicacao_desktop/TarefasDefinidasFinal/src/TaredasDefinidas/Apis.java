package TaredasDefinidas;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.*;
import java.io.*;


public class Apis extends Main {
     private static String rota = "http://192.168.0.114:3000/" ;

 // ------------------- Uso Geral em todas clases e painel
//=============================================================================


    private String authValidado( String user, String senha) {

        try {
            URL url = new URL(this.rota+"auth");
            HttpURLConnection conexao = null;
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept", "application/json");
            conexao.setDoOutput(true);

            String jsonInputString = "{\"nome_usuario\": \"" + user + "\", \"senha\": \"" + senha + "\"}";

            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = jsonInputString.getBytes();
                os.write(input, 0, input.length);
            }catch(Exception err ){
                System.out.println("erro ::::> "+err);
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
                return "Erro: " + conexao.getResponseCode()+" "+ conexao.getResponseMessage();
            }
        } catch (IOException error) {
            error.printStackTrace();
            return "Erro "+error ;
        }
    }

    private String chamadaApiGET (String novaRota ) {

        try {

            URL url = new URL ( novaRota ) ;
            HttpURLConnection conexao =  (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty( "Content-Type","application/json");
            conexao.setRequestProperty("Accept","application/json");
            conexao.setRequestProperty("x-access-token",token);
            conexao.setDoOutput(true);

            if(conexao.getResponseCode() == 200 ||  conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString() ;

            }


        }catch(IOException error) {
            System.out.println("erro catch "+error);
            return "erro catch "+error;
        }

        return null ;
    }

    private String postGeral (String novaRota , String parametros  ) {

        try {

            URL url = new URL(novaRota);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setRequestProperty("Content-Type", "application/json");
            conexao.setRequestProperty("Accept","application/json");
            conexao.setRequestProperty("x-access-token", token );

            conexao.setDoOutput(true);

            try (OutputStream os = conexao.getOutputStream()) {
                byte[] input = parametros.getBytes();
                os.write(input,0, input.length);
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
            }else {
                return "Erro : > "+conexao.getResponseCode()+" - "+ conexao.getResponseMessage();
            }

        }catch(IOException error){
            error.printStackTrace();
            return "Erro >> "+error ;
        }

    }

    private String postGeralComArquivo(String novaRota, File arquivo, String nomeFoto) {
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
                writer.append("Content-Type: " + java.nio.file.Files.probeContentType(arquivo.toPath()) + "\r\n\r\n");
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

    public String getCaminhoFoto (int id){
        return  this.rota+"fotoPerfil?caminho_foto="+id;
    }



// ------------------- Controle de Acesso / Login
//=============================================================================


    public  String [] authAcesso (String user, String senha){

        String retornoApi = authValidado(user , senha ) ;
         String [] d = retornoApi.split(" ");

        if(d[0].equals("Erro")){
            System.out.println("erro de conexao") ;
            return new String[] { "500","500"};
        }
        if(d[0].equals("Erro:")){
            System.out.println("erro de auth ") ;
            String vetorDados ;
            return new String[] {"401","401"} ;
        }
        else{

            JSONObject obj = new JSONObject( retornoApi.toString());
            String vetorDados [] = {
                    obj.optString("ativo"),
                    obj.optString("auth"),
                    obj.optString("user_id")
            };
            return vetorDados ;

        }

     }


//=============================================================================
// ------------------- Painel perfil

    public String[] dadosUserLogado(int idUser) {
        String rota = this.rota + "dadosUser?id=" + idUser;
        String retAPI = chamadaApiGET(rota);
        try {
            JSONObject jsonResponse = new JSONObject(retAPI);
            JSONArray messageArray = jsonResponse.optJSONArray("mesage");
            JSONObject userObj = messageArray.getJSONObject(0);

            String[] vtAPI = {
                    String.valueOf(userObj.optInt("id", 0)),
                    userObj.optString("nome", "Nome não encontrado"),
                    userObj.optString("email", "Email não encontrado"),
                    userObj.optString("data_criacao", "Data de criação não encontrada"),
                    userObj.optString("telefone", "Telefone não encontrado"),
                    userObj.optString("cargo", "Cargo não encontrado"),
                    userObj.optString("sobrenome", "Sobrenome não encontrado")
            };


            return vtAPI;
        }catch (Exception err ){
            System.out.println(">> "+err);
            return null ;
        }
    }

    public String[]  mudarSenha ( String senha, int idTroca  ){
        if(senha == null || senha.isEmpty()) {
            return new String[] {"Atenção , campos de usuarios ou senha em branco, ou o processo foi cancelado , tente novamente "} ;
        }
        String rota = this.rota + "trocarSenha?id="+idUsuarioLogado;
        String body = "{\"user_id\": \"" + idTroca + "\", \"senha\": \"" + senha + "\"}";
        String repostaAPI  = postGeral (rota , body) ;
         return new String[] { repostaAPI };

     }

    public String editarEnviarArquivo(int id, String caminhoArquivo) {

        String novaRota = this.rota + "uploadFotos?id="+idUsuarioLogado;
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            return "Arquivo não encontrado!";
        }


        String nomeFoto = String.valueOf(id);
        String retorno = postGeralComArquivo(novaRota, arquivo, nomeFoto);
         return retorno;
    }

    public String fileImgPerfis(int id_user ){
        String rota = this.rota + "fotoPerfil?id="+idUsuarioLogado+"&caminho_foto="+id_user ;
        String retAPI = chamadaApiGET(rota);

        return retAPI ;
    }


//===============================  Painel Agente   ==============================================


    public String addAcessos(String[] vetorDadosApi) {
        String novaRota = this.rota + "insertAcesso?id=" + idUsuarioLogado;
        boolean status_validado = false ;

        if(vetorDadosApi[7].equals("Ativo")) {
            status_validado = true ;
        }
        else {
            status_validado = false ;
        }

        String body = "{" +
                "\"nome\": \"" + vetorDadosApi[0] + "\", " +
                "\"email\": \"" + vetorDadosApi[2] + "\", " +
                "\"telefone\": \"" + vetorDadosApi[3] + "\", " +
                "\"cargo\": \"" + vetorDadosApi[4] + "\", " +
                "\"sobrenome\": \"" + vetorDadosApi[1] + "\", " +
                "\"usuario\": \"" + vetorDadosApi[5] + "\", " +
                "\"senha\": \"" + vetorDadosApi[6] + "\", " +
                "\"status\": " + status_validado + ", " +
                "\"tipoAcesso\": \"" + vetorDadosApi[8] + "\"" +
                "}";

         String retorno = postGeral(novaRota, body);

         return retorno;

    }

    // ---------

    public String[][] pesquisaTodosAgentes() {
        String rota2 = this.rota + "filtroGeralAgente?id=" + idUsuarioLogado;
        String retorno = chamadaApiGET(rota2);

        if (retorno != null) {
            JSONObject obj = new JSONObject(retorno);

             if (obj.has("message")) {
                JSONArray messageArray = obj.getJSONArray("message");
                String[][] ri = new String[messageArray.length()][6];

                 for (int i = 0; i < messageArray.length(); i++) {
                    JSONObject userObj = messageArray.getJSONObject(i);
                    ri[i][0] = userObj.optString("nome_usuario", "N/A") ;
                    ri[i][1] =  String.valueOf(userObj.optInt("id")) ;
                    ri[i][2] = userObj.optString("nome","N/A") ;
                    ri[i][3] = String.valueOf(userObj.optBoolean("ativo" , false)) ;
                    ri[i][4] = userObj.optString("email") ;
                    ri[i][5] = userObj.optString("data_criacao");
                }
                return ri;
            } else {
                 return new String[][] { {"Erro: chave 'message' não encontrada na resposta JSON"} };

             }
        } else {
            return new String[][] { {"Erro : reposta nula da API"} };
         }
    }

    public String [][] pesquisaNomeAgente(String nomeAgente ){
        String rota  = this.rota + "filterNomeAgente?id="+idUsuarioLogado+"&nome="+nomeAgente;

        String retorno = chamadaApiGET(rota);
        JSONObject obj = new JSONObject(retorno);
        JSONArray messageArray = obj.getJSONArray("message");
        String[][] matrizresponse = new String[messageArray.length()][6];
        for (int i = 0; i < messageArray.length(); i++) {
            JSONObject userObj = messageArray.getJSONObject(i);
            matrizresponse[i][0] = userObj.optString("nome_usuario", "N/A") ;
            matrizresponse[i][1] =  String.valueOf(userObj.optInt("id")) ;
            matrizresponse[i][2] = userObj.optString("nome","N/A") ;
            matrizresponse[i][3] = String.valueOf(userObj.optBoolean("ativo" , false)) ;
            matrizresponse[i][4] = userObj.optString("email") ;
            matrizresponse[i][5] = userObj.optString("data_criacao");
        }
        return matrizresponse  ;



    }

    public String [][] pesquisarLoginAgente (String nomeAgente ){
        String rota  = this.rota + "filterLoginAgentes?id="+idUsuarioLogado+"&nome_usuario="+nomeAgente;

        String retorno = chamadaApiGET(rota);
         JSONObject obj = new JSONObject(retorno);
        JSONArray messageArray = obj.getJSONArray("message");
        String[][] matrizresponse = new String[messageArray.length()][6];
        for (int i = 0; i < messageArray.length(); i++) {
            JSONObject userObj = messageArray.getJSONObject(i);
            matrizresponse[i][0] = userObj.optString("nome_usuario", "N/A") ;
            matrizresponse[i][1] =  String.valueOf(userObj.optInt("id")) ;
            matrizresponse[i][2] = userObj.optString("nome","N/A") ;
            matrizresponse[i][3] = String.valueOf(userObj.optBoolean("ativo" , false)) ;
            matrizresponse[i][4] = userObj.optString("email") ;
            matrizresponse[i][5] = userObj.optString("data_criacao");
        }
        return matrizresponse  ;


    }

    public String [][] pesquisaIdAgente (int id_pesquisa){

        String rota  = this.rota + "filterIdAgente?id="+idUsuarioLogado+"&user_id="+id_pesquisa;

        String retorno = chamadaApiGET(rota);
        JSONObject obj = new JSONObject(retorno);
        JSONArray messageArray = obj.getJSONArray("message");
        String[][] matrizresponse = new String[messageArray.length()][6];
        for (int i = 0; i < messageArray.length(); i++) {
            JSONObject userObj = messageArray.getJSONObject(i);
            matrizresponse[i][0] = userObj.optString("nome_usuario", "N/A") ;
            matrizresponse[i][1] =  String.valueOf(userObj.optInt("id")) ;
            matrizresponse[i][2] = userObj.optString("nome","N/A") ;
            matrizresponse[i][3] = String.valueOf(userObj.optBoolean("ativo" , false)) ;
            matrizresponse[i][4] = userObj.optString("email") ;
            matrizresponse[i][5] = userObj.optString("data_criacao");
        }
        return matrizresponse  ;
     }

    public String[] dadosUserExistente(int idParaVerificar){

     String novaRota = this.rota + "filterIdAgenteCriado?id=" + idUsuarioLogado +"&user_id="+idParaVerificar;
     String retornoApi = chamadaApiGET(novaRota);

     JSONObject jsonResponse = new JSONObject(retornoApi);
     JSONArray messageArray = jsonResponse.optJSONArray("message");
     JSONObject userObj = messageArray.getJSONObject(0);

     String[] vtAPI = {
             userObj.optString("nome_usuario"),
             String.valueOf(userObj.optInt("id")),
             userObj.optString("nome"),
             userObj.optString("ativo"),
             userObj.optString("email"),
             userObj.optString("data_criacao"),
             userObj.optString("sobrenome"),
             userObj.optString("tipo_acesso"),
             userObj.optString("cargo"),
             userObj.optString("telefone")
     };


     return vtAPI;

  }

    public String editarUserExistente(int id , String [] vetorDadosApi  ){

          String novaRota = this.rota + "updateAcesso?id=" + idUsuarioLogado +"&user_id="+id;
          String body = "{" +
          "\"coluna\": \"" + vetorDadosApi[0] + "\", " +
          "\"valor\": \"" + vetorDadosApi[1] + "\", " +
          "\"user_id\": \"" + id  + "\"" +
          "}";



          String retApi = postGeral(novaRota , body);
            return retApi ;
      }

//================================ Painel Tarefas =============================================
    //----------------  parte 1  ----------------

    public  String salvaEstadoFinalTarefa( int tarefa , String status ){
        String novaRota = this.rota + "salvarFinalTarefa?id=" + idUsuarioLogado ;
        String body = "{" +
                "\"status\": \"" + status + "\", " +
                "\"id_tarefa\": " + tarefa +
                "}";
        String str  = postGeral(novaRota , body) ;
        return  str;

    }

    public String[][] dadosAgentesTarefas(boolean ativo ){

        String novaRota = this.rota+"agentesTarefas?id="+idUsuarioLogado+"&ativo="+ativo+"&tipo_acesso=agente";
        String retApi = chamadaApiGET(novaRota);

        if(retApi.equals("{\"mesage\":[]}")){
            String[][] matrizNUla = new String [1][4] ;

                        matrizNUla[0][0] = "0";
                        matrizNUla[0][1] = "não encontrado" ;
                        matrizNUla[0][2] = "false";
                        matrizNUla[0][3] = "não encontrado";

               return matrizNUla ;

        }


        JSONObject jResonse = new JSONObject(retApi);
        JSONArray msgArry =   jResonse.optJSONArray("mesage");
        String[][] matrizRetorno = new String[msgArry.length()][4] ;



        for(int i = 0 ; i < msgArry.length(); i++){
            JSONObject userObj = msgArry.getJSONObject(i);

            matrizRetorno[i][0] = String.valueOf(userObj.optInt("id")) ;
            matrizRetorno[i][1] = userObj.optString("nome") ;
            matrizRetorno[i][2] = String.valueOf(userObj.optBoolean("ativo" , false)) ;
            matrizRetorno[i][3] = userObj.optString("sobrenome") ;

        }



        return matrizRetorno ;
    }

    public String adicionarTarefas(   String  [] parametos , int idAgente ){

         String novaRota = this.rota + "addTarefas?id=" + idUsuarioLogado ;

        //2024-12-31 03:59:00
        String tempo = parametos[3]+"-"+parametos[4]+"-"+parametos[5]+" "+parametos[6]+":"+parametos[7]+":00";
        String body = "{" +
                "\"id_user_destino\": " + idAgente + ", " +
                "\"id_user_gera_tarefa\": " +idUsuarioLogado+ ", " +
                "\"status\": \"" + parametos[1] + "\", " +
                "\"titulo\": \"" + parametos[0] + "\", " +
                "\"tempo_estimado_os\": \"" + tempo + "\", " +
                "\"prioridade\": \"" + parametos[2] + "\", " +
                "\"descricao\": \"" + parametos[8] + "\"" +
                "}";

        String retApi = postGeral (novaRota , body);

        return retApi;

    }

    public String addComentarios(String [] vetorDados){

        String novaRota = this.rota + "addComentarios?id=" + idUsuarioLogado ;
        String body = "{" +
                "\"id_tarefa\": " + vetorDados[0] + ", " +
                "\"id_user_comentado\": " + vetorDados[1] + ", " +
                "\"comentario\": \"" + vetorDados[2] + "\", " +
                "\"status\": \"" + vetorDados[3] + "\", " +
                "\"prioridade\": \"" + vetorDados[4] + "\"" +
                "}";

        String retApi = postGeral(novaRota, body);

        return retApi;


    }

    //----------------  parte 2  ---------------------


    public String[][] psqGenericaTarefas (String novaRota){

        String retApi = chamadaApiGET(novaRota);

        try {
            JSONObject jResponse = new JSONObject(retApi);
            Object message = jResponse.get("message");

            String[][] matrizRetorno;

            if (message instanceof JSONObject) {

                JSONObject userObj = (JSONObject) message;

                matrizRetorno = new String[1][5];
                matrizRetorno[0][0] = String.valueOf(userObj.optInt("taref_id", 0));
                matrizRetorno[0][1] = userObj.optString("titulo", "N/D");
                matrizRetorno[0][2] = userObj.optString("status", "N/D");
                matrizRetorno[0][3] = userObj.optString("data_tarefa_criada", "N/D");
                matrizRetorno[0][4] = userObj.optString("prioridade", "N/D");

            }

            if (message instanceof JSONArray) {

                JSONArray messageArray = (JSONArray) message;
                matrizRetorno = new String[messageArray.length()][5];

                for (int i = 0; i < messageArray.length(); i++) {

                    JSONObject userObj = messageArray.getJSONObject(i);

                    matrizRetorno[i][0] = String.valueOf(userObj.optInt("taref_id", 0));
                    matrizRetorno[i][1] = userObj.optString("titulo", "N/D");
                    matrizRetorno[i][2] = userObj.optString("status", "N/D");
                    matrizRetorno[i][3] = userObj.optString("data_tarefa_criada", "N/D");
                    matrizRetorno[i][4] = userObj.optString("prioridade", "N/D");
                }

            }

            else {
                return new String[][]{{"N/D", "N/D", "N/D", "N/D", "N/D"}};
            }

            return matrizRetorno;

        }
        catch (Exception err) {
            System.out.println("Erro ao processar a resposta da API: " + err);
            return new String[][]{{"N/D", "N/D", "N/D", "N/D", "N/D"}};
        }
    }

    public String[][]  pqsTarefaGeral (int id_agente){

        String novaRota = this.rota+"filtroTodasTarefas?id="+idUsuarioLogado+"&id_agete_tarefa="+id_agente;
        return psqGenericaTarefas (novaRota) ;

    }

    public String[][] pqsNumTarefa(int id_agente, String numeroTarefa) {

        String novaRota = this.rota + "fiterNumeroTarefas?id=" + idUsuarioLogado
                + "&id_user_destino_tarefa_fk=" + id_agente
                + "&taref_id=" + numeroTarefa;

        return psqGenericaTarefas(novaRota) ;


    }

    public String[][] pqsStatusTarefas(int id_agente, String status) {
        String novaRota = this.rota + "filterStatusTarefa?id=" + idUsuarioLogado
                + "&id_user_destino_tarefa_fk=" + id_agente
                + "&status=" + status;
        return psqGenericaTarefas(novaRota) ;

    }

    public String [][] pqsPrioridadeTarefa(int id_agente , String prioridade){


        String novaRota =    this.rota+"filterPrioridadesTarefas?id="+idUsuarioLogado
                +"&id_agente="+id_agente
                +"&prioridade="+prioridade;


        return psqGenericaTarefas(novaRota) ;

    }

    public String [][] pqsDadosTarefas (int id_tarefa )  {

        String novaRota = this.rota + "dadosTarefas?id=" + idUsuarioLogado +"&id_tarefa="+id_tarefa;
        String retApi = chamadaApiGET(novaRota);
        String[][] matrizRetorno;

        try{

            JSONObject jResponse = new JSONObject(retApi);
            Object message = jResponse.get("message");
            JSONObject userObj = (JSONObject) message;
            matrizRetorno = new String[1][9];

            matrizRetorno[0][0] = String.valueOf(userObj.optInt("taref_id", 0));
            matrizRetorno[0][1] = userObj.optString("id_user_destino_tarefa_fk", "N/D");
            matrizRetorno[0][2] = userObj.optString("id_user_criado_tarefa_fk", "N/D");
            matrizRetorno[0][3] = userObj.optString("status", "N/D");
            matrizRetorno[0][4] = userObj.optString("titulo", "N/D");
            matrizRetorno[0][5] = userObj.optString("data_tarefa_criada", "N/D");
            matrizRetorno[0][6] = userObj.optString("tempo_estimado_fim_tarefa", "N/D");
            matrizRetorno[0][7] = userObj.optString("prioridade", "N/D");
            matrizRetorno[0][8] = userObj.optString("descricao", "N/D");





        } catch (Exception e) {

            return new String[][]{{"N/D", "N/D", "N/D", "N/D", "N/D"}};

        }
        return  matrizRetorno;

    }

    public String [][] pqsDdosComentarios (int id_tarefa){

        String novaRota = this.rota+"dadosComentariosTarefas?id="+idUsuarioLogado+"&id_tarefa="+id_tarefa;

        String retApi = chamadaApiGET(novaRota);
        System.out.println("----------------------------->"+retApi);

        if(retApi.equals("{\"message\":\" Tarefa sem comentario\"}")){

            return new String [][] {{"","","Tarefa sem comentarios  ",""}} ;
        }

        try{

            JSONObject jResponse = new JSONObject(retApi);
            Object message = jResponse.get("message");

            String[][] matrizRetorno;

            if (message instanceof JSONObject) {
                System.out.println(">>> chamo matriz simles");
                JSONObject userObj = (JSONObject) message;

                matrizRetorno = new String[1][4];
                matrizRetorno[0][0] = userObj.optString("nome", "");
                matrizRetorno[0][1] = userObj.optString("sobrenome", "");
                matrizRetorno[0][2] = userObj.optString("comentario", "Sem comentarios");
                matrizRetorno[0][3] = userObj.optString("data_comentario", "");

                return matrizRetorno ;

            }

            if (message instanceof JSONArray) {
                System.out.println(">>> chamo matriz arry ");

                JSONArray messageArray = (JSONArray) message;
                matrizRetorno = new String[messageArray.length()][4];
                System.out.println("++++++++++++++++++>>>> "+messageArray.length() );

                for (int i = 0; i < messageArray.length(); i++) {

                    JSONObject userObj = messageArray.getJSONObject(i);
                    matrizRetorno[i][0] = userObj.optString("nome", "");
                    matrizRetorno[i][1] = userObj.optString("sobrenome", "");
                    matrizRetorno[i][2] = userObj.optString("comentario", "");
                    matrizRetorno[i][3] = userObj.optString("data_comentario", "");

                }

                return matrizRetorno ;

            }


        }catch(Exception err){

          return new String [][] {{"","","Sem comentarios (Erro catch) ",""}} ;

        }

         return new String [][] {{"","","Tarefa sem comentarios ",""}} ;
    }

    public Object [] psqIndicadores (int id_agente){

        String novaRota = this.rota+"indicadiresTaredas?id="+idUsuarioLogado+"&id_agente="+id_agente ;
        String retApi = chamadaApiGET(novaRota);

        JSONObject jsonObj = new JSONObject(retApi);
        JSONArray messageArray = jsonObj.getJSONArray("message");

        Object [] totals = new Object [messageArray.length()];

        for (int i = 0; i < messageArray.length(); i++) {
            JSONObject item = messageArray.getJSONObject(i);
            totals[i] = Integer.parseInt(item.getString("total"));
        }


        return totals  ;
    }




}


