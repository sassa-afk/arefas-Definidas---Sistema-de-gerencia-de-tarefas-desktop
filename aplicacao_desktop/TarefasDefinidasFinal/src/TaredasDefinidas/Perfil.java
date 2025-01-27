package TaredasDefinidas;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;



public class Perfil extends Componentes {
    Apis api = new Apis();
    VerificacaoGeral verificacaoGeral = new VerificacaoGeral();

    public JScrollPane p1(JFrame janela , int id ) {

        String caminhoFotoADM = api.getCaminhoFoto(id) ;
        caminhoFotoADM = caminhoFotoADM + "&timestamp=" + java.util.UUID.randomUUID();

        JanelasJOP janelaJP = new JanelasJOP() ;
        String [] infoAcesso =  api.dadosUserLogado(id) ;


//____________________________________________________________________________
//____________________________________________________________________________


        JPanel painelConteudo =  setPanel(0 , 0 , Color.black) ;

        painelConteudo.setPreferredSize(new Dimension(100, 1000 ));

        JLabel titulo = getTituloPainel("Meu perfil");



        JLabel foto  = setImageDimensaoCircularURL( caminhoFotoADM , 400 , 400 );

        foto.setBounds(150, 150 , 600, 600);

        JSeparator traco1 = setSeparadorHorizontal(0);
        traco1.setBounds(610, 250 , 100, 400);

        JLabel lb1 = fontPadrao("Nome : ");
        lb1.setBounds(640, 220 , 600, 100);
        lb1.setText("Nome : "+infoAcesso[1]+" "+infoAcesso[6]);

        JLabel lb2 = fontPadrao("Cargo : ");
        lb2.setBounds(640, 270 , 600, 100);
        lb2.setText("Cargo : "+infoAcesso[5]);


        JLabel lb3 = fontPadrao("E-mail: ");
        lb3.setBounds(640, 320 , 600, 100);
        lb3.setText("E-mail : "+infoAcesso[2]);


        JLabel lb4 = fontPadrao("Telefone : ");
        lb4.setBounds(640, 370 , 600, 100);
        lb4.setText("Telefone : "+infoAcesso[4]);


        JLabel lb5 = fontPadrao(" Acesso criado :  ");
        lb5.setBounds(640, 420 , 600, 100);
        lb5.setText("Acesso criado : "+verificacaoGeral.dataFormatada(infoAcesso[3]));



        JButton bt1 = setBoutton("Trocar foto de perfil", Color.white  ) ;
        bt1.setBounds(150, 700, 450, 50);

        JButton bt2 = setBoutton("Editar meus dados de contato e senha", Color.white) ;
        bt2.setBounds(150, 760, 450, 50);




        painelConteudo.add(titulo);
        painelConteudo.add(foto);
        painelConteudo.add(traco1);
        painelConteudo.add(lb1);
        painelConteudo.add(lb2);
        painelConteudo.add(lb3);
        painelConteudo.add(lb4);
        painelConteudo.add(lb5);
        painelConteudo.add(bt1);
        painelConteudo.add(bt2);

        JScrollPane scrollPane = new JScrollPane(painelConteudo);



        janela.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width =  janela.getWidth() ;
                int height = janela.getHeight();
                scrollPane.setBounds(250, 0, width - 250, height - 35 );

                janela.revalidate();
                janela.repaint();
            }
        });

        bt2.addActionListener(e ->{

            String[] dadosAgenteExistente =  janelaJP.dadosAgenteExistente(id);



            painelConteudo.remove(titulo);
             painelConteudo.remove(traco1);
            painelConteudo.remove(lb1);
            painelConteudo.remove(lb2);
            painelConteudo.remove(lb3);
            painelConteudo.remove(lb4);
            painelConteudo.remove(lb5);
            painelConteudo.remove(bt1);
            painelConteudo.remove(bt2);

            janela.revalidate();

            String [] infoAcessoPosAtualizacao =  api.dadosUserLogado(id) ;


            lb1.setText("Nome : "+infoAcessoPosAtualizacao[1]+" "+infoAcessoPosAtualizacao[6]);
            lb2.setText("Cargo : "+infoAcessoPosAtualizacao[5]);
            lb3.setText("E-mail : "+infoAcessoPosAtualizacao[2]);
            lb4.setText("Telefone : "+infoAcessoPosAtualizacao[4]);
            lb5.setText("Acesso criado : "+ verificacaoGeral.dataFormatada(infoAcessoPosAtualizacao[3]));




            painelConteudo.add(titulo);
//            painelConteudo.add(foto);
            painelConteudo.add(traco1);
            painelConteudo.add(lb1);
            painelConteudo.add(lb2);
            painelConteudo.add(lb3);
            painelConteudo.add(lb4);
            painelConteudo.add(lb5);
            painelConteudo.add(bt1);
            painelConteudo.add(bt2);

            janela.repaint();

        });
        bt1.addActionListener(err -> {

            FileDialog janelaDialogoUpArq = new FileDialog(janela, "Escolha uma imagem com extensões png, jpg, jpeg", FileDialog.LOAD);
            janelaDialogoUpArq.setVisible(true);

            String filePath = janelaDialogoUpArq.getDirectory() + janelaDialogoUpArq.getFile();

            if (filePath != null && new File(filePath).exists()) {

                String retApi = api.editarEnviarArquivo(id, filePath);
                JOptionPane.showConfirmDialog(null, retApi, "", JOptionPane.DEFAULT_OPTION);
            }
            else {
                 JOptionPane.showConfirmDialog(null, "Nenhum arquivo selecionado ou arquivo inválido.", "", JOptionPane.DEFAULT_OPTION);
            }

            painelConteudo.removeAll();
            janela.revalidate();

            String caminhoFotoADM2 = api.getCaminhoFoto(id) ;
            caminhoFotoADM2 = caminhoFotoADM2 + "&timestamp=" + java.util.UUID.randomUUID();

            JLabel fotoPosAtualizacao = setImageDimensaoCircularURL( caminhoFotoADM2 , 400 , 400 );
            fotoPosAtualizacao.setBounds(150, 150 , 600, 600);

            painelConteudo.add(titulo);
            painelConteudo.add(fotoPosAtualizacao);
            painelConteudo.add(traco1);
            painelConteudo.add(lb1);
            painelConteudo.add(lb2);
            painelConteudo.add(lb3);
            painelConteudo.add(lb4);
            painelConteudo.add(lb5);
            painelConteudo.add(bt1);
            painelConteudo.add(bt2);

            janela.repaint();

        });




        return scrollPane;
    }

}

