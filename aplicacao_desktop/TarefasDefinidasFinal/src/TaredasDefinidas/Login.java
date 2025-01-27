package TaredasDefinidas;

import javax.swing.*;
import java.awt.*;


public class Login extends Componentes {

    public JFrame getLogin ( ){

         Main instanciaMain = new Main(); // 1
        VerificacaoGeral validacaoGeral = new VerificacaoGeral(); // 2
        Apis api = new Apis () ;

        // ============= componentes de tela

        Color corLabel = Color.white ;

        JLabel img = setImageDimensao( "src/TaredasDefinidas/img6.jpg", 700 , 800);
        img.setBounds(0,0 ,800 , 800 );

        JLabel labelTitulo = setLabel( "Novas Metas" , 45  ,corLabel );
        labelTitulo.setBounds(850 , 150 , 500 , 100 ) ;

        JLabel labelUser = setLabel("Usuário" , 15 , corLabel) ;
        labelUser.setBounds(770 , 300 , 400 , 30 );

        JTextField inputLogin = setJTextFIld(1);
        inputLogin.setBounds(850 , 300 , 400, 30 );


        JLabel labelsenha = setLabel("Senha" , 15 , corLabel) ;
        labelsenha.setBounds(770 , 350 , 400 , 30 );
        JPasswordField inputsenha = setJPass(1);
        inputsenha.setBounds(850 , 350 , 400, 30 );

        JButton botao = setBoutton ("Acessar" , new Color(173, 216, 230));
        botao.setBounds(880 , 400 , 300, 30 );

// 		JPanel painel = setPanel(0, 0, new Color(255, 200, 100)); // Laranja claro
// 		JPanel painel = setPanel(0, 0, new Color(255, 182, 193)); // Rosa claro
// 		JPanel painel = setPanel(0, 0, new Color(255, 192, 203)); // Rosa mais claro
//		JPanel painel = setPanel(0, 0, new Color(173, 216, 230)); // Azul claro
//        JPanel painel = setPanel(0, 0, new Color(255, 228, 240)); // Rosa muito claro
        JPanel painel = setPanel(0, 0, Color.black); // Azul claro

        painel.add(labelTitulo);
        painel.add(img);
        painel.add(inputLogin);
        painel.add(labelUser);

        painel.add(labelsenha);
        painel.add(inputsenha);

        painel.add(botao);


        JFrame  janela = janela ( 1300 ,800, "Painel de login" , false );
        janela.add(painel);

        // =============================  Acoes do botao ================================

        botao.addActionListener(e -> {

            String valorCampoUsuario = inputLogin.getText();

            char[] valorCampoSenhaChar = inputsenha.getPassword();
            String valorCampoSenha = new String(valorCampoSenhaChar);

            Boolean validaUser = validacaoGeral.verImput(valorCampoUsuario, "usuário");
            Boolean validaSenha = validacaoGeral.verImput(valorCampoSenha, "senha");

            if (inputLogin.getText().isEmpty() || valorCampoSenha.isEmpty()) {
                JOptionPane.showMessageDialog(null, "O campo de usuário ou senha está sem dados ", "", JOptionPane.ERROR_MESSAGE);

            } else {



            try {

                if (validaUser == true && validaSenha == true) {

                    String[] dadosResApi = api.authAcesso(valorCampoUsuario, valorCampoSenha);
                    int id = Integer.parseInt(dadosResApi[2]);

                    if (dadosResApi[0].equals("true") && id > 0) {
                        instanciaMain.varIdUsuario(id, true, dadosResApi[1]);
                    }

                    if (dadosResApi[0].equals("false")) {
                        JOptionPane.showMessageDialog(null, "Acesso desativado , valide suas credenciais com outros usuários ou adms da aplicação ", "", JOptionPane.ERROR_MESSAGE);
                    }

                }
            } catch (Exception err) {

                String[] dadosResApi = api.authAcesso(inputLogin.getText(), valorCampoSenha);
                if (dadosResApi[0].equals("503") || dadosResApi[1].equals("500")) {
                    JOptionPane.showMessageDialog(null, ">> Erro de conexão com o servidor ", "", JOptionPane.ERROR_MESSAGE);
                }

                if (dadosResApi[0].equals("401")) {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos ", "", JOptionPane.ERROR_MESSAGE);
                }

            }
             }
        });;


        //================================================================================
        return janela;
    }

}