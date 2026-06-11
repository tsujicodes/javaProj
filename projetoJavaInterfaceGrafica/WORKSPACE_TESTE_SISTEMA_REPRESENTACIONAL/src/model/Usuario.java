package model;

public class Usuario {
    // Atributos privados (Encapsulamento)
    private String nome;
    private String prontuario;

    // Construtor
    public Usuario(String nome, String prontuario) {
        this.nome = nome;
        this.prontuario = prontuario;
    }

    // Métodos Getter e Setter (Acesso aos atributos)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProntuario() {
        return prontuario;
    }

    public void setProntuario(String prontuario) {
        this.prontuario = prontuario;
    }

    // Lógica de Strings: gera o nome do arquivo conforme o requisito 1.2 do
    // projeto.
    // Exemplo: "NOME USUARIO" + "RJ123" -> RESULTADO_NOME_USUARIO_RJ123.TXT
    public String gerarNomeArquivoSaida() {
        // Coloca em maiúsculas e substitui espaços por underscores
        String nomeFormatado = this.nome.toUpperCase().replace(" ", "_");
        return "RESULTADO_" + nomeFormatado + "_" +
                this.prontuario.toUpperCase() + ".TXT";
    }
}