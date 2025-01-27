package TaredasDefinidas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.net.URL;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Componentes {

//    -------------------------------------  FRAMES e PAINEIS  -----------------------------------------

    public JPanel setPanel(int width, int height, Color cor) {
        JPanel painel = new JPanel();
        painel.setPreferredSize(new Dimension(width, height));
        painel.setBackground(cor);
        painel.setLayout(null);
        return painel;
    }

    public JFrame janela(int largura, int altura, String nomeJenela, Boolean redimensionadaUser) {
        JFrame janela = new JFrame(nomeJenela);
        janela.setSize(largura, altura);
        janela.setResizable(redimensionadaUser);
        janela.setVisible(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        return janela;
    }

    public JScrollPane setRolagem(JPanel painel) {
        JScrollPane painelRolagem = new JScrollPane(painel);
        painelRolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        painelRolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        return painelRolagem;
    }
//    -------------------------------------  JLABELs  -----------------------------------------

    public JLabel setLabel(String textoLabel, int tamanhoLetra, Color cor) {
        JLabel label = new JLabel(textoLabel);
        Font fontLetra = label.getFont().deriveFont(Font.BOLD, tamanhoLetra);
        label.setFont(fontLetra);
        label.setForeground(cor);
        return label;
    }

    public JLabel getTituloPainel(String texto ) {
        JLabel titulo = setLabel (texto , 35 , Color.white) ;
        titulo.setBounds(260, 120 , 400 , 60);
        return titulo  ;
    }

    public JLabel fontPadrao (String texto){
        return setLabel (texto , 15 , Color.white)  ;
    }

    public JComboBox<String> setSeletorString(String vetor[]) {
        return new JComboBox<>(vetor);
    }

    //    -------------------------------------  JLABELs  imagens -----------------------------------------




    public JLabel setImage(String caminhoImage) {
        ImageIcon imagem = new ImageIcon(caminhoImage);
        JLabel LabelImagem = new JLabel();
        LabelImagem.setIcon(imagem);

        return LabelImagem;
    }

    public JLabel setImageDimensao(String caminhoImage, int largura, int altura) {
        ImageIcon imagem = new ImageIcon(caminhoImage);
//        ImageIcon imagem = new ImageIcon(new URL(caminhoImage));

        Image tamanho = imagem.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        ImageIcon imagemRedimensionada = new ImageIcon(tamanho);
        JLabel labelImagem = new JLabel();
        labelImagem.setIcon(imagemRedimensionada);
        return labelImagem;
    }

    public JLabel setImageDimensaoCircular(String caminhoImage, int largura, int altura) {
        ImageIcon imagem = new ImageIcon(caminhoImage);
        Image imgOriginal = imagem.getImage();



        BufferedImage imagemRedonda = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imagemRedonda.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(new Ellipse2D.Float(0, 0, largura, altura));
        g2.drawImage(imgOriginal, 0, 0, largura, altura, null);
        g2.dispose();
        ImageIcon imagemRedimensionada = new ImageIcon(imagemRedonda);
        JLabel labelImagem = new JLabel();
        labelImagem.setIcon(imagemRedimensionada);

        return labelImagem;
    }

    public JLabel setImageDimensaoCircularURL(String caminhoImage, int largura, int altura) {

        try{
            ImageIcon imagem = new ImageIcon(new URL(caminhoImage));

            Image imgOriginal = imagem.getImage();

            BufferedImage imagemRedonda = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = imagemRedonda.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setClip(new Ellipse2D.Float(0, 0, largura, altura));
            g2.drawImage(imgOriginal, 0, 0, largura, altura, null);
            g2.dispose();
            ImageIcon imagemRedimensionada = new ImageIcon(imagemRedonda);
            JLabel labelImagem = new JLabel();
            labelImagem.setIcon(imagemRedimensionada);

            return labelImagem;
        }catch (Exception err){
            return null ;
        }

    }
    //  ------------------------------------------  IMPUTS -------------------------------------------------
    public JTextField setJTextFIld(int tamanho) {
        return new JTextField(tamanho);
    }

    public JPasswordField setJPass(int tamanho) {
        return new JPasswordField(tamanho);
    }

    public JTextArea setAreaTexto (int rows , int columns ){
        JTextArea textoArea = new JTextArea(rows, columns);
        textoArea.setLineWrap(true);
        textoArea.setWrapStyleWord(true);
        return textoArea ;
    }

    public JComboBox seletorString (String[] opcao ){
        JComboBox <String> comboBox = new JComboBox<>(opcao) ;
        return comboBox ;
    }

    public JComboBox seletorInt (int [] opcao ){
        Integer[] opcoes = new Integer[opcao.length];
        JComboBox<Integer> comboBox = new JComboBox<>(opcoes);
        return comboBox ;
    }
//  ------------------------------------------    butao -------------------------------------------------

    public JButton setBoutton(String descricao, Color cor) {
        JButton botao = new JButton(descricao);
        botao.setBackground(cor);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setFocusable(false);
        return botao;
    }
//  ------------------------------------------    SEPARADORES  -------------------------------------------------

    public JSeparator setSeparador() {
        JSeparator separador = new JSeparator();
        separador.setPreferredSize(new Dimension(500, 5));
        return separador;
    }


    public JSeparator setSeparadorHorizontal(int tamanho ) {
        JSeparator separador = new JSeparator(SwingConstants.VERTICAL);
        separador.setPreferredSize(new Dimension(tamanho, 700)); // Largura 500, altura 5
        return separador;
    }


//  ------------------------------------------    TABELA   -------------------------------------------------
    // seguir ordem 1 (setModelo) / 2 (setTabela) /3 (JTableHeader) //


    public DefaultTableModel setModelo (String [] colunasTabela) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for(int i = 0 ; i < colunasTabela.length ; i++) {
            modelo.addColumn(colunasTabela[i]);
        }


        return modelo ;
    }

    public JTable setTabela (String colunasTabela[] , DefaultTableModel modelo  , int valorRolagemTabela){

        JTable tabela = new JTable(modelo);
        tabela.setPreferredScrollableViewportSize(new Dimension(700, 100 ));

        tabela.setBackground(Color.black);
        tabela.setForeground(Color.white);
        tabela.setFont(new Font("Arial", Font.BOLD, 16 ));
        tabela.setPreferredSize(new Dimension(tabela.getPreferredSize().width, valorRolagemTabela)); // 2000
        tabela.setRowHeight(30);
        tabela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tabela.getTableHeader().setReorderingAllowed(false);


        return tabela ;
    }

    public JTableHeader setCabecalhoHeader (JTable tabela) {

        JTableHeader cabecalho = tabela.getTableHeader();
        cabecalho.setBackground(Color.black);
        cabecalho.setFont(new Font("Arial", Font.BOLD, 16));
        cabecalho.setForeground(Color.white);
        cabecalho.setPreferredSize(new Dimension(cabecalho.getPreferredSize().width, 100)); // Ajusta a altura

        return cabecalho ;
    }


}
