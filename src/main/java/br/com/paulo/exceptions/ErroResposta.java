package br.com.paulo.exceptions;

public class ErroResposta {
    private int status;
    private String mensagem;

    public ErroResposta(int status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    // Getters e Setters
    public int getStatus() { return status; }
    public String getMensagem() { return mensagem; }
}

