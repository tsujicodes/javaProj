package model;

// o import java.util.Map e import java.util.HashMap são necessários
// porque o Map não faz parte do núcleo "padrão" que o Java carrega
// sozinho; ele faz parte das bibliotecas de utilitários de coleções.
import java.util.HashMap;
import java.util.Map;

public class Avaliacao {
    // A classe Usuario deve ser criada antes da classe Avaliacao
    // para podermos criar o atributo usuário
    private Usuario usuario;
    // O Map onde a chave é o número da questão e o valor é o peso da resposta
    // Este Map guardará os totais acumulados: "V" -> 15, "A" -> 10, etc.
    private Map<String, Integer> totaisPorCanal;

    // Método construtor
    public Avaliacao(Usuario usuario) {
        this.usuario = usuario;
        this.totaisPorCanal = new HashMap<>(); // Inicializa o Map vazio
        // Inicializamos os canais com zero
        // para evitar erros de NullPointerException
        totaisPorCanal.put("V", 0);
        totaisPorCanal.put("A", 0);
        totaisPorCanal.put("C", 0);
        totaisPorCanal.put("D", 0);
    }

    //
    // Criamos métodos específicos, em vez de getters e setters
    //
    // Método que recebe o identificador do canal e
    // soma os pontos ao total atual.
    public void computarPontos(String canal, int pontos) {
        // Pegamos o valor que já estava lá
        int valorAtual = totaisPorCanal.get(canal);
        // Somamos com o novo valor e guardamos de volta
        totaisPorCanal.put(canal, valorAtual + pontos);
    }

    // Este método devolve todos os resultados para o programa de teste exibir.
    public Map<String, Integer> getTotais() {
        return totaisPorCanal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    // Método que analisa os totais e retorna o nome do perfil com maior pontuação.
    // Devolve a String com o nome do perfil dominante.
    public String obterPerfilDominante() {
        String dominante = "Não identificado";
        int maior = -1;

        // Percorremos o mapa de resultados
        for (Map.Entry<String, Integer> entrada : totaisPorCanal.entrySet()) {
            if (entrada.getValue() > maior) {
                maior = entrada.getValue();
                // Converte a sigla para o nome completo
                switch (entrada.getKey()) {
                    case "C": dominante = "CINESTÉSICO";    break;
                    case "A": dominante = "AUDITIVO";       break;
                    case "V": dominante = "VISUAL";         break;
                    case "D": dominante = "DIGITAL";        break;
                }
            }
        }
        return dominante;
    }
}