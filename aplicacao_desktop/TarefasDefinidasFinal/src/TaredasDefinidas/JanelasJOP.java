package TaredasDefinidas;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import java.awt.* ;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.Timer;

 import javax.swing.text.*;


public class JanelasJOP extends Componentes{

    Apis retornosApi = new Apis();
    VerificacaoGeral varificaoGeral = new VerificacaoGeral();


    //    String imgFotos = "http://192.168.0.114:3000/fotoPerfil?caminho_foto=" + id + "&timestamp=" +  java.util.UUID.randomUUID();;


//    --------------------------- usual para todos os paineis  ------------------------




    public String[] trocaSenha ( ) {

        JPanel painel = setPanel(800 , 250  , null );

        JLabel l0 = setLabel("Trocar senha ", 35 , Color.black );
        l0.setBounds( 250, 0 , 650, 150 );

        JLabel l1 = setLabel("Insira a nova senha : ", 15 , Color.black );
        l1.setBounds( 0 , 140 , 300 , 50);

        JPasswordField  pass1 = setJPass (40) ;
        pass1.setBounds( 180 , 150 , 590 , 30 );

        JLabel l2 = setLabel("Repita a nova senha :", 15 , Color.black  );
        l2.setBounds(0,200 , 200 , 50);

        JPasswordField  pass2 = setJPass (40) ;
        pass2.setBounds( 180 , 205 , 590 , 30 );

        painel.setLayout(null);
        painel.add(l0);
        painel.add(l1);
        painel.add(pass1);
        painel.add(l2);
        painel.add(pass2);


        while(true){
            int result = JOptionPane.showConfirmDialog(null , painel , "Editar senha"  ,  JOptionPane.OK_CANCEL_OPTION );
            if(result == 0 ) {
                char[] ip1_char =  pass1.getPassword() ;
                String ip1_str = new String (ip1_char);

                char[] ip2_char =  pass2.getPassword() ;
                String ip2_str = new String (ip2_char);


                if(ip1_str.equals(ip2_str) ){

                    String [] vet = new String[2];
                    vet[0] = ip1_str;
                    vet[1] = ip2_str;
                    return vet ;

                }else if(ip1_str == null || ip1_str == ""  || ip1_str == " "|| ip2_str == null || ip2_str == ""  || ip2_str == " "){
                    JOptionPane.showMessageDialog(null , "Atenção, todos os campos devem ser preenchidos !", "",JOptionPane.WARNING_MESSAGE);

                }else if (  ip1_str.isEmpty() ||  ip2_str.isEmpty()){

                    JOptionPane.showMessageDialog(null , "- Atenção, todos os campos devem ser preenchidos !", "",JOptionPane.WARNING_MESSAGE);

                }else{

                    JOptionPane.showMessageDialog(null , "Atenção, as senhas dos campos não são iguais !", "",JOptionPane.WARNING_MESSAGE);

                }

            }else{
                JOptionPane.showMessageDialog(null , "Operação cancelada ", "",JOptionPane.WARNING_MESSAGE);

                break ;
            }

        }





        String[] vetor = new String[1];

        return vetor ;
    }

    public String[] dadosAgenteExistente (int id){


        String [] dadosUser  = retornosApi.dadosUserExistente(id);

        JPanel painel = setPanel(1550 , 600 , null );
        painel.setLayout(null);

        String fotoImg = retornosApi.getCaminhoFoto(id);
        System.out.println(fotoImg) ;
        JLabel fotoUser = setImageDimensaoCircularURL(fotoImg+"&timestamp=" +  java.util.UUID.randomUUID() ,  350, 350 ) ;

        fotoUser.setBounds(0 , 80 , 350 , 350);

        JLabel l1 = setLabel("Nome : "+dadosUser[2] +" "+dadosUser[6] , 15, Color.black);
        l1.setBounds(400 , 50 , 500 , 100 );

        JLabel l2 = setLabel("Acesso criado : "+varificaoGeral.dataFormatada(dadosUser[5])  , 15 , Color.black );
        l2.setBounds(400 , 100 , 500  , 100 );

        JLabel l3 = setLabel("ID de acesso : "+id , 15 , Color.black);
        l3.setBounds(1200 , 50 , 200 , 100 ) ;

        JLabel l4  = setLabel("Usuário : "+dadosUser[0]  , 15 , Color.black);
        l4.setBounds(1200 , 100 , 200 , 100 );

        JLabel l5  = setLabel("Tipo de acesso : "+dadosUser[7]  , 15 , Color.black);
        l5.setBounds(400 , 150 , 200 , 100 );

        JLabel l6  = setLabel("Ativo : "+dadosUser[3]  , 15 , Color.black);
        l6.setBounds(1200 , 150 , 200 , 100 );

        JLabel l7  = setLabel("Cargo : "+dadosUser[8]  , 15 , Color.black);
        l7.setBounds(1200 , 200 , 250 , 100 );

        JLabel l8  = setLabel("Telefone : "+dadosUser[9] +" |  E-mail : "+dadosUser[4] , 15 , Color.black);
        l8.setBounds(400 , 200 , 500 , 100 );


        JButton btnSenha = setBoutton( "Trocar senha deste acesso", null );
        btnSenha.setBounds(400 , 300  , 1100, 50 );

        JButton btnEditAcesso = setBoutton( "Editar dados deste acesso", null );
        btnEditAcesso.setBounds(400 , 380  , 1100, 50 );

        painel.removeAll();

        painel.add(fotoUser);
        painel.add(l1);
        painel.add(l2);
        painel.add(l3);
        painel.add(l4);
        painel.add(l5);
        painel.add(l6);
        painel.add(l7);
        painel.add(l8);
        painel.add(btnSenha);
        painel.add(btnEditAcesso);

        painel.repaint();
        painel.revalidate();


        btnSenha.addActionListener(e->{
            String [] dadosNovaSenha = trocaSenha();
            String senha = dadosNovaSenha[0] ;

            if(dadosNovaSenha[0] == dadosNovaSenha[1] || dadosNovaSenha[0] != null || dadosNovaSenha[1] != null   ){
                String [] ver = retornosApi.mudarSenha(dadosNovaSenha[0] , id);
                JOptionPane.showMessageDialog(null , ver[0], "",JOptionPane.WARNING_MESSAGE);

                painel.removeAll();
                painel.add(fotoUser);
                painel.add(l1);
                painel.add(l2);
                painel.add(l3);
                painel.add(l4);
                painel.add(l5);
                painel.add(l6);
                painel.add(l7);
                painel.add(l8);
                painel.add(btnSenha);
                painel.add(btnEditAcesso);
                painel.repaint();
                painel.revalidate();
            }
        });

        btnEditAcesso.addActionListener(e->{
            String[] retornoBotao = editarDadosAcesso(id);
            System.out.print(">> <<>><<"+retornoBotao[0] +"  "+retornoBotao[1]);

            boolean c1OP = varificaoGeral.validaCarcatericosVaziosBrancos(retornoBotao[0]);
            boolean c2OP = varificaoGeral. validaCarcatericosVaziosBrancos(retornoBotao[1]);

            if(c1OP == true &&  c2OP == true) {
                String retApi = retornosApi.editarUserExistente(id , retornoBotao );
                JOptionPane.showMessageDialog(null," "+retApi,"",JOptionPane.INFORMATION_MESSAGE);
                painel.repaint();
                painel.revalidate();

            }else{
                JOptionPane.showMessageDialog(null , "Atenção , há campos invalidos ou em brancos para atulizar os dados do acesso. Tente novamente !","",JOptionPane.ERROR_MESSAGE);
            }
        });


        Object[] options = { "Fechar painel"};
        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,
                "Registro do agente "+id  ,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
        return null ;
    }

    public String[] editarDadosAcesso(int id) {
        JPanel painel = setPanel(800, 150, null);

        JLabel l1 = setLabel("Selecione um campo para que seja editado:", 15, Color.BLACK);
        l1.setBounds(0, 40, 400, 50);

        String[] opcoesEdicoes = {"nome", "sobrenome", "email", "telefone", "cargo", "status"};
        JComboBox<String> edicoes = setSeletorString(opcoesEdicoes);
        edicoes.setBounds(370, 48, 430, 30);

        String[] opcaoStatus = {"ativo", "desativado"};
        JComboBox<String> status = setSeletorString(opcaoStatus);
        status.setBounds(0, 100, 800, 30);

        JTextField valorEditavel = setJTextFIld(20);
        valorEditavel.setBounds(0, 100, 800, 30);

        edicoes.addActionListener(e -> {
            String valorSelecionado = (String) edicoes.getSelectedItem();
            painel.removeAll();
            painel.add(l1);
            painel.add(edicoes);

            if (valorSelecionado.equals("status")) {
                painel.add(status);
            } else {
                painel.add(valorEditavel);
            }

            painel.repaint();
            painel.revalidate();
        });

        painel.add(l1);
        painel.add(edicoes);

        int result = JOptionPane.showConfirmDialog(null, painel, "Editar dados do usuário", JOptionPane.OK_CANCEL_OPTION);

        String valorSelecionado = (String) edicoes.getSelectedItem();
        String valorCampo;
        if ("status".equals(valorSelecionado)) {
            valorCampo = (String) status.getSelectedItem();
        } else {
            valorCampo = valorEditavel.getText();
        }

         return result == JOptionPane.OK_OPTION ? new String[]{valorSelecionado, valorCampo} : new String[]{};
    }


//---------------------------- Exclusivo para o painel agente

    public String[] addAgente() {
        String retornoAPI , retorno_vt_api [] = new String[9];
        //______________________________________________

        JPanel painel = setPanel(1550, 900, null);
        painel.setLayout(null);

        JLabel l1 = setLabel("Nome", 15, Color.black);
        l1.setBounds(0, 20, 200, 100);

        JLabel l01 = setLabel("Sobrenome", 15, Color.black);
        l01.setBounds(710, 20, 200, 100);

        JLabel l2 = setLabel("E-mail", 15, Color.black);
        l2.setBounds(0, 120, 200, 100);

        JLabel l3 = setLabel("Telefone", 15, Color.black);
        l3.setBounds(0, 220, 200, 100);

        JLabel l4 = setLabel("Cargo", 15, Color.black);
        l4.setBounds(0, 320, 200, 100);

        JLabel l5 = setLabel("Login", 15, Color.black);
        l5.setBounds(0, 420, 200, 100);

        JLabel l6 = setLabel("Senha", 15, Color.black);
        l6.setBounds(0, 520, 200, 100);

        JLabel l7 = setLabel("Ativo", 15, Color.black);
        l7.setBounds(0, 620, 200, 100);

        JLabel l8 = setLabel("Acesso", 15, Color.black);
        l8.setBounds(0, 720, 200, 100);

        JTextField iptNome = setJTextFIld(20);
        iptNome.setBounds(80, 55, 600, 30);

        JTextField iptSobreNome = setJTextFIld(20);
        iptSobreNome.setBounds(840, 55, 680, 30);

        JTextField iptEmail = setJTextFIld(20);
        iptEmail.setBounds(80, 155, 1450, 30);

        JTextField iptTelefone = setJTextFIld(20);
        iptTelefone.setBounds(80, 255, 1450, 30);

        JTextField iptCargo = setJTextFIld(20);
        iptCargo.setBounds(80, 355, 1450, 30);

        JTextField iptLogin = setJTextFIld(20);
        iptLogin.setBounds(80, 455, 1450, 30);

        JTextField iptSenha = setJTextFIld(20);
        iptSenha.setBounds(80, 555, 1450, 30);
        String [] status = {"Ativo" , "Desativado"  };
        JComboBox iptStatus = seletorString(status);
        iptStatus.setBounds(80, 655, 1450, 30);

        String [] acesso = {"adm" , "agente"};

        JComboBox iptAcesso = seletorString(acesso);
        iptAcesso.setBounds(80, 755, 1450, 30);
        painel.add(l1);
        painel.add(l01);
        painel.add(l2);
        painel.add(l3);
        painel.add(l4);
        painel.add(l5);
        painel.add(l6);
        painel.add(l7);
        painel.add(l8);
        painel.add(iptNome);
        painel.add(iptSobreNome);
        painel.add(iptEmail);
        painel.add(iptTelefone);
        painel.add(iptCargo);
        painel.add(iptLogin);
        painel.add(iptSenha);
        painel.add(iptStatus);
        painel.add(iptAcesso);
        Object[] options = { "Registrar novo acesso "};

        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,
                "Registro do agente "  ,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        String inputNome = iptNome.getText();
        String inputSobrenome = iptSobreNome.getText();
        String inputEmail = iptEmail.getText();
        String inputTelefone = iptTelefone.getText();
        String inputCargo = iptCargo.getText();
        String inputLogin = iptLogin.getText();
        String inputSenha = iptSenha.getText();
        String inputStatus = (String) iptStatus.getSelectedItem();
        String inputAcessoTipo  = (String)iptAcesso.getSelectedItem();

        if (resposta == 0) {
            if(inputNome.isEmpty() ||  inputSobrenome.isEmpty() || inputEmail.isEmpty() ||
                    inputTelefone.isEmpty() || inputCargo.isEmpty() ||  inputLogin.isEmpty()
                    || inputSenha.isEmpty() || inputStatus.isEmpty() || inputAcessoTipo.isEmpty()  ) {
                JOptionPane.showMessageDialog(null , "Não foi possivel salvar dados do usuário há campos em brancos ", "" , JOptionPane.CLOSED_OPTION);
            }else {
                String vetor [] = { inputNome , inputSobrenome , inputEmail , inputTelefone , inputCargo , inputLogin , inputSenha  , inputStatus , inputAcessoTipo } ;
                return vetor ;
            }
        }
        String vetor[] = {null} ;
        return vetor ;

    }

//--------------------------- Exclusivo para o painel tarefas ------------------------

String [] tiposStatus =   {"Aberto" , "Concluido" ,"Validando", "Atendendo" };
    String [] tiposPrioridade = {"Baixa" , "Media" ,"Alta", "Urgente" } ;

    public void terefaVisaoGeral (int idAgenteTarefa ,  int admGernciaTarefa ) {

        JPanel painel = setPanel(1600 , 900 , null );
        painel.setLayout(null);

        JSeparator sp1 = setSeparador();
        sp1.setBounds(0 , 52 , 1600 , 40);

        JLabel l1 = setLabel("Titulo do chamado " , 12 , Color.black) ;
        l1.setBounds( 0 , 100 ,200 , 50  );

        JTextField impt1 = setJTextFIld(20);
        impt1.setBounds(150 , 110 , 1450 , 30 );

        JLabel l2 = setLabel("Status da tarefa " , 12 , Color.BLACK);
        l2.setBounds(0 , 150 , 200 , 50  );

         JComboBox seletorStatus = seletorString( tiposStatus ) ;
        seletorStatus.setBounds(150 , 160 , 1450 , 30 );

        JLabel l3 = setLabel("Prioridade " , 12 , Color.BLACK);
        l3.setBounds(0 , 200 , 100 , 50 );

         JComboBox seletorPrioridade = seletorString( tiposPrioridade ) ;
        seletorPrioridade.setBounds(150 , 210 , 1450 , 30 );

        JLabel l4 = setLabel("Data / Hora estimado para finaizar a tarefa" , 12 , Color.BLACK);
        l4.setBounds(0  , 250 , 350 , 50 );

 //--------------------------------------------------------------------------------\\
//----------------------------------------------------------------------------------\\

        String []  tipoDias = new String[32];
        tipoDias = tempoCorrido ( 32 ,  tipoDias  ) ;
        tipoDias[0] = "Dia" ;
        JComboBox seletorDia = setSeletorString(tipoDias) ;
        seletorDia.setBounds(320 , 260 , 100 , 30  );

        // --------------------

        String []  tipoMes = new String[13];
        tipoMes = tempoCorrido ( 13  ,  tipoMes  ) ;
        tipoMes[0] = "Mês" ;

        JComboBox selectorMes = setSeletorString(tipoMes);
        selectorMes.setBounds(450 , 260 , 100 , 30 );


        // --------------------

        String [] tipoAno = new String [20] ;
        int aux = 2022 ;
        for (int i = 0 ; i < 10; i++){

            aux = aux + 1 ;
            tipoAno[i] = ""+aux ;
        }
        tipoAno[0] = "Ano";

        JComboBox selectorAno = setSeletorString(tipoAno);
        selectorAno.setBounds(570 , 260 , 100 , 30 );

        // --------------------

        String []  tipoHora = new String[25];
        tipoHora = tempoCorrido ( 25 ,  tipoHora  ) ;
        tipoHora[0] = "Horas" ;
        tipoHora[24] = "0" ;

        JComboBox selectorHora = setSeletorString(tipoHora);
        selectorHora.setBounds(700 , 260 , 100 , 30 );

        // --------------------

        String []  tipoMin = new String[61];
        tipoMin = tempoCorrido ( 25 ,  tipoMin  ) ;
        tipoMin[0] = "Minutos" ;

        JComboBox selectorMin = setSeletorString(tipoMin);
        selectorMin.setBounds(840 , 260 , 100 , 30 );
        // --------------------

        JLabel l5 = setLabel("Descrição da tarefa" , 12 , Color.BLACK);
        l5.setBounds(0 , 300 , 400 , 50 );

        JTextArea textoArea = setAreaTexto (10, 10);
        JScrollPane scrollText = new JScrollPane(textoArea);
        scrollText.setBounds(0 , 350 , 1600 , 500 );

        //--------------------------------------------------------------------------------------

        painel.add(sp1);
        painel.add(l1);
        painel.add(impt1);
        painel.add(l2);
        painel.add(seletorStatus);
        painel.add(l3);
        painel.add(seletorPrioridade);
        painel.add(l4);
        painel.add(seletorDia);
        painel.add(selectorMes);
        painel.add(selectorAno);
        painel.add(selectorHora);
        painel.add(selectorMin);
        painel.add(l5);
        painel.add(scrollText);


        //====================================
        Object[] options = { "Salvar tarefa "};

        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,

                "Tarefas" ,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]

        );

            if(resposta == 0 ){
                boolean validarCaractericosBrancos = false ;

                String areaTextoSTR = textoArea.getText().replace("\t", "         ") ;

                String [] vetor = {
                        impt1.getText(),
                        (String) seletorStatus.getSelectedItem() ,
                        (String) seletorPrioridade.getSelectedItem(),
                        (String) seletorDia.getSelectedItem(),
                        (String)  selectorMes.getSelectedItem(),
                        (String) selectorAno.getSelectedItem(),
                        (String) selectorHora.getSelectedItem(),
                        (String) selectorMin.getSelectedItem() ,
                        areaTextoSTR.replaceAll("\n", " _==_ ") // <<==================================
                };

                validarCaractericosBrancos =  varificaoGeral.validaCarcatericosVaziosBrancosVetor(vetor);

                if( validarCaractericosBrancos != true ){

                    JOptionPane.showInputDialog(null," Não foi possivel abrir o chamado pois há campos obrigatorios em brancos ","",JOptionPane.WARNING_MESSAGE);

                }else {

                    LocalDateTime now = LocalDateTime.now();
                    int dia = Integer.parseInt( (String) seletorDia.getSelectedItem());
                    int mes = Integer.parseInt( (String) selectorMes.getSelectedItem());
                    int ano = Integer.parseInt( (String) selectorAno.getSelectedItem());
                    int hora  = Integer.parseInt( (String) selectorHora.getSelectedItem());
                    int min  =  Integer.parseInt( (String) selectorMin.getSelectedItem());

                    LocalDateTime temṕoPrevisto = LocalDateTime.of(ano, mes , dia , hora , min , 00);
                    LocalDateTime tempoAtual = LocalDateTime.now();

                    if(temṕoPrevisto.isAfter(tempoAtual)){

                        String ut = retornosApi.adicionarTarefas(vetor , idAgenteTarefa );
                        JOptionPane.showMessageDialog(null , ut  ,"" , JOptionPane.INFORMATION_MESSAGE);

                    }else{

                        JOptionPane.showInputDialog(null,"Não foi possivel abrir a tarefa, pois"+
                                " as datas de previsões execução do chamado já pasaram ","",JOptionPane.WARNING_MESSAGE);

                    }

                }
            }
        //====================================

    }

    public String[] tempoCorrido (int aux , String [] vetor ){
        int cont = 0 ;
        for( int i =  1 ; i < vetor.length ; i++){
            cont = cont + 1 ;
            vetor[i] = ""+i ;
        }
        return vetor ;
    }

    public String[] filtroTarefas (){

        JPanel painel = setPanel(700 , 250 , null );

        //        -------------------------

        JLabel l1 = setLabel("Selecione o tipo de filtro da pesquisa de suas tarefas : " , 12 , Color.black);
        l1.setBounds (25 , 25 ,  400 , 30 ) ;

        String [] tiposPesquisa = {"Todas tarefas" , "Numero da tarefa" , "Status da Tarefa", "Prioridade da tarefa"} ;
        JComboBox seletorFiltro = setSeletorString(tiposPesquisa);
        seletorFiltro.setBounds(25 , 80 , 650 , 40 );

        //        -------------------------

        JLabel l2 = setLabel("Adicione o numero da tarefa : " , 12 , Color.black);
        l2.setBounds(25 , 80 , 650 , 40 );

        JTextField iptNumTarefas = setJTextFIld(20);
        iptNumTarefas.setBounds(260 , 138 , 400 , 30  );

        //       -------------------------

        JLabel l3 = setLabel("Selecione o status da tarefa : " , 12 , Color.BLACK);
        l3.setBounds(25 , 130 , 650 , 40 );

//        String[] optStatus = {"Aberto" , "Concluido" ,"Analisando", "Atendendo" };
        JComboBox seletoTarefasStatus = setSeletorString(tiposStatus);
        seletoTarefasStatus.setBounds(260 , 135 , 410 , 30  );

//        -------------------------

        JLabel l4 = setLabel("Selecione a prioridade da tarefa : " , 12 , Color.BLACK);
        l4.setBounds(25 , 130 , 650 , 40 );

//        String[] optPrioridades ={"Baixa" , "Media" ,"Alta", "Urgente" };
        JComboBox seletoTarefasPrioridade = setSeletorString(tiposPrioridade);
        seletoTarefasPrioridade.setBounds(270 , 135 , 400 , 30  );


        painel.add(l1);
        painel.add(seletorFiltro);


        seletorFiltro.addActionListener(err1 ->{
            String valorSltFiltro = (String) seletorFiltro.getSelectedItem();

            if(valorSltFiltro.equals("Todas tarefas")){

                painel.removeAll();

                painel.add(l1);
                painel.add(seletorFiltro);

                painel.revalidate();
                painel.repaint();



            }
            if(valorSltFiltro.equals("Numero da tarefa")){

                painel.removeAll();

                painel.add(seletorFiltro);
                painel.add(l1);
                painel.add(l2);
                painel.add(iptNumTarefas);

                painel.revalidate();
                painel.repaint();

            }
            if(valorSltFiltro.equals("Status da Tarefa")){

                painel.removeAll();

                painel.add(seletorFiltro);
                painel.add(l1);
                painel.add(l3);
                painel.add(seletoTarefasStatus);

                painel.revalidate();
                painel.repaint();

            }
            if(valorSltFiltro.equals("Prioridade da tarefa")){
                painel.removeAll();

                painel.add(seletorFiltro);
                painel.add(l1);
                painel.add(l4);
                painel.add(seletoTarefasPrioridade);

                painel.revalidate();
                painel.repaint();


            }



        });


        Object[] options = { "Aplicar filtro de pesquisa"};
        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,
                "FIltro de pequisa "  ,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );
        String valorSltFiltro = (String) seletorFiltro.getSelectedItem();

        if( valorSltFiltro.equals("Todas tarefas")){

            return new String [] {"full"};
        }
        if(valorSltFiltro.equals("Numero da tarefa")){
              try{
                  int j = Integer.parseInt(iptNumTarefas.getText());
                  return new String [] { "1" , ""+j};
             }catch (Exception err){
                 return new String [] {"Adicione um valor inteiro no campo de pesquisa"};
             }
        }
        if(valorSltFiltro.equals("Status da Tarefa")){

            return new String [] { "2" , (String) seletoTarefasStatus.getSelectedItem() };
        }

        if(valorSltFiltro.equals("Prioridade da tarefa")){
            return new String [] {"3", (String)  seletoTarefasPrioridade.getSelectedItem()};
        }



        return new String [] {""};
    }


    int  dtsComentario = 50 ;


    public void tarefasAberta (int idTarefa , int idAgente ){

        String[][] retApi = retornosApi.pqsDadosTarefas(idTarefa) ;

        String[][] retApiComentarios = retornosApi.pqsDdosComentarios (idTarefa) ;

        String[][] dadosComentario = new String[retApiComentarios.length][4];
        dadosComentario = retornosApi.pqsDdosComentarios (idTarefa) ;


        JPanel painel = setPanel(1700 , 900 , null );

        // --------------------------------------------------------


        System.out.println(" ====================================== ");
        for(int i = 0 ; i <  dadosComentario.length ; i++){

            System.out.println(i+" - "+dadosComentario[i][0]);
            System.out.println(i+" - "+dadosComentario[i][1]);
            System.out.println(i+" - "+dadosComentario[i][2]);
            System.out.println(i+" - "+dadosComentario[i][3]);
        }
        System.out.println(" ====================================== ");

        int w = 1650 ;


        JButton btn1 = setBoutton("Descrições" , null) ;
        btn1.setBounds(0 , 0 , 200  , 50);

        JTextField titulo = setJTextFIld(20) ;
        titulo.setBounds(0 , 100 , w , 30 );
        titulo.setEditable(false);
        titulo.setText("Titulo : "+retApi[0][4]);

        JComboBox seletorStatus = seletorString( tiposStatus ) ;
        seletorStatus.setBounds(0 , 160 , w , 30 );
        seletorStatus.setSelectedItem(retApi[0][3]);
        String regStatus = (String) seletorStatus.getSelectedItem();
        if(retApi[0][3].equals("Concluido")){
            seletorStatus.setEnabled(false);
        }

        JComboBox seletorPrioridade = seletorString(tiposPrioridade);
        seletorPrioridade.setBounds(0, 210, w, 30);
        seletorPrioridade.setEnabled(false);
        seletorPrioridade.setSelectedItem(retApi[0][7]);
        String regPrioridade = (String) seletorPrioridade.getSelectedItem();

        JTextArea textoArea = setAreaTexto (10, 10);
        textoArea.setEditable(false);
        String descTarefaQuebrarLinha = retApi[0][8].replaceAll("_==_", "\n").replace(" _==_ ","\n"); // <<==================================
        textoArea.setText("\nDescrições da tarefa : \n\n"+descTarefaQuebrarLinha);
        JScrollPane scrollText = new JScrollPane(textoArea);
        scrollText.setBounds(0 , 250 , w , 640 );

       JLabel l1_2 = setLabel("Tarefa aberta dia "+retApi[0][5]+
        ", previsão de execução "+retApi[0][6]  , 15 ,Color.black );
        l1_2.setBounds(500 , 50 , 1000 , 50);

        // --------------------------------------------------------
        JButton btn2 = setBoutton("Respostas",null);
        btn2.setBounds(203 , 0  , 200 , 50);


        JLabel l1_1 = setLabel("Resgistro da tarefa " + idTarefa , 15 , Color.BLACK);
        l1_1.setBounds(0 , 80 , 500 , 50 );

        JPanel p1 = setPanel(500 , 500 , null);
        JScrollPane scrollP1 = new JScrollPane(p1);
        scrollP1.setBounds(0 , 120 , w , 700 );



//        ==================================== dentro do p1============


//        JLabel img1 = setImageDimensaoCircular("src/TaredasDefinidas/eu.enc", 50 ,50 );
//        img1.setBounds(30 , 30 , 90 , 90 );


//        JLabel dsc1 = setLabel("Tarefa aberto pelo usuário id "+retApi[0][2],  14 , Color.BLACK);
//        dsc1.setBounds(760 , 30 , 400 , 30 );



        JButton btn3 = setBoutton("Adicionar comentarios" ,  null);

        btn3.setBounds(1500, 850 , 200 , 30 );
        dtsComentario = 80 ;
        JLabel comentario = null ;
        JLabel img1 = null ;
        for(int i = 0 ; i < dadosComentario.length; i++){

            img1 = setImageDimensaoCircular("src/TaredasDefinidas/eu.enc", 50 ,50 );
            img1.setBounds(30 , dtsComentario - 30 , 90 , 90 );

            comentario = setLabel( ""+dadosComentario[i][2], 14 , Color.black);
            comentario.setBounds(100 , dtsComentario , dadosComentario[i][2].length() * 10 , 30);

             dtsComentario = dtsComentario + 70 ;

            p1.setPreferredSize(  new Dimension(1600 , dtsComentario ) );
            p1.add(comentario);
            p1.add(img1);


        }



        // --------------------------------------------------------

        painel.add(btn1);
        painel.add(btn2);
        painel.add(titulo);
        painel.add(seletorStatus);




        painel.add(seletorPrioridade);
        painel.add(scrollText);
        painel.add(l1_2);

// ---------- ação btns

        btn1.addActionListener(err->{
            painel.removeAll();
            painel.add(btn1);
            painel.add(btn2);

            painel.add(titulo);
            painel.add(seletorStatus);
            painel.add(seletorPrioridade);
            painel.add(scrollText);
            painel.add(l1_2);


            painel.repaint();
            painel.revalidate();

        });
        btn2.addActionListener(err->{


            painel.removeAll();

            painel.add(btn1);
            painel.add(btn2);

            painel.add(l1_1);
//            painel.add(dsc1);



//******************************************************
            painel.add(scrollP1);
 //            p1.add(dsc1);

//******************************************************

            painel.add(scrollP1);



            painel.add(btn3);
            if(retApi[0][3].equals("Concluido")){
                painel.remove(btn3);
            }


            painel.repaint();
            painel.revalidate();

        });
        btn3.addActionListener( err -> {

           String validaRetComentario =  addComentarisoTarefas( idTarefa , idAgente , regStatus , regPrioridade );
           JOptionPane.showMessageDialog(null ," "+validaRetComentario  ,"" , JOptionPane.WARNING_MESSAGE);

            String[][] retApiComentarios2 = retornosApi.pqsDdosComentarios (idTarefa) ;
            String[][] dadosComentario2 = new String[retApiComentarios.length][4];
            dadosComentario2 = retornosApi.pqsDdosComentarios (idTarefa) ;

            JLabel comentario2 = null ;
            JLabel img1_2 = null ;


            painel.removeAll();

            for(int i = 0 ; i < dadosComentario2.length; i++){
                  comentario2 = setLabel( ""+dadosComentario2[i][2], 14 , Color.black);
                comentario2.setBounds(100 , dtsComentario , dadosComentario2[i][2].length() * 10 , 30);

                img1_2= setImageDimensaoCircular("src/TaredasDefinidas/eu.enc", 50 ,50 );
                img1_2.setBounds(30 , dtsComentario - 30 , 90 , 90 );

                dtsComentario = dtsComentario + 70 ;

                p1.setPreferredSize(  new Dimension(1600 , dtsComentario ) );

                p1.add(comentario2);
                p1.add(img1_2);

            }

            p1.add(comentario2);

            painel.add(btn1);
            painel.add(btn2);

            painel.add(l1_1);


//****************************************************** painel p1
            painel.add(scrollP1);
 //            p1.add(dsc1);

//******************************************************

            painel.add(scrollP1);
            painel.add(btn3);


            painel.repaint();
            painel.revalidate();

        });



        // --------------------------------------------------------

        Object[] options = { "Salvar edições realizada "};
        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,
                 "Tarefa "+idTarefa ,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]

        );


        if (resposta == 0) {

          String r =   retornosApi.salvaEstadoFinalTarefa( idTarefa ,  (String) seletorStatus.getSelectedItem() ) ;
            JOptionPane.showMessageDialog(null , ""+r , "", JOptionPane.WARNING_MESSAGE);
        }

    }

    public String addComentarisoTarefas(int id_tarefa, int id_user_comentado, String status, String prioridade) {
        JPanel painel = setPanel(750, 400, null);

         JTextArea textArea = setAreaTexto(10, 10);

        JLabel contadorLabel = new JLabel("0/1000 caracteres ");
        contadorLabel.setBounds(10, 280, 200, 20); // Posicione o contador abaixo da JTextArea
        painel.add(contadorLabel);

        // ============================
        ((PlainDocument) textArea.getDocument()).setDocumentFilter(new DocumentFilter() {
            private final int MAX_CHAR = 1000;

            @Override
            public void insertString(FilterBypass fb, int offset, String str, AttributeSet attr) throws BadLocationException {
                if (fb.getDocument().getLength() + str.length() <= MAX_CHAR) {
                    super.insertString(fb, offset, str, attr);
                    atualizarContador(fb.getDocument());
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String str, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() - length + (str != null ? str.length() : 0) <= MAX_CHAR) {
                    super.replace(fb, offset, length, str, attrs);
                    atualizarContador(fb.getDocument());
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
                atualizarContador(fb.getDocument());
            }

            private void atualizarContador(Document doc) {
                contadorLabel.setText(doc.getLength() + "/" + MAX_CHAR + " caracteres");
            }
        });
        // ============================

        JScrollPane scrollText = new JScrollPane(textArea);
        scrollText.setBounds(0, 100, 700, 170);
        painel.add(scrollText);

        Object[] options = {"Adicionar comentário"};
        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,
                "Tarefa " + id_tarefa + " | ID User comentado " + id_user_comentado,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );


        String[] vetor = {
                "" + id_tarefa,
                "" + id_user_comentado,
                textArea.getText().replaceAll("\n", " ").replaceAll("\t", " "),
                status,
                prioridade
        };

        boolean validaStr = varificaoGeral.validaCarcatericosVaziosBrancosVetor(vetor);

        if (validaStr) {
            String retApi = retornosApi.addComentarios(vetor);
            return retApi;
        } else {
            return "Não foi possível gravar o comentário, pois há campos vazios.";
        }
    }

    public void dadosTarefas  (String id_agente ){

        Object  [] matrix = new Object [4];
        matrix = retornosApi.psqIndicadores(Integer.parseInt(id_agente));

        System.out.println(">>> >>> .>> "+matrix[0]);
        System.out.println(">>> >>> .>> "+matrix[1]);
        System.out.println(">>> >>> .>> "+matrix[2]);
        System.out.println(">>> >>> .>> "+matrix[3]);

        int soma = 0 ;
        for (int i = 0 ;  i < matrix.length ;i++){
            soma = soma + (int) matrix[i] ;
        }



        JPanel painel = setPanel(200 , 400 , null);

        JLabel l1 = setLabel("Totais de tarefas "+soma , 12 , Color.BLACK);
        l1.setBounds(0 , 60 , 300 , 50 );

        JLabel l2 = setLabel("Tarefas abertas "+matrix[0] , 12 , Color.black);
        l2.setBounds(0 , 110 , 300 , 50 );

        JLabel l3 = setLabel("Tarefas concluidas "+matrix[1], 12 , Color.black);
        l3.setBounds(0 , 150 , 300 , 50 );

        JLabel l4 = setLabel("Tarefas em validação "+matrix[2] , 12 , Color.BLACK);
        l4.setBounds(0 , 200 , 300 , 50 );

        JLabel l5 = setLabel("Tarefas em atendimento "+matrix[3] , 12 , Color.BLACK);
        l5.setBounds(0 , 250 , 300 , 50 );

        painel.add(l1);
        painel.add(l2);
        painel.add(l3);
        painel.add(l4);
        painel.add(l5);

        Object[] options = { "Salvar edições realizada "};
        int resposta = JOptionPane.showOptionDialog(
                null,
                painel,
                "Aente"+id_agente ,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]

        );
    }

}