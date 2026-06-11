package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import model.Questao; // IMPORTANTE: Importa a Questao do pacote model

public class GerarQuestionario {
    public static String cadastrar() {
        // Criando o array de 5 elementos (Mapeando a classe Questao)
        Questao[] perguntas = new Questao[5];
        // Usando o CONSTRUTOR da classe Questao para respeitar o Encapsulamento
        perguntas[0] = new Questao(1,
                "Eu tomo decisões importantes baseado em:",
                "O que me parece melhor (V)",
                "Intuição (C)",
                "O que me soa melhor (A)",
                "Um estudo preciso (D)");
        perguntas[1] = new Questao(2,
                "Durante uma discussão eu sou mais influenciado por:",
                "Argumento visual (V)",
                "Sentimentos reais (C)",
                "Tom da voz (A)",
                "Lógica do argumento (D)");
        perguntas[2] = new Questao(3,
                "Eu comunico mais facilmente o que se passa comigo:",
                "Do modo como me visto e aparento (V)",
                "Pelos sentimentos que compartilho (C)",
                "Pelo tom da minha voz (A)",
                "Pelas palavras que escolho (D)");
        perguntas[3] = new Questao(4,
                "É muito fácil para mim:",
                "Escolher as combinações de cores mais ricas e atraentes (V)",
                "Escolher os móveis mais confortáveis (C)",
                "Achar o volume e a sintonia ideais num sistema de som (A)",
                "Selecionar o ponto mais relevante relativo a um assunto interessante (D)");
        perguntas[4] = new Questao(5,
                "Eu me percebo assim:",
                "Eu respondo fortemente às cores e à aparência de uma sala (V)",
                "Eu sou muito sensível à maneira como a roupa veste o meu corpo (C)",
                "Se estou muito em sintonia com os sons dos ambientes (A)",
                "Se sou muito capaz de raciocinar com fatos e dados novos (D)");
        try {
            // Para salvar o arquivo .DAT na pasta raiz:
            // Em vez de salvar na pasta raiz do workspace, salvamos dentro de /data
            File pastaData = new File("data"); // Define a pasta "data" como local do .DAT
            if (!pastaData.exists())
                pastaData.mkdir(); // Cria a pasta "data" se não existir
            // Define o nome do .DAT e que ele ficará na pasta "data"
            File arquivoPronto = new File(pastaData, "TESTE_SISTEMA_REPRESENTACIONAL.DAT");
            // Gravação Binária (Serialização)
            try (ObjectOutputStream gravador = new ObjectOutputStream(new FileOutputStream(arquivoPronto))) {
                gravador.writeObject(perguntas); // Grava o array com as 5 questões no .DAT
            }
            return arquivoPronto.getAbsolutePath();// Retorna String contendo o local do .DAT
        } catch (Exception e) { // Caso a geração do .DAT dê algum erro, avisa
            System.err.println("Erro ao gerar o arquivo .DAT: " + 
                              e.getMessage());
            return null;
        }
    }
}
