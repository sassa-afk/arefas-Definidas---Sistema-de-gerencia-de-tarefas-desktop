package TaredasDefinidas;

 import javax.imageio.ImageIO;
 import javax.swing.*;

import java.awt.*;
 import java.awt.event.ActionListener;
 import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
 import java.awt.image.BufferedImage;
 import java.net.URL;
 import java.util.*;

public class JanelaGerencia extends Componentes {
    Main instanciaMain = new Main();
    Agente painelAge = new Agente();
    Perfil perfil = new Perfil();
    TarefasGeral tarefas = new TarefasGeral() ;
    Apis api = new Apis();


    public JFrame janelaPosLogin() {


         // <================
        int id = instanciaMain.retornoIdSQL();
        String caminhoFotoADM = api.getCaminhoFoto(id) ;
        String foto = caminhoFotoADM + "&timestamp=" + java.util.UUID.randomUUID();


        Color corLetraBtnMenu = Color.white;
        Color fundoBtnMenu = null;

        JPanel painelLateral = setPanel(0, 0, Color.black);
        JPanel panielSuperior = setPanel(0, 0, Color.black);


        JLabel imagemPerfil = setImageDimensaoCircularURL(foto, 220 , 220 ); // <---------------------------
        JSeparator linha1 = setSeparador();

        String [] dadosUser  = api.dadosUserExistente(id);

        JLabel label1 = setLabel("Olá, "+dadosUser[2]+" "+dadosUser[6], 15, Color.white);
        JButton btn1 = setBoutton("Perfil", fundoBtnMenu);
        JButton btn2 = setBoutton("Acessos", fundoBtnMenu);
        JButton btn3 = setBoutton("Tarefas", fundoBtnMenu);

        JFrame janela = janela(1600, 1800, "Novas Metas", true);
        janela.setBackground(Color.black);
        janela.setLayout(null);

        janela.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                int getHeight = janela.getHeight();
                int getWidth = janela.getWidth();


                imagemPerfil.setBounds(10, 50, 350, 350);

                linha1.setBounds(0, 400, 400, 10);

                label1.setBounds(20, 450, 200, 15);

                btn1.setBounds(0, 500, 250, 50);
                extencaoFonteColorBotao(btn1, "Ariel", corLetraBtnMenu);

                btn2.setBounds(0, 560, 250, 50);
                extencaoFonteColorBotao(btn2, "Ariel", corLetraBtnMenu);

                btn3.setBounds(0, 620, 250, 50);
                extencaoFonteColorBotao(btn3, "Ariel", corLetraBtnMenu);

                painelLateral.add(linha1);
                painelLateral.add(label1);
                painelLateral.add(btn1);
                painelLateral.add(btn2);
                painelLateral.add(btn3);


                painelLateral.setBounds(0, 0, 250, getHeight);
                panielSuperior.setBounds(0, 0, getWidth, 102);
                painelLateral.add(imagemPerfil);

                janela.revalidate();
                janela.repaint();

            }
        });

        // ---------------------------------------------------------------------
        JScrollPane painelRolagemAgente = painelAge.AgentePainel1(janela  ) ;
        JScrollPane painelRolagemPerfil = perfil.p1(janela , id  );
        JScrollPane painelRolagemTarefas = tarefas.scrPanne(janela , id );


        // ---------------------------------------------------------------------

        janela.add(painelRolagemPerfil);
        janela.add(painelLateral);
//        String urlImagem = "http://192.168.0.114:3000/fotoPerfil?caminho_foto=" + id + "&timestamp=" +  java.util.UUID.randomUUID();


        btn1.addActionListener(er -> {
            // ==========================================
             try {
                painelLateral.removeAll();
                String urlImagem = api.getCaminhoFoto(id) + "&timestamp=" +  java.util.UUID.randomUUID();
                JLabel novaImagemPerfil = setImageDimensaoCircularURL(urlImagem, 220, 220);
                novaImagemPerfil.setBounds(10, 50, 350, 350);

                painelLateral.add(novaImagemPerfil);
                painelLateral.add(linha1);
                painelLateral.add(label1);
                painelLateral.add(btn1);
                painelLateral.add(btn2);
                painelLateral.add(btn3);

              } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem. Verifique a conexão ou a URL.");
            }
            // ==========================================

            painelLateral.revalidate();
            painelLateral.repaint();

            janela.remove(painelRolagemTarefas);
            janela.remove(painelRolagemAgente);
            janela.remove(painelRolagemPerfil);
            janela.add(painelRolagemPerfil);

            janela.revalidate();
            janela.repaint();

        });

        btn2.addActionListener(e ->{
            // ==========================================
            try{
                painelLateral.removeAll();
                String urlImagem = api.getCaminhoFoto(id) + "&timestamp=" +  java.util.UUID.randomUUID();
//                String urlImagem = api.getCaminhoFoto(id)  + java.util.UUID.randomUUID();

                JLabel novaImagemPerfil = setImageDimensaoCircularURL(urlImagem, 220, 220);
                novaImagemPerfil.setBounds(10, 50, 350, 350);

                painelLateral.add(novaImagemPerfil);
                painelLateral.add(linha1);
                painelLateral.add(label1);
                painelLateral.add(btn1);
                painelLateral.add(btn2);
                painelLateral.add(btn3);


            } catch (Exception er) {
                er.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem. Verifique a conexão ou a URL.");
            }
            // ==========================================

            janela.remove(painelLateral);
            painelLateral.remove(imagemPerfil);
            String urlImagem = api.getCaminhoFoto(id) + "&timestamp=" +  java.util.UUID.randomUUID();

            JLabel imagemPerfil2 = atualizarImg (urlImagem, 220, 220); // <---------------------------
            imagemPerfil2.setBounds(10, 50, 350, 350);
            painelLateral.add(imagemPerfil2);
            janela.add(painelLateral);

            janela.remove(painelRolagemTarefas); // <+++++++++++++++++++++++++
            janela.remove(painelRolagemAgente);
            janela.remove(painelRolagemPerfil);

            janela.add(painelRolagemAgente);

            janela.revalidate();
            janela.repaint();


        });

        btn3.addActionListener(e ->{


            // ==========================================
            try{

                painelLateral.removeAll();

                String urlImagem = api.getCaminhoFoto(id) + "&timestamp=" +  java.util.UUID.randomUUID();
                JLabel novaImagemPerfil = setImageDimensaoCircularURL(urlImagem, 220, 220);
                novaImagemPerfil.setBounds(10, 50, 350, 350);

                painelLateral.add(novaImagemPerfil);
                painelLateral.add(linha1);
                painelLateral.add(label1);
                painelLateral.add(btn1);
                painelLateral.add(btn2);
                painelLateral.add(btn3);


            } catch (Exception er) {
            er.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar a imagem. Verifique a conexão ou a URL.");
        }

            // ==========================================





            janela.add(painelLateral);

            janela.remove(painelRolagemAgente);
            janela.remove(painelRolagemPerfil);
            janela.add(painelRolagemTarefas);


            janela.revalidate();
            janela.repaint();

        });



        // ---------------------------------------------------------------------

        return janela;
    }

    public JButton extencaoFonteColorBotao(JButton btn, String fonte, Color cor) {
        Font font = new Font(fonte, Font.PLAIN, 20);
        btn.setForeground(cor);
        btn.setFont(font);
        return btn;
    }
    public JLabel atualizarImg (String caminhoFotoADM , int largura , int altura  ){
        JLabel imagemPerfil = setImageDimensaoCircularURL(caminhoFotoADM, largura , altura ); // <---------------------------
        return imagemPerfil ;
    }
}
