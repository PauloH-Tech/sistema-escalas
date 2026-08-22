package br.com.paulo.escalas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        String title = "Endpoint não suportado";
        ErrorResponse error = new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(), title, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(MethodArgumentTypeMismatchException ex) {
        String title = "Requisição inválida";
        ErrorResponse error = new ErrorResponse(Instant.now(), HttpStatus.BAD_REQUEST.value(), title, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RodadaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRodadaNotFound(RodadaNotFoundException ex) {
        String title = "Rodada não encontrada";
        ErrorResponse error = new ErrorResponse(Instant.now(), HttpStatus.NOT_FOUND.value(), title, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MilitarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMilitarNotFound(MilitarNotFoundException ex) {
        String title = "Militar não encontrado";
        ErrorResponse error = new ErrorResponse(Instant.now(), HttpStatus.NOT_FOUND.value(), title, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MilitarInativoException.class)
    public ResponseEntity<ErrorResponse> handleMilitarInativo(MilitarInativoException ex) {
        String title = "Militar está inativo";
        ErrorResponse error = new ErrorResponse(Instant.now(), HttpStatus.NOT_FOUND.value(), title, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
