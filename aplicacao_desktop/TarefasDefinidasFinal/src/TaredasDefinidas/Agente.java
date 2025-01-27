package TaredasDefinidas;



import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;


public class Agente  extends Componentes {
    Apis api = new Apis();


    public JScrollPane AgentePainel1 (JFrame janela   ) {
        VerificacaoGeral verificarGeral = new VerificacaoGeral();
        JanelasJOP janelaJP = new JanelasJOP() ;


//--------------------------------------------------------------------------
        JPanel painel = setPanel(0 , 0 , Color.BLACK);
        painel.setPreferredSize(new Dimension(100,1050));
        JScrollPane scrollPane = new JScrollPane(painel);
//--------------------------------------------------------------------------

        JLabel titulo = getTituloPainel("Acessos "  );

        JLabel lb1 = fontPadrao("Filtro avançado : ");
        lb1.setBounds(25 , 140 , 200 , 150 );

        JLabel lb01 = fontPadrao("Tipo de acesso : ");
        lb01.setBounds(25 , 260 , 150 , 20 );

        String [] acessos = {"Todos","agente" , "adm" } ;
        JComboBox tipoAcesso = seletorString(acessos);
        tipoAcesso.setBounds(180 , 260 , 200 , 20 );

        JLabel lb2 = fontPadrao("Adicionar conteudo do filtro aplicado para as pesquisas por nome do agente , login do agente e id de acesso : ");
        lb2.setBounds(25 , 280 , 1000 , 150 );


        String vetor [] = {"Filtrar todos agentes" , "Filtrar pelo nome do agente" , "Filtrar pelo login do agente" , "Filtrar pelo id de acesso"};
        JComboBox seletorPesquisa = seletorString(vetor);

        JTextField ImputDadosAgente = setJTextFIld(20);
        JSeparator separador1 = setSeparador();
        JButton botaoPesquisa = setBoutton("Aplicar pesquisa avançada" , Color.white ) ;
        JButton botaoAddAgente = setBoutton("Adicionar novo  agente", null);

        //  tabela =================================================================

        String [] colunasTabela = {"Login","ID","Nome","Ativo","E-mail","Criado", "Editar"} ;

        DefaultTableModel modelo = setModelo (colunasTabela) ;

        JTable tabela = setTabela (colunasTabela , modelo , 300); // 2000

        JTableHeader cabecalho = setCabecalhoHeader(tabela);

        JScrollPane scrollPanetabela = new JScrollPane(tabela);
        scrollPanetabela.getVerticalScrollBar().setBackground(Color.black);
        scrollPanetabela.getHorizontalScrollBar().setBackground(Color.black);
        scrollPanetabela.setBackground(Color.black);

        painel.add(scrollPanetabela); // <-------------


// 		=================================================================

        janela.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width =  janela.getWidth() ;
                int height = janela.getHeight();
                scrollPane.setBounds(250, 0, width - 250, height - 35 );
//    	        ==================================================
                seletorPesquisa.setBounds(25 , 300, width - 300  , 20 );
                ImputDadosAgente.setBounds(25 , 380, width - 300  , 20 );
                separador1.setBounds(0 , 510 , width   , 20 );
                botaoPesquisa.setBounds(25 , 450, width - 300  , 30);
                botaoAddAgente.setBounds(width - 510 , 530  , 250   , 30 );
                scrollPanetabela.setBounds(45, 600, width - 350 , 400 );
//    	        ==================================================

                janela.revalidate(); // obrigatorio para atualizar a tela não tirar
                janela.repaint(); // obrigatorio para atualizar a tela não tirar
            }
        });
//--------------------------------------------------------------------------
        painel.add(titulo);
        painel.add(lb1);
        painel.add(seletorPesquisa);
        painel.add(lb2);
        painel.add(tipoAcesso);
        painel.add(lb01);
        painel.add(ImputDadosAgente);
        painel.add(separador1);
        painel.add(botaoPesquisa);
        painel.add(botaoAddAgente);
        //--------------------------------------------------------------------------

        botaoPesquisa.addActionListener(e ->{
            String login , id1 , nome , ativo , email , data_criacao , editar ;
            int aux = 0 ;

            String valorInputSeletorPesquisa = ImputDadosAgente.getText();
            String tipoDaPesquisa = (String)seletorPesquisa.getSelectedItem();
            int valorInputSeletorPesquisaInt = 0 ; // <-----------

            String retornoAPI ;
            String [] retorno_vt_api = {} ;
            String [] vt2 = new String[0];

//________________________________________________________________________________________________________________________________________

            if(tipoDaPesquisa.equals(vetor[0])) { // Todos agentes
                try {

                    String[][] retornoPesquisa = api.pesquisaTodosAgentes();
                    modelo.setRowCount(0);
                    int cont = 0 ;
                    for (int i = 0; i < retornoPesquisa.length; i++) {
                        login = retornoPesquisa[i][0];
                        id1 = retornoPesquisa[i][1];
                        nome = retornoPesquisa[i][2];
                        ativo = retornoPesquisa[i][3];
                        email = retornoPesquisa[i][4];
                        data_criacao = verificarGeral.dataFormatada(retornoPesquisa[i][5]);  // dataFormatada
                        editar = "EDITAR";
                        modelo.addRow(new Object[]{login, id1, nome, ativo, email, data_criacao, editar});
                        cont = cont + 1 ;
                    }

                    tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 50)); // 2000

                }catch (Exception err ){
                    JOptionPane.showMessageDialog(null , "Erro na execução da pesquisa "+err , "" , JOptionPane.CLOSED_OPTION);
                }
            }
//________________________________________________________________________________________________________________________________________
            if(tipoDaPesquisa.equals(vetor[1]) && valorInputSeletorPesquisa.length() > 0) { // Pesquisa por nome
                try {

                    String[][] retornoPesquisa = api.pesquisaNomeAgente(valorInputSeletorPesquisa) ;
                    modelo.setRowCount(0);
                    int cont = 0 ;
                    for (int i = 0; i < retornoPesquisa.length; i++) {
                        login = retornoPesquisa[i][0];
                        id1 = retornoPesquisa[i][1];
                        nome = retornoPesquisa[i][2];
                        ativo = retornoPesquisa[i][3];
                        email = retornoPesquisa[i][4];
                        data_criacao = retornoPesquisa[i][5];
                        editar = "EDITAR";
                        modelo.addRow(new Object[]{login, id1, nome, ativo, email, data_criacao, editar});
                        cont = cont + 1 ;
                    }

                    tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 40)); // 2000

                }catch(Exception err ) {
                    JOptionPane.showMessageDialog(null , "Usuário não identificado  "+err , "", JOptionPane.CLOSED_OPTION);
                }
            }
//________________________________________________________________________________________________________________________________________
            if(tipoDaPesquisa.equals(vetor[3]) && valorInputSeletorPesquisa.length() > 0){// PESQUISA POR ID

                try {
                    int cont = 0 ;
                    int valorInputSeletorPesquisaINT = Integer.parseInt(valorInputSeletorPesquisa);
                     String[][] retornoPesquisa = api.pesquisaIdAgente(valorInputSeletorPesquisaINT) ;
                    modelo.setRowCount(0);
                    for (int i = 0; i < retornoPesquisa.length; i++) {
                        login = retornoPesquisa[i][0];
                        id1 = retornoPesquisa[i][1];
                        nome = retornoPesquisa[i][2];
                        ativo = retornoPesquisa[i][3];
                        email = retornoPesquisa[i][4];
                        data_criacao = retornoPesquisa[i][5];
                        editar = "EDITAR";
                        modelo.addRow(new Object[]{login, id1, nome, ativo, email, data_criacao, editar});
                        cont = cont + 1 ;
                    }
                    tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 40)); // 2000


                }catch(Exception err ) {
                    JOptionPane.showMessageDialog(null , "Usuário não identificado  "+err , "", JOptionPane.CLOSED_OPTION);
                }


            }

//________________________________________________________________________________________________________________________________________
            if(tipoDaPesquisa.equals(vetor[2]) && valorInputSeletorPesquisa.length() > 0){ //  PESQUISA LOGIN

                try {

                    int cont = 0 ;
                    String[][] retornoPesquisa = api.pesquisarLoginAgente(valorInputSeletorPesquisa) ;
                    modelo.setRowCount(0);
                    for (int i = 0; i < retornoPesquisa.length; i++) {
                        login = retornoPesquisa[i][0];
                        id1 = retornoPesquisa[i][1];
                        nome = retornoPesquisa[i][2];
                        ativo = retornoPesquisa[i][3];
                        email = retornoPesquisa[i][4];
                        data_criacao = retornoPesquisa[i][5];
                        editar = "EDITAR";
                        modelo.addRow(new Object[]{login, id1, nome, ativo, email, data_criacao, editar});
                        cont = cont + 1 ;
                     }

                    tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width,  cont * 40)); // 2000


                }catch(Exception err ) {
                    JOptionPane.showMessageDialog(null , "Usuário não identificado  "+err , "", JOptionPane.CLOSED_OPTION);
                }




            }

//________________________________________________________________________________________________________________________________________
            else {

                if((tipoDaPesquisa.equals(vetor[1]) || tipoDaPesquisa.equals(vetor[2]) || tipoDaPesquisa.equals(vetor[3])) &&   valorInputSeletorPesquisa.isEmpty()) {
                    JOptionPane.showMessageDialog(null,"Atenção o campo do tipo da pesquisa deve ser preenchido ! ","Aviso", JOptionPane.ERROR_MESSAGE);

                }
            }
//________________________________________________________________________________________________________________________________________

            try {

                tabela.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        int row = tabela.rowAtPoint(e.getPoint());
                        int column = tabela.columnAtPoint(e.getPoint());

                        if (column == 6) {
                            String cliqueTabela = tabela.getValueAt(row, 1).toString();
                            int idstr = Integer.parseInt(cliqueTabela);
                            int idColetado = Integer.parseInt(tabela.getValueAt(row, 1).toString()); // Coluna "ID"
                            modelo.setRowCount(0);

                            JOptionPane.showMessageDialog(null ,"Acessando dados do usuário de ID :  "+idColetado, "", JOptionPane.CLOSED_OPTION);
                            String[] dadosAgenteExistente =  janelaJP.dadosAgenteExistente(idColetado);
                        }
                    }


                });
            }catch(Exception err ) {
                System.out.println(">> erro "+err);

            }


        });
//________________________________________________________________________________________________________________________________________
        botaoAddAgente.addActionListener(e->{
            String[] dadosAddNovoAcesso =  janelaJP.addAgente();
            String retornoDadosCriarAcesso = api.addAcessos(dadosAddNovoAcesso);
            retornoDadosCriarAcesso = verificarGeral.getCaracteriosManipulados (retornoDadosCriarAcesso);

            if(dadosAddNovoAcesso.length > 0 ) {
                JOptionPane.showMessageDialog(null ,""+retornoDadosCriarAcesso, "" , JOptionPane.DEFAULT_OPTION );

            }



        });

        return scrollPane ;
    }





}
