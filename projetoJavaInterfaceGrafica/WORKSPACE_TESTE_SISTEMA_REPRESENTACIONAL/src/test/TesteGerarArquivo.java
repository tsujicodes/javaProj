package test;

import java.io.File; // Necessário para testar se o arquivo .DAT foi mesmo criado
import util.GerarQuestionario; // Necessário para criar o .DAT

public class TesteGerarArquivo {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO TESTE DE GERAÇÃO DE DADOS ===");
        // 1. Chama o método cadastrar da classe GerarQuestionario codificada na pasta
        // util
        // (Assumindo que o método cadastrar() retorna o caminho do arquivo onde .DAT
        // fica)
        String caminhoRetornado = GerarQuestionario.cadastrar();
        if (caminhoRetornado != null) {
            // 2. Validação física: O arquivo realmente existe onde a classe disse?
            File arquivo = new File(caminhoRetornado);
            if (arquivo.exists()) {
                System.out.println("SUCESSO!");
                System.out.println("Arquivo gerado em: " + 
                caminhoRetornado);
                System.out.println("Tamanho do arquivo: " + 
                arquivo.length() + " bytes.");
            } else {
                System.err.println("ERRO: O método retornou um caminho, ");
                System.err.println("mas o arquivo não foi localizado.");
            }
        } else {
            System.err.println("ERRO: O método GerarQuestionario.cadastrar() retornou null.");
        }
        System.out.println("=== FIM DO TESTE ===");
    }
}