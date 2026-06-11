package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import model.Usuario;
import model.Questao;

public class FormMenu extends JFrame { // Herança de JFrame: Torna a classe uma janela gráfica
    private static final long serialVersionUID = 1L;
    // As variáveis usuarioLogado e prontuarioLogado guardam as credenciais do
    // usuário atual
    private String usuarioLogado;
    private String prontuarioLogado;
    // O vetor perguntas armazenará os dados vindos do arquivo .DAT, enquanto as
    // variáveis
    // totalV, totalA, totalC e totalD acumularão os pontos de cada sistema
    // representacional
    // (Visual, Auditivo, Cinestésico, Digital)
    private Questao[] perguntas;
    private int totalV = 0, totalA = 0, totalC = 0, totalD = 0;
    // Variáveis de apoio para a FormMenu
    private JPanel painelDireito;
    private int indiceAtual = 0;

    // Método construtor FormMenu: recebe o objeto Usuario
    public FormMenu(Usuario user) {
        this.usuarioLogado = user.getNome();
        this.prontuarioLogado = user.getProntuario();
        // Aqui, o título da janela é customizado dinamicamente para exibir quem está
        // logado
        setTitle("Sistema Representacional" + " [" +
                user.getNome().toUpperCase() + "-" +
                user.getProntuario().toUpperCase() + "] LOGADO");
        setSize(1100, 700); // Definindo o tamanho da janela em pixels
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // setDefaultCloseOperation define o que o sistema operacional deve fazer
        // quando o usuário clicar no botão de fechar (o "X" vermelho ou preto) da
        // janela.
        // Por que é necessária: Por padrão, o Java apenas esconde a janela, mas deixa
        // o programa rodando em segundo plano (consumindo memória RAM e processador).
        // A constante EXIT_ON_CLOSE garante que, ao fechar a janela, todo o processo
        // do programa seja completamente encerrado no computador.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setLocationRelativeTo define a posição onde a janela vai aparecer
        // na tela do computador.
        // Quando passamos o valor null (nulo) como argumento, o Java entende que
        // a janela não deve ser posicionada em relação a nenhum outro componente
        // gráfico,
        // mas sim em relação à tela inteira. O resultado prático é que a janela surge
        // perfeitamente centralizada na tela do usuário, independentemente do tamanho
        // ou da
        // resolução do monitor dele.
        setLocationRelativeTo(null);
        // setLayout define o "Gerenciador de Layout" da janela, ou seja, a regra de
        // como os
        // componentes (menus, botões, painéis) serão organizados e distribuídos dentro
        // dela.
        // O BorderLayout divide o espaço da janela em 5 regiões distintas:
        // Norte (Top), Sul (Bottom), Leste (Right), Oeste (Left) e Centro (Center).
        // Enfim, a tela fica organizada com seus espaços internos (BorderLayout).
        setLayout(new BorderLayout());
        // --- BARRA DE MENU COM ATALHOS ---
        JMenuBar menuBar = new JMenuBar(); // Cria um menu de barras
        // Define os ítens das barras de menu
        JMenuItem menuCadastrar = new JMenuItem("Cadastrar questionário");
        menuCadastrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                ActionEvent.ALT_MASK));
        JMenuItem menuVisualizar = new JMenuItem("Visualizar questionário");
        menuVisualizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                ActionEvent.ALT_MASK));
        JMenuItem menuRealizar = new JMenuItem("Realizar teste");
        menuRealizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
                ActionEvent.ALT_MASK));
        JMenuItem menuSobre = new JMenuItem("Sobre o teste");
        menuSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                ActionEvent.ALT_MASK));
        JMenuItem menuFim = new JMenuItem("Fim");
        menuFim.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        // Agrega os ítens ao menu de barras
        menuBar.add(menuCadastrar);
        menuBar.add(menuRealizar);
        menuBar.add(menuVisualizar);
        menuBar.add(menuSobre);
        menuBar.add(menuFim);
        setJMenuBar(menuBar); // Mostra o menu criado na tela

        /*
         * Sempre que precisarmos de um plano de fundo ou de uma barra lateral temática
         * que se adapte a monitores de tamanhos diferentes (como as resoluções variadas
         * dos computadores do laboratório), estender um JPanel e sobrescrever o método
         * paintComponent é a solução mais profissional e robusta dentro do Java Swing.
         */
        // --- IMAGEM LATERAL DINÂMICA ---
        // 1. Criamos o painel customizado que redesenha a imagem ao esticar a tela
        JPanel painelImagemLateral = new JPanel() {
            private Image imagemOriginal = new ImageIcon("images/SistemasRepresentacionais.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Desenha a imagem ocupando 100% da largura e altura ATUAIS do painel
                g.drawImage(imagemOriginal, 0, 0, getWidth(), getHeight(), this);
            }
        };
        // 2. Definimos a largura fixa que a barra lateral deve ter (ex: 200 pixels)
        // O Java cuidará da altura automaticamente preenchendo a lateral da janela
        painelImagemLateral.setPreferredSize(new Dimension(200, 0));
        // 3. Adicionamos o painel elástico diretamente no lado esquerdo do BorderLayout
        add(painelImagemLateral, BorderLayout.WEST);

        // Criamos outro painel para ocupar o restante da tela à direita da imagem
        // lateral
        painelDireito = new JPanel(new BorderLayout());
        add(painelDireito, BorderLayout.CENTER);
        // Verifica se o usuário NÃO é o MASTER
        if (!usuarioLogado.equalsIgnoreCase("MASTER")) {
            // Esconde os menus administrativos
            menuCadastrar.setVisible(false);
            menuVisualizar.setVisible(false);
            // Inicia o teste automaticamente (como pede o item 4)
            carregarDadosETelaInicial();
        }

    } // Fim do Método construtor FormMenu

    // ==============MÉTODOS AUXILIARES=================
    private void carregarDadosETelaInicial() {
        try {
            // 1. Define a pasta "data" como local do .DAT
            File pastaData = new File("data");
            // 2. Define o arquivo na pasta correta
            File arquivoDAT = new File(pastaData, "TESTE_SISTEMA_REPRESENTACIONAL.DAT");
            // 3. Tenta abrir o arquivo para leitura usando o objeto 'arquivo'
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(arquivoDAT))) {
                perguntas = (Questao[]) in.readObject();
                indiceAtual = 0;
                totalV = 0;
                totalA = 0;
                totalC = 0;
                totalD = 0;
                exibirPerguntaAtual();
            }
        } catch (java.io.FileNotFoundException ex) {
            // Caso o usuário tente realizar o teste sem ter clicado em "Cadastrar", avisa
            JOptionPane.showMessageDialog(this, "Atenção: O arquivo de questões " +
                    "(TESTE_SISTEMA_REPRESENTACIONAL.DAT) ainda não foi gerado.\n" +
                    "Por favor, clique em 'Cadastrar questionário' primeiro.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar " +
                    "TESTE_SISTEMA_REPRESENTACIONAL.DAT!");
            ex.printStackTrace();
        }
    }

    // Exibe instruções, frases e itens para pontuações, além do botão enviar
    private void exibirPerguntaAtual() {
        painelDireito.removeAll(); // Remove tudo que estava neste painel antes
        painelDireito.setLayout(new BorderLayout());
        // Painel que agrupa o conteúdo superior e o grid
        JPanel pPrincipal = new JPanel(new GridBagLayout());
        pPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.weightx = 1.0;
        g.fill = GridBagConstraints.HORIZONTAL;
        // 1. TÍTULO "PERGUNTAS" (Centralizado)
        JLabel lblPerguntas = new JLabel("Perguntas [AINDA EM IMPLEMENTAÇÃO...]",
                SwingConstants.CENTER);
        lblPerguntas.setFont(new Font("Arial", Font.BOLD, 22));
        g.gridy = 0;
        g.anchor = GridBagConstraints.CENTER;
        g.insets = new Insets(0, 0, 10, 0);
        pPrincipal.add(lblPerguntas, g);
        // 2. ORIENTAÇÕES (Alinhadas à Esquerda)
        JLabel orientacoes = new JLabel("<html><body style='width: 650px;'>" +
                "Para cada pergunta temos 04 afirmações e cada uma deve receber uma pontuação" +
                "diferente.<br>" +
                "Quanto mais alta a pontuação que você escolher significa que melhor representa" +
                "você.<br>" +
                "Se a pontuação for mais baixa significa que você não se identifica tanto com" +
                "aquela afirmação.<br>" +
                "Desta forma, escolha de 1 a 4 pontos sem repetir nenhum número dentro de cada" +
                "pergunta." +
                "</body></html>");
        orientacoes.setFont(new Font("Arial", Font.PLAIN, 12));
        g.gridy = 1;
        g.anchor = GridBagConstraints.WEST; // alinha à esquerda
        g.insets = new Insets(0, 0, 15, 0);
        pPrincipal.add(orientacoes, g);
        // Coloca o pPrincipal no centro do painelDireito
        painelDireito.add(pPrincipal, BorderLayout.CENTER);
        // O comando revalidate() avisa ao gerenciador de layout do Java que a estrutura
        // interna daquele painel mudou (por exemplo, quando você removeu os componentes
        // antigos e adicionou novos).
        // Ele força o Java a recalcular as posições e os tamanhos de todos os novos
        // elementos
        // dentro do painel para garantir que nada fique esmagado, sobreposto ou fora do
        // lugar. É como se ele refizesse a "planta baixa" da interface antes de
        // construir.
        painelDireito.revalidate();
        painelDireito.repaint(); // Redesenha o painelDireito na tela
    }
}// Fim da classe FormMenu