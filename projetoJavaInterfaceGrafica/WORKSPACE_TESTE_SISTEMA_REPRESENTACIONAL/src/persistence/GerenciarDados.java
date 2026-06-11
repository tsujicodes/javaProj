package persistence;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Arrays;
import java.util.List;
import model.Questao;

public class GerenciarDados {
    // Caminho do arquivo (Dica: usar caminho relativo para funcionar em Windows e
    // Linux)
    private final String CAMINHO_ARQUIVO = "data/TESTE_SISTEMA_REPRESENTACIONAL.DAT";

    // Método para ler as questões do arquivo binário.
    // Retorna a lista de objetos do tipo Questao.
    public List<Questao> carregarQuestoes() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CAMINHO_ARQUIVO))) {
            // 1. Lê como Array (que é como o arquivo foi gravado)
            Questao[] array = (Questao[]) ois.readObject();
            // 2. Converte para Lista e a retorna
            return Arrays.asList(array);
        } catch (Exception e) { // Caso não consiga ler .DAT, avisa
            System.err.println("Erro ao carregar o arquivo .DAT: " + e.getMessage());
            return null;
        }
    }
}