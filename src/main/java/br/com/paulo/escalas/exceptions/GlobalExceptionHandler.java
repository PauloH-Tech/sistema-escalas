package br.com.paulo.escalas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroResposta> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        String message = "Endpoint não suportado: " + ex.getMessage();
        ErroResposta erro = new ErroResposta(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroResposta> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        String message = "Requisição inválida: " + ex.getMessage();
        ErroResposta erro = new ErroResposta(HttpStatus.BAD_REQUEST.value(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(RodadaNotFoundException.class)
    public ResponseEntity<ErroResposta> handleRodadaNotFound(RodadaNotFoundException ex) {
        String message = "Rodada não encontrada: " + ex.getMessage();
        ErroResposta erro = new ErroResposta(HttpStatus.NOT_FOUND.value(), message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

}
