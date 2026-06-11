package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Avaliacao;
import model.Questao;
import model.Usuario;
import persistence.GerenciarDados;
// Para gerar o arquivo .DAT (quando o usuário for MASTER), fazemos os dois imports abaixo
import java.io.File;
import java.io.FileWriter;
// Para gerar o arquivo .TXT, fazemos o import abaixo
import java.io.PrintWriter;
import util.GerarQuestionario;
import util.TextosResultado; // O significado de cada perfil está definido na classe TextosResultado

public class TesteFluxoConsole { // NOSSA CLASSE DE TESTE
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        GerenciarDados persistence = new GerenciarDados();
        System.out.println("====================================================");
        System.out.println(" SISTEMA DE AVALIAÇÃO REPRESENTACIONAL ");
        System.out.println("====================================================");
        System.out.print("Nome do Aluno: ");
        String nome = leitor.nextLine();
        System.out.print("Prontuário: ");
        String prontuario = leitor.nextLine();
        Usuario usuario = new Usuario(nome, prontuario);
        // Se o usuário não for MASTER, rodamos o teste
        if (!usuario.getNome().equalsIgnoreCase("MASTER")) {
            // O método carregarQuestoes lê o arquivo .DAT e carrega as questões como uma
            // Lista
            List<Questao> questoes = persistence.carregarQuestoes();
            Avaliacao avaliacao = new Avaliacao(usuario);
            // Mostrando nome e prontuário do usuário
            System.out.println("\nSistema Representacional [" + usuario.getNome().toUpperCase() +
                    "-" + usuario.getProntuario().toUpperCase() + "] LOGADO");
            // Mostra as instruções
            mostraInstrucoes();
            // Mostra as questões e captura pontuações
            for (Questao q : questoes) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("PERGUNTA " + q.getNumero() + ": " + q.getFrase());
                System.out.println("-".repeat(60));
                // Mostra todas as frases numeradas
                System.out.println("\n[1] " + q.getVisual());
                System.out.println("\n[2] " + q.getCinestesico());
                System.out.println("\n[3] " + q.getAuditivo());
                System.out.println("\n[4] " + q.getDigital());
                // LISTA TEMPORÁRIA: Para controlar as notas dadas nesta pergunta específica
                List<Integer> notasUsadas = new ArrayList<>();
                // Processo de captura com validação de duplicidade
                System.out.println("\nNota para [1]: ");
                int n1 = capturarNotaValidada(leitor, notasUsadas);
                avaliacao.computarPontos("V", n1);
                System.out.println("Nota para [2]: ");
                int n2 = capturarNotaValidada(leitor, notasUsadas);
                avaliacao.computarPontos("C", n2);
                System.out.println("Nota para [3]: ");
                int n3 = capturarNotaValidada(leitor, notasUsadas);
                avaliacao.computarPontos("A", n3);
                System.out.println("Nota para [4]: ");
                int n4 = capturarNotaValidada(leitor, notasUsadas);
                avaliacao.computarPontos("D", n4);
            }
            // Exibição do resultado
            exibirResultado(avaliacao);
            // Mostrar o gráfico com o resultado do teste
            exibirGrafico(avaliacao, usuario);
            // Gerar o arquivo com o resultado do teste
            gerarArquivoResultado(avaliacao, usuario);
        } else {// Se o usuário for MASTER, geramos o arquivo .DAT
            System.out.println("\n[INFO] Modo MASTER detectado.\n");
            gerarArquivoDat();
        }
        leitor.close();
    }

    // Métodos auxiliares para organizar o código
    private static void mostraInstrucoes() {
        System.out.println("\nINSTRUÇÕES:\n");
        System.out.println("Para cada pergunta, temos 04 afirmações e " + //
                "cada uma deve receber uma pontuação diferente.");
        System.out.println("Quanto mais alta a pontuação que você escolher, " + //
                "melhor representa você.");
        System.out.println("Se a pontuação for mais baixa, significa que " + //
                "você não se identifica tanto com aquela afirmação.");
        System.out.println("Desta forma, escolha de 1 a 4 pontos sem repetir " + //
                "nenhum número dentro de cada pergunta.");
        System.out.println("Pressione ENTER após cada nota.");
    }

    // Método que garante que a nota seja de 1 a 4 e que não tenha sido repetida
    private static int capturarNotaValidada(Scanner s, List<Integer> notasJaDadas) {
        int nota = 0;
        boolean valido = false;
        while (!valido) {
            try {
                System.out.print(" -> Digite a nota (1-4): ");
                nota = Integer.parseInt(s.nextLine()); // Lê efetivamente a nota
                if (nota < 1 || nota > 4) { // Checa se a nota digitada está dentro dos limites
                    System.out.println(" [ERRO] A nota deve ser entre 1 e 4!");
                } else if (notasJaDadas.contains(nota)) { // Checa se repetiu a nota
                    System.out.println(" [ERRO] Você já usou a nota "
                            + nota + " nesta pergunta. Escolha outra!");
                } else {
                    notasJaDadas.add(nota); // Adiciona à lista de controle
                    valido = true;
                }
            } catch (Exception e) { // Caso não seja digitado número inteiro
                System.out.println(" [ERRO] Digite apenas números inteiros!");
            }
        }
        return nota;
    }

    private static void exibirResultado(Avaliacao av) {
        System.out.println("\n\n" + "#".repeat(50));
        System.out.println(" RELATÓRIO FINAL DE AVALIAÇÃO");
        System.out.println("#".repeat(50));
        System.out.println("CANDIDATO : " + av.getUsuario().getNome().toUpperCase());
        System.out.println("PRONTUÁRIO: " + av.getUsuario().getProntuario().toUpperCase());
        System.out.println("-".repeat(50));
        // Acessando cada canal individualmente pelo Map de totais
        // O método .get("C") busca o valor inteiro associado àquela letra
        // Para exibir os resultados finais, acessamos o mapa de pontuações
        // usando as chaves 'C', 'A', 'V' e 'D'.
        // O método get(chave) nos devolve exatamente
        // o valor acumulado para aquele sistema representacional.
        System.out.printf(" > CINESTÉSICO : %2d pontos PERCENTAGEM = %2d%%%n",
                av.getTotais().get("C"), av.getTotais().get("C") * 2);
        System.out.printf(" > AUDITIVO : %2d pontos PERCENTAGEM = %2d%%%n",
                av.getTotais().get("A"), av.getTotais().get("A") * 2);
        System.out.printf(" > VISUAL : %2d pontos PERCENTAGEM = %2d%%%n",
                av.getTotais().get("V"), av.getTotais().get("V") * 2);
        System.out.printf(" > DIGITAL : %2d pontos PERCENTAGEM = %2d%%%n",
                av.getTotais().get("D"), av.getTotais().get("D") * 2);
        // Mostrando o perfil dominante
        System.out.println(" PERFIL DOMINANTE: " + av.obterPerfilDominante());
        System.out.println("#".repeat(50));
    }

    public static void gerarArquivoResultado(Avaliacao av, Usuario user){
    // Chama o método da classe Usuario que gera o nome do arquivo de saída
    // O arquivo contendo o resultado será gerado na pasta data
    String nomeArquivoResultado = "data/"+user.gerarNomeArquivoSaida();
    // Cria o arquivo de saída
    try (PrintWriter arq = new PrintWriter(new FileWriter(nomeArquivoResultado))) {
    String formato = "===== PERFIL REPRESENTACIONAL DE %s =====%n";
    // Criamos uma linha divisória
    String divisoria = "=".repeat(61);
    arq.println(divisoria);
    arq.printf(formato, user.getNome().toUpperCase());
    arq.println(divisoria);
    formato = "| %2d%% VISUAL | %2d%% AUDITIVO | %2d%% CINESTÉSICO | %2d%% DIGITAL |%n";
    arq.printf(formato, av.getTotais().get("V")*2, av.getTotais().get("A")*2,
    av.getTotais().get("C")*2, av.getTotais().get("D")*2 );
    arq.println(divisoria);
    // Mostra o significado do resultado
    formato = "Algumas pessoas captam melhor as mensagens do mundo exterior "+//
    " através da audição, são as pessoas chamadas auditivas.\r\n" + //
"Outras pessoas sentem necessidade de perguntar muito, necessitam de muitas "+//
"informações e fatos. Estas são as digitais.\r\n" + //
"As cinestésicas aprendem melhor por meio das sensações táteis, como o tato, "+//
"a temperatura, a umidade, as sensações internas e as emoções.\r\n" + //
"Já as pessoas visuais aprendem melhor quando se valendo da visão.";
arq.println(formato);
arq.println(divisoria);
formato = "Seu perfil é ";
arq.println(formato + av.obterPerfilDominante());
arq.println(divisoria);
// Busca o significado do resultado do teste na classe utilitária
arq.println(TextosResultado.getDescricao(av.obterPerfilDominante()));
arq.println("\n" + divisoria);
// Fecha o arquivo
arq.close();
System.out.println("SUCESSO: Resultado gerado em: " + nomeArquivoResultado);
} catch (Exception e) {
System.err.println("ERRO ao gravar " + nomeArquivoResultado + " -> " + e.getMessage());
}
// Exibe o resultado final
System.out.println("\n--------------------------------------");
System.out.println("Nome do arquivo gerado:");
System.out.println(nomeArquivoResultado);
System.out.println("--------------------------------------");
}

    private static void exibirGrafico(Avaliacao av, Usuario user) {
        System.out.println("\n GRÁFICO DE PERFIL REPRESENTACIONAL DE "
                + user.getNome().toUpperCase() + " (EM %) ");
        System.out.println("=".repeat(72));
        // Pegamos os valores e calculamos a porcentagem (Total maximo 50 pontos = 100%)
        int c = av.getTotais().get("C") * 2;
        int a = av.getTotais().get("A") * 2;
        int v = av.getTotais().get("V") * 2;
        int d = av.getTotais().get("D") * 2;
        // Desenha as barras (dividimos por 2 para a barra não ficar gigante na tela)
        imprimirBarra("CINESTÉSICO", c);
        imprimirBarra("AUDITIVO ", a);
        imprimirBarra("VISUAL ", v);
        imprimirBarra("DIGITAL ", d);
        System.out.println("=".repeat(72));
        System.out.println(" 0% 20% 40% 60% 80% 100%");
    }

    private static void imprimirBarra(String rotulo, int valor) {
        // Caractere de preenchimento (pode usar '#' se o console não suportar Unicode)
        String bloco = "█";
        // Proporção: cada bloco representa 2% para caber no console
        String barra = bloco.repeat(valor / 2); // Aqui mostra a barra pronta
        System.out.printf("%s | %s %d%%%n", rotulo, barra, valor); // e a %
    }

    public static void gerarArquivoDat() { // Quando o MASTER é logado, gerar o .DAT
        System.out.println("=== INICIANDO GERAÇÃO DO ARQUIVO " + //
                "TESTE_SISTEMA_REPRESENTACIONAL.DAT ===");
        // 1. Chama o método cadastrar da classe GerarQuestionario
        String caminhoRetornado = GerarQuestionario.cadastrar();
        if (caminhoRetornado != null) {
            // 2. Validação física: O arquivo realmente existe onde a classe disse?
            File arquivo = new File(caminhoRetornado);
            if (arquivo.exists()) {
                System.out.println("SUCESSO!");
                System.out.println("Arquivo gerado em: " + caminhoRetornado);
                System.out.println("Tamanho do arquivo: " + arquivo.length() + " bytes.");
            } else {
                System.err.println("ERRO: O método retornou um caminho, " + //
                        "mas o arquivo não foi localizado.");
            }
        } else {
            System.err.println("ERRO: O método GerarQuestionario.cadastrar() retornou null.");
        }
    }
}
