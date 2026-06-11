package test;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import model.Questao;
import persistence.GerenciarDados;

public class TesteGerarRelatorio {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO GERAÇÃO DO RELATÓRIO DE QUESTÕES ===");
        GerenciarDados persistence = new GerenciarDados();
        List<Questao> lista = persistence.carregarQuestoes();
        if (lista != null && !lista.isEmpty()) {
            String arquivoSaida = "data/REL_QUESTOES.TXT";
            try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoSaida))) {
                // Formato ajustado: Nro(8), Frase(55) e os 4 itens com 75 cada
                String formato = "| %-3s | %-55s | %-75s | %-75s | %-75s | %-75s |%n";
                // Criamos uma linha divisória proporcional ao tamanho das colunas
                // Aproximadamente a soma das larguras + separadores
                String divisoria = "=".repeat(376);
                writer.println(divisoria);
                writer.printf(formato, "Nro", "Frase", "item_1", "item_2", "item_3", "item_4");
                writer.println(divisoria);
                for (Questao q : lista) {
                    writer.printf(formato,
                            q.getNumero(),
                            q.getFrase(),
                            q.getCinestesico(),
                            q.getAuditivo(),
                            q.getVisual(),
                            q.getDigital());
                }
                writer.println(divisoria);
                System.out.println("SUCESSO: Relatório gerado em: " 
                + arquivoSaida);
            } catch (Exception e) {
                System.err.println("ERRO ao gravar o relatório TXT: " + e.getMessage());
            }
        } else {
            System.err.println("ERRO: Não há questões carregadas para gerar o relatório.");
        }
    }
}
