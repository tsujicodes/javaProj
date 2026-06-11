package test;

import model.Usuario; //Para poder usar a classe Usuario que está na pasta model

public class TesteUsuario {
    public static void main(String[] args) {
        // 1. Criando um objeto (Instanciação) com nome contendo espaços
        //Usuario aluno = new Usuario("André Luiz d@ Silva", "sp07102x");
        Usuario aluno = new Usuario("Alberta da Silva Feitosa", "sp123456");
        // 2. Testando o Get (Acesso controlado)
        System.out.println("Usuário cadastrado: " + aluno.getNome());
        // 3. Testando a Lógica do Requisito 1.2 (Gerar nome de arquivo)
        String nomeArquivo = aluno.gerarNomeArquivoSaida();
        System.out.println("--------------------------------------");
        System.out.println("Gerando nome do arquivo...");
        System.out.println("Resultado: " + nomeArquivo);
        System.out.println("--------------------------------------");
        // Verificação de Sucesso
        if (nomeArquivo.equals("RESULTADO_ALBERTA_DA_SILVA_FEITOSA_SP123456.TXT"))
            System.out.println("TESTE OK: Espaços substituídos e formato correto!");
        else
            System.out.println("TESTE FALHOU: Verifique a lógica do replace().");
    }
}