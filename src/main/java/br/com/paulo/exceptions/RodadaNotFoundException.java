package br.com.paulo.exceptions;

public class RodadaNotFoundException extends RuntimeException {
    public RodadaNotFoundException(String message) {
        super(message);
    }
}
