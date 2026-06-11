package model;

import java.io.Serializable;

public class Questao implements Serializable {
    // Atributo necessário para o controle de versão da serialização
    private static final long serialVersionUID = 1L;
    private int numero;
    private String frase;
    private String visual;
    private String auditivo;
    private String cinestesico;
    private String digital;

    // Construtor que recebe as 5 partes da questão
    public Questao(int n, String frase, String v, String c, String a, String d) {
        this.numero = n;
        this.frase = frase;
        this.visual = v;
        this.cinestesico = c;
        this.auditivo = a;
        this.digital = d;
    }

    // NÃO É NECESSÁRIO HAVER SETTERS NESTA CLASSE, somente Getters!
    // Uma vez que o sistema lê as perguntas do arquivo .DAT,
    // o aluno não vai querer "mudar" o texto da pergunta durante a execução.
    // Getters: Essenciais para a tela ler a frase e as opções
    // e exibi-las para o usuário.
    // Setters: AQUI, não são necessários, pois os dados entram
    // pelo Construtor no momento da leitura do arquivo
    // e ficam "congelados" lá.
    // OS GETTERS DEVEM ESTAR EXATAMENTE ASSIM:
    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getNumero() {
        return numero;
    }

    public String getFrase() {
        return frase;
    }

    public String getVisual() {
        return visual;
    }

    public String getAuditivo() {
        return auditivo;
    }

    public String getCinestesico() {
        return cinestesico;
    }

    public String getDigital() {
        return digital;
    }
}