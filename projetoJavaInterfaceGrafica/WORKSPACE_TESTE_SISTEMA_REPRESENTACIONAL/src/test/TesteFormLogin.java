package test;

import view.FormLogin;
import javax.swing.SwingUtilities;

public class TesteFormLogin {
    public static void main(String[] args) {
        // No Swing, é boa prática iniciar a tela dentro da Thread de Eventos
        SwingUtilities.invokeLater(() -> { // Isso é uma Lambda sem parâmetros.
            FormLogin tela = new FormLogin();
            tela.setVisible(true);
        });
    }
}