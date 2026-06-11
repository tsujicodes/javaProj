package view;

import model.Usuario; // Para a classe Usuario

import javax.swing.*;
/* O asterisco (*) significa que você está importando todos os componentes da biblioteca
Swing de uma só vez.
• Para que serve: Ele libera o uso de componentes como JButton (o botão Entrar),
JTextField (onde digita o nome), JLabel (os textos e imagens) e o próprio JFrame
(a janela principal).
• É o "pacote básico de peças" para montar qualquer tela.*/
import javax.swing.border.EmptyBorder;
/* Este é bem específico para o design e o acabamento visual.
• Para que serve: O EmptyBorder cria uma "margem invisível" ao redor dos
componentes. Sabe aquele espaço entre o texto e a borda da janela que impede que
tudo fique "grudado" nos cantos? É ele quem faz.
• Por que não o *? Às vezes, subpacotes como o .border precisam ser chamados
explicitamente para o Java organizar melhor a memória. */
import java.awt.*;

/* O AWT (Abstract Window Toolkit) é o "avô" do Swing. Embora o Swing seja mais moderno,
ele ainda depende do AWT para coisas fundamentais.
• Para que serve: Você o usa principalmente para o Layout. No seu código, quando
você diz BorderLayout.WEST (para colocar a imagem na esquerda) ou
GridBagConstraints (para alinhar o formulário de login), você está usando
ferramentas que moram dentro do pacote java.awt.
• Cores e Fontes: Se você quiser mudar a cor de um texto ou o tamanho da letra,
também precisará do AWT. */

public class FormLogin extends JFrame {
    // Atributos: definição dos componentes da tela
    private JTextField txtNome;
    private JTextField txtProntuario;
    private JButton btnEntrar;

    // Método construtor
    public FormLogin() {
        setTitle("Login - Perfil Representacional"); // Define o título da tela
        /*
         * Necessário para o programa encerrar completamente o processo do programa
         * e liberar a memória do computador
         * Se você não colocar o comando a seguir, algo muito curioso (e irritante)
         * acontece:
         * o usuário clica no "X" para fechar a janela, a janela some da tela, mas o
         * programa
         * continua rodando "escondido" na memória do computador.
         */
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300); // Define a largura e altura da tela em pixels
        setLocationRelativeTo(null); // Posiciona a janela no centro da tela
        // Carrega a imagem do ícone do sistema
        setIconImage(new ImageIcon("images/iconsistem.png").getImage());
        /*
         * Painel principal com borda (margem nas bordas da janela)
         * O JPanel funciona como uma "folha de acetato" ou uma "bandeja" que colocamos
         * sobre
         * a janela (JFrame).
         * Embora seja possível colocar tudo direto no JFrame, usar um JPanel permite
         * que
         * você organize grupos de componentes de forma independente.
         * No projeto, o painelPrincipal é a base onde desenharemos
         * todo o formulário de login.
         * GridBagLayout é o Gerenciador de Layout mais flexível e poderoso do Java
         * Swing.
         * Imagine uma grade invisível (como uma planilha do Excel) composta por colunas
         * e
         * linhas.
         * Diferente de outros gerenciadores simples que apenas empilham coisas, o
         * GridBagLayout permite que você controle exatamente em qual "célula" cada
         * componente
         * vai ficar, se ele deve esticar ou se deve ficar centralizado.
         */
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        /*
         * EmptyBorder, como o nome diz, é uma "Borda Vazia". Ela não desenha linhas nem
         * molduras visíveis; a sua única função é criar um espaço em branco (margem)
         * entre
         * 6
         * as bordas do painel e os componentes que estão lá dentro (os campos de texto
         * e
         * botões).
         * Entendendo os Números (20, 20, 20, 20): os valores representam a distância em
         * pixels, seguindo sempre a ordem dos ponteiros do relógio (começando pelo
         * topo):
         * Top (Acima) : 20px
         * Left (Esquerda): 20px
         * Bottom (Abaixo): 20px
         * Right (Direita): 20px
         * Funciona como a margem de uma folha de caderno. Você não escreve o texto
         * encostado
         * no corte do papel; você deixa um espaço em branco para que a leitura seja
         * confortável. No Java, essa margem garante que os nossos botões e campos não
         * fiquem
         * 'esmagados' contra os cantos da janela.
         */
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        /*
         * Definindo a cor de fundo (Background) do componente. No caso, estamos
         * aplicando
         * isso ao painelPrincipal, que é a base onde tudo está montado.
         * O comando new Color(245, 245, 245):
         * aqui entramos no sistema RGB (Red, Green, Blue). O Java cria cores misturando
         * três
         * canais de luz:
         * R (Red - Vermelho): 245 | G (Green - Verde): 245 | B (Blue - Azul): 245
         * A escala vai de 0 a 255
         * Se todos forem 0, 0, 0, a cor é Preto.
         * Se todos forem 255, 255, 255, a cor é Branco.
         * Como usamos 245, que é um valor muito alto (quase 255), o resultado é um
         * Cinza Claríssimo, quase branco.
         */
        painelPrincipal.setBackground(new Color(245, 245, 245)); // Cinza claro
        /*
         * A instrução a seguir é a chave para organizar telas que não "bagunçam" ao
         * redimensionarmos a janela. Se o BorderLayout é bom para colocar coisas nas
         * bordas
         * (como a imagem do puzzle na esquerda da tela principal), o GridBagLayout é
         * ideal
         * para organizar o meio da tela, como o formulário de login.
         * 1. O que é o GridBagConstraints?
         * Imagine que a sua tela de login é uma planilha de Excel invisível (uma grade
         * com
         * linhas e colunas).
         * • O GridBagLayout é o gerente que cuida dessa grade.
         * • O GridBagConstraints (que abreviamos como gbc) é a ficha de instruções de
         * cada
         * componente (botão, texto, rótulo).
         * Quando você digita GridBagConstraints gbc = new GridBagConstraints();, você
         * está
         * criando uma "ficha em branco" onde vai anotar as regras de posicionamento.
         * 2. Para que serve na prática?
         * Depois de criar o objeto gbc, você começa a preencher as propriedades dele
         * para
         * dizer ao Java exatamente onde cada peça deve morar. Por exemplo:
         * • gbc.gridx e gbc.gridy: Definem a coluna e a linha (ex: gridx = 0, gridy = 0
         * é o
         * topo esquerdo).
         * • gbc.insets: Define o espaçamento (margem) ao redor daquele componente
         * específico.
         * • gbc.anchor: Diz se o componente deve ficar "grudado" no centro, na esquerda
         * ou na
         * direita da célula.
         * • gbc.fill: Define se o componente deve esticar para preencher todo o espaço
         * da
         * célula ou ficar no tamanho original.
         * 3. Por que usamos o new?
         * O new serve para instanciar (criar na memória) esse objeto de configuração.
         * Sem
         * essa linha, você não teria onde guardar as regras de layout, e o Java não
         * saberia
         * como alinhar o campo de "Usuário" com o campo de "Senha".
         */
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 10, 10, 10); // Espaçamento entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // --- Rótulo e Campo NOME ---
        gbc.gridx = 0;
        gbc.gridy = 0; // Coordenada (0,0)
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Arial", Font.BOLD, 14));
        // A seguinte instrução adciona a label contendo "Nome:" na tela
        // (painelPrincipal)
        painelPrincipal.add(lblNome, gbc);
        gbc.gridx = 1; // Coordenada (1,0)
        txtNome = new JTextField(20);
        txtNome.setPreferredSize(new Dimension(200, 30));
        painelPrincipal.add(txtNome, gbc); // Adiciona txtNome na tela (painelPrincipal)
        // --- Rótulo e Campo PRONTUÁRIO ---
        gbc.gridx = 0;
        gbc.gridy = 1; // Coordenada (0,1)
        JLabel lblProntuario = new JLabel("Prontuário:");
        lblProntuario.setFont(new Font("Arial", Font.BOLD, 14));
        painelPrincipal.add(lblProntuario, gbc); // Adiciona lblProntuario em painelPrincipal
        gbc.gridx = 1; // Coordenada (1,1)
        txtProntuario = new JTextField(20);
        txtProntuario.setPreferredSize(new Dimension(200, 30));
        painelPrincipal.add(txtProntuario, gbc); // Adiciona txtProntuario em painelPrincipal
        // --- BOTÃO ENTRAR ---
        gbc.gridx = 1;
        gbc.gridy = 2; // Coordenada (1,2): botão fica embaixo do campo prontuário
        gbc.gridwidth = 1; // Ocupa uma coluna só (embaixo do campo prontuário)
        gbc.insets = new java.awt.Insets(20, 10, 10, 10);
        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntrar.setBackground(new Color(70, 130, 180)); // Cor fundo do texto: Azul aço
        btnEntrar.setForeground(Color.WHITE); // Cor do texto: Branco
        btnEntrar.setFocusPainted(false);
        btnEntrar.setPreferredSize(new Dimension(100, 40));
        painelPrincipal.add(btnEntrar, gbc); // Adiciona btnEntrar em painelPrincipal
        // Mostra na tela todos os componentes que estão dentro do painel:
        // as labels com "Nome" e "Prontuário", os campos para digitá-los e o botão
        // Entrar

        // Codificando o ActionListener: adicionando o evento ao clique do botão Entrar
        // Trata evento click no btnEntrar com expressão Lambda:
        btnEntrar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String prontuario = txtProntuario.getText().trim();
            // Criamos o objeto usuário conforme já codificado anteriormente
            Usuario usu = new Usuario(nome, prontuario);
            // Se o campo nome e o campo prontuário estiverem preenchidos, para o momento
            // apenas damos boas vindas ao usuário e avisamos que iniciaremos o teste
            if (!nome.isEmpty() && !prontuario.isEmpty()) {
                //JOptionPane.showMessageDialog(null, "Bem-vindo, " +
                //        usu.getNome().toUpperCase() +
                //        "!\nVamos iniciar o teste.");
                // Futuramente: enviaremos os dados digitados para o construtor do FormMenu
                new FormMenu(usu).setVisible(true); // Mostraremos a tela do sistema
                dispose(); // Fecha a tela de login
            } else {
                // Se o campo nome e/ou o campo prontuário estiver em branco, adverte usuário
                JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        add(painelPrincipal);
    }
}