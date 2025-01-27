package TaredasDefinidas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TarefasGeral  extends Componentes{

    Apis api = new Apis();
    JanelasJOP janelaJop =  new JanelasJOP();

//========================================================================================

    public JScrollPane scrPanne(JFrame janela , int id  ) {

//=====================================================================================================
// =====================================================================================================

        int width = janela.getWidth();
        int height = janela.getHeight();

//<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        JPanel p1 = setPanel(width, height, Color.black);
        p1.setBounds(0 , 0 , width , height);


        JLabel l1 = getTituloPainel("Tarefas");

        String [] selecaoOp = {"Ativos","Desativados"} ;
        JComboBox selectorStauts = setSeletorString(selecaoOp);

        JButton botaoFiltrar = setBoutton("Aplicar busca", Color.white);

        JTextField imput = setJTextFIld(20); // <-------- imput
        imput.setBounds(0 , 110  , 100 , 20 ); // <-------- imput

//        JButton bt1 = setBoutton("bt1 > ", Color.orange);
//        bt1.setBounds(0 , 200 , 100 , 20 );

        JSeparator separador1 = setSeparador();
//<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

        janela.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                 int width =  janela.getWidth() ;
                  separador1.setBounds(0 , 399 , width , 10  );
                selectorStauts.setBounds(0, 300 , width - 250 , 25);
                botaoFiltrar.setBounds(0 , 340 ,width - 250 , 25);
             }
        });



        p1.add(l1);
        p1.add(separador1);
        p1.add(selectorStauts);
        p1.add(botaoFiltrar);
        p1.add(botaoFiltrar);

        botaoFiltrar.addActionListener(e -> {
             boolean booleastatus;
            String valorSeletorStatus = (String) selectorStauts.getSelectedItem();

            if (valorSeletorStatus.equals("Ativos")) {
                booleastatus = true;
            } else {
                booleastatus = false;
            }

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

            p1.removeAll();

            p1.add(l1);
            p1.add(separador1);
            p1.add(selectorStauts);
            p1.add(botaoFiltrar);

            p1.revalidate();
            p1.repaint();


            String[][] dadosApiRetorno  = api.dadosAgentesTarefas(booleastatus);
            p1.setPreferredSize(new Dimension(100,100 * dadosApiRetorno.length)); // <<<--------------

            int posicaoComponenteDadosAgente = 450;
            JLabel labelDadosAgenteApi = null;
            JButton acessoCont = null; // <<---------------------



//=====================================================================================================
//=====================================================================================================

            for (int j = 0; j < dadosApiRetorno.length; j++) { // <<--------------------- AQUI ELMENTOS DE MOSTRAR AGENTES FILTRADOS

                labelDadosAgenteApi = setLabel("ID : " + dadosApiRetorno[j][0] + " |  Agente : " + dadosApiRetorno[j][1] +" "+dadosApiRetorno[j][3]  , 18, Color.white);
                labelDadosAgenteApi.setBounds(30, posicaoComponenteDadosAgente, 800, 30);
                acessoCont = setBoutton("acessar", Color.gray);
                acessoCont.setBounds(950, posicaoComponenteDadosAgente, 500, 30);

                posicaoComponenteDadosAgente += 70;

                p1.add(labelDadosAgenteApi);
//                System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> "+  dadosApiRetorno[j][0]);

                if( dadosApiRetorno[j][0].equals("0") ){
                    p1.remove(acessoCont);
                }else {
                    p1.add(acessoCont);

                }

                int valorClicadoFOR = j;



                acessoCont.addActionListener(er ->{ // <<--------------------- AQUI ELMENTOS ACESSAR AGENTES PARA CRIAR OU VER TAREFAS

                    String idStr = dadosApiRetorno[valorClicadoFOR][0];
                    boolean statusAtivo =  Boolean.parseBoolean(dadosApiRetorno[valorClicadoFOR][1]);
                    statusAtivo = Boolean.parseBoolean(dadosApiRetorno[valorClicadoFOR][2] ) ;

                    JLabel l2 = setLabel("Agente : "+dadosApiRetorno[valorClicadoFOR][1]+" "+dadosApiRetorno[valorClicadoFOR][3]+" | I.D : "+idStr+ " | Status : "+ statusAtivo , 18, Color.white);
                    l2.setBounds(200,  32 , 1000 , 50 );

                    JButton bt2 = setBoutton("Voltar" , Color.black);
                    bt2.setBounds(50 , 30 , 100 , 40 );

                    JLabel l3 = setLabel(" Tarefas " , 25 , Color.white);
                    l3.setBounds(50 , 140 , 400 , 40 );

                    JButton bt3 = setBoutton("Gerar nova tarefa" , Color.black);
                    bt3.setBounds(50 , 200 , width-320 , 50 );

                    JLabel l4 = setLabel("Registro de chamados " , 15 , Color.white);
                    l4.setBounds(50 , 260 , 1000 , 50 );

                    JButton btn4 =  setBoutton("Pesquisar tarefas" , Color.black ) ;
                    btn4.setBounds(300 , 270 , 200 , 30 );

                    String [] rowTabela = {"Chamado" , "Titulo" , "Status" , "Criado" , "Prioridade" , "acessar "};
                    DefaultTableModel modelo = setModelo(rowTabela);
                    JTable tabela = setTabela(rowTabela , modelo , 0 );

                    JTableHeader cabecalho = setCabecalhoHeader(tabela);
                    JScrollPane tabelaRolagem = new JScrollPane(tabela);
                    tabelaRolagem.getVerticalScrollBar().setBackground(Color.black);
                    tabelaRolagem.getHorizontalScrollBar().setBackground(Color.black);
                    tabelaRolagem.setBackground(Color.black);
                    tabelaRolagem.setBounds(50 , 320 , 900 , 400 );

                    JSeparator sp1 = setSeparadorHorizontal(0);
                    sp1.setBounds(980 , 320 , 20 , 600 );

                    String ft = api.getCaminhoFoto(Integer.parseInt(idStr));
                    ft = ft + "&timestamp=" +  java.util.UUID.randomUUID();
                    JLabel fotoUser = setImageDimensaoCircularURL(ft,  350, 350 ) ;
                    fotoUser.setBounds(1100 , 350 , 350 , 350);

                    JButton btnIndicadores = setBoutton("Visão geral de chamados" , Color.orange);
                    btnIndicadores.setBounds(1100 , 800 , 380 , 50 );

                    btnIndicadores.addActionListener(err->{
                        janelaJop.dadosTarefas(idStr);
                    });

                    p1.removeAll();
                    p1.add(l2);
                    p1.add(bt2);
                    p1.add(l3);

                    if(statusAtivo  == true ){
                        p1.add(bt3);
                    }
                    p1.add(l4);
                    p1.add(tabelaRolagem);
                    p1.add(sp1);
                    p1.add(fotoUser);
                    p1.add(btnIndicadores);
                    p1.add(btn4);

                    janela.repaint();
                    janela.revalidate();

                    bt2.addActionListener(err->{

                        p1.removeAll();

                        p1.add(l1);
                        p1.add(separador1);
                        p1.add(selectorStauts);
                        p1.add(botaoFiltrar);


                        janela.repaint();
                        janela.revalidate();

                    });


                    bt3.addActionListener(e2->{
                        int agenteVisualizacao = Integer.parseInt(idStr);


//                        (int idAgenteTarefa , int numeroTarefa , boolean novaTarefa  , int admGernciaTarefa )
                        janelaJop.terefaVisaoGeral(  agenteVisualizacao ,  id ) ;
                     });

                    btn4.addActionListener(er4 ->{
                        String chamado , titulo , status , criado , prioridade , editar ;

                        modelo.setRowCount(0);



                        String [] vetorPesquisa = janelaJop.filtroTarefas() ;
                        int agenteVisualizacao = Integer.parseInt(idStr);

                        if(vetorPesquisa[0] .equals("full")){

                            try{
                                int cont = 0 ;
                                String[][] retornoPesquisa = api.pqsTarefaGeral( agenteVisualizacao );
                                modelo.setRowCount(0);
                                for (int i = 0; i < retornoPesquisa.length; i++) {
                                    chamado = retornoPesquisa[i][0];
                                    titulo = retornoPesquisa[i][1];
                                    status = retornoPesquisa[i][2];
                                    criado = retornoPesquisa[i][3];
                                    prioridade = retornoPesquisa[i][4];
                                     editar = "EDITAR";
                                    modelo.addRow(new Object[]{chamado, titulo , status , criado , prioridade,  editar});
                                    cont = cont + 1 ;
                                }


                                tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 100)); // 2000

                            }catch (Exception err){
                                JOptionPane.showMessageDialog(null , "Erro na execução da pesquisa "+err , "" , JOptionPane.CLOSED_OPTION);

                            }




                        }
                        if(vetorPesquisa[0] .equals("1")){
                            int cont = 0 ;
                            String[][] retornoPesquisa = new String[1][5] ;
                            retornoPesquisa = api.pqsNumTarefa( agenteVisualizacao , vetorPesquisa[1] );
                         modelo.setRowCount(0);


                                    chamado = retornoPesquisa[0][0];
                                    titulo = retornoPesquisa[0][1];
                                    status = retornoPesquisa[0][2];
                                    criado = retornoPesquisa[0][3];
                                    prioridade = retornoPesquisa[0][4];
                                    editar = "EDITAR";

                                    modelo.addRow(new Object[]{chamado, titulo , status , criado , prioridade,  editar});


                                tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  500)); // 2000



                        }
                        //Status da Tarefa
                        if(vetorPesquisa[0].equals("2")){

                            try {

                                int cont = 0 ;
                                String[][] retornoPesquisa = api.pqsStatusTarefas(  agenteVisualizacao , vetorPesquisa[1]  );


                                modelo.setRowCount(0);
                                for (int i = 0; i < retornoPesquisa.length; i++) {

                                    chamado = retornoPesquisa[i][0];
                                    titulo = retornoPesquisa[i][1];
                                    status = retornoPesquisa[i][2];
                                    criado = retornoPesquisa[i][3];
                                    prioridade = retornoPesquisa[i][4];
                                    editar = "EDITAR";
                                    modelo.addRow(new Object[]{chamado, titulo , status , criado , prioridade,  editar});
                                    cont = cont + 1 ;
                                }

                                tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 100)); // 2000

                            }catch (Exception err){


                            }

                        }
                        if(vetorPesquisa[0].equals("3")){
                            try {

                                int cont = 0 ;
                                String[][] retornoPesquisa = api.pqsPrioridadeTarefa(  agenteVisualizacao , vetorPesquisa[1]  );

                                modelo.setRowCount(0);

                                for (int i = 0; i < retornoPesquisa.length; i++) {

                                    chamado = retornoPesquisa[i][0];
                                    titulo = retornoPesquisa[i][1];
                                    status = retornoPesquisa[i][2];
                                    criado = retornoPesquisa[i][3];
                                    prioridade = retornoPesquisa[i][4];
                                    editar = "EDITAR";
                                    modelo.addRow(new Object[]{chamado, titulo , status , criado , prioridade,  editar});
                                    cont = cont + 1 ;
                                }

                                tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 100)); // 2000

                            }catch (Exception err){


                            }
                        }



                    });



                    try {

                        tabela.addMouseListener(new MouseAdapter() {

                            @Override
                            public void mouseClicked(MouseEvent e) {

                                int row = tabela.rowAtPoint(e.getPoint());
                                int column = tabela.columnAtPoint(e.getPoint());

                                if (column == 5 ) {
                                    String cliqueTabela = tabela.getValueAt(row, 0).toString();
                                    int idStrTarefa = Integer.parseInt(cliqueTabela);
                                     modelo.setRowCount(0);

                                    JOptionPane.showMessageDialog(null ,"Acessando dados do usuário de ID :  "+idStrTarefa, "", JOptionPane.CLOSED_OPTION);
                                    janelaJop.tarefasAberta(idStrTarefa , id);
                                }
                            }


                        });
                    }catch(Exception err ) {
                        System.out.println(">> erro "+err);

                    }

                });


             }

            p1.revalidate();
            p1.repaint();
        });

//=====================================================================================================
//=====================================================================================================



        JScrollPane painelRolante = setRolagem( p1 );
        painelRolante.setBounds(250, 0, width, height);
         return painelRolante;
    }





}
