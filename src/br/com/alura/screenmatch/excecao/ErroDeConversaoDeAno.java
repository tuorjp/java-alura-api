package br.com.alura.screenmatch.excecao;

public class ErroDeConversaoDeAno extends RuntimeException {
    private String message;

    public ErroDeConversaoDeAno(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
