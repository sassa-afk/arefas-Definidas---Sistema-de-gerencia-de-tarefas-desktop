package TaredasDefinidas;

import javax.swing.*;


public class Main extends Componentes {

    public static JFrame janela ;
    public static JScrollPane  janelaRolagem ;
    public static int getHeight ;
    public static int getWidth ;
    public static int idUsuarioLogado ;
    public static boolean authGeral = false  ;
    public static String token ;



    public static void  main (String[] args ) {
//		 Main mainInstance = new Main();
//		//=============================================================
//
//        Thread contadorThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//         		    SwingUtilities.invokeLater(Main::login);
//
//            }
//        });
//       contadorThread.start();
//		//=============================================================
//
        SwingUtilities.invokeLater(Main::login);
//        SwingUtilities.invokeLater(Main::framePrincipal);




    }


    private static void login () {
        Login login = new Login();
        janela = login.getLogin();

    }


    private static void framePrincipal() {
        JanelaGerencia janelaPosAuth = new JanelaGerencia();
        janela = janelaPosAuth.janelaPosLogin( );



    }

    // ------------------------------------------------------

    public void varIdUsuario(int id , boolean auth , String tk) {
        idUsuarioLogado = id ;
        authGeral = auth ;
        token = tk ;

        if(auth == true && idUsuarioLogado > 0) {
            janela.dispose();
            SwingUtilities.invokeLater(Main::framePrincipal);


        }


    }

    //_________________________
    public int retornoIdSQL () {
        return idUsuarioLogado ;
    };
    public boolean retornoAuth() {
        return authGeral ;
    }
    public String token() {
        return token ;
    }

    //_________________________

}
