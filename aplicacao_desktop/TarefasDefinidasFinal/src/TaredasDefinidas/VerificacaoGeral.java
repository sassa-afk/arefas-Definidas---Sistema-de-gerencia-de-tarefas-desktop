package TaredasDefinidas;


import java.time.*;
import java.util.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.*;

import java.awt.*;
public class VerificacaoGeral {

    //JOptionPane.INFORMATION_MESSAGE , JOptionPane.WARNING_MESSAGE , JOptionPane.ERROR_MESSAGE  , JOptionPane.QUESTION_MESSAGE , JOptionPane.PLAIN_MESSAGE
    //public <T>  T valida(T dados , String nomeDoCampo) {  ((String) dados).isEmpty()


    // funçoes no freme login ===============================================================

    public <T> Boolean verImput (T dados , String nomeDoCampo) {
        if(  dados == null ) {
            JOptionPane.showMessageDialog(null,"Atenção o campo "+nomeDoCampo+" deve ser preenchido ! ","Aviso", JOptionPane.ERROR_MESSAGE);
            return false ;
        }
        return true ;
    }

    public String[] getVetorAuth (String retorno){

        String st1 = retorno.replace("{","").replace("}","").replace("]","").replace("[", "").replace("\"", "").replace(","," " );
        String st2 = st1.replace(":","").replace("message", "").replace("user_id","").replace("ativo", "").replace("auth", "");
        String vetor[] = st2.split(" ");

        return  vetor ;
    }

    public int MaiorWidthPerfil (ArrayList<Integer> lista) {

        int maior = 0 ;
        int aux = lista.get(0) ;

        for(int i =0 ; i < lista.size() ;i++){
            if(aux <= lista.get(i)){
                aux = lista.get(i);
            }
        }

        return aux;
    }

    public String[] getVetorDadosID (String retorno  ){
        String st1 = retorno.replace(":","").replace("{","").replace("}","").replace("]","").replace("[", "").replace("\"", "").replace(","," " );
        String st2 = st1.replace("cargo","").replace("id","").replace("mesage", "").replace("nome", "").replace("email", "").replace("data_criacao", "").replace("telefone", "");
        String vetor[] = st2.split(" ");
        return vetor ;
    }

    public String getCaracteriosManipulados (String retorno ){ // exclusivo função para limpar caractericos de retornos de json
        return retorno.replace("{","").replace("}","").replace("]","").replace("[", "").replace("\"", "").replace(","," " ).replace(":","").replace("'", "");
    }

    public String strFormatadaPesqAgente  (String retorno ){ // exclusivo função pesquisaAgente da classe api

        String retorno2  =  getCaracteriosManipulados(retorno);
        String retorno3 = retorno2.replace("_usuario","");
        String retorno4  = retorno3.replace("id","").replace("ativo",""). replace("message","")
                .replace("id_usuario"," ").replace("nome","").replace("email", "").
                replace("data_criacao", "").replace("telefone", "").replace("cargo", "");

        return retorno ;
    }

    public boolean validaCarcatericosVaziosBrancos (String str ){
        if(str == null || str.isEmpty()){
            return false;
        }
        String  va = str.replace(" ","");

        if(va.length() <= 0 ){
            return false ;
        }

        return true ;
    }

    public boolean validaCarcatericosVaziosBrancosVetor(String [] vetor ){

        for (int i = 0 ; i < vetor.length ; i++){
            if(vetor[i] == null || vetor[i].isEmpty()){
                return false;
            }
        }
        return true  ;
    }

    public boolean validaHora  (String data){

        data = data.replace(":"," ").replace("-"," ");
        System.out.println(data);




        return false ;
    }


    // ----------------------------------------------------------------
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

}



