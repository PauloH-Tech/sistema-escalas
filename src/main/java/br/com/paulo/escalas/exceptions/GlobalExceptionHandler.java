package br.com.paulo.escalas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> credenciais(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(Instant.now(),401, "UNAUTHORIZED","E-mail ou senha inválidos"));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> inativo(DisabledException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(Instant.now(),403, "FORBIDDEN","Usuário inativo. Procure o administrador."));
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<ErrorResponse> regraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Instant.now(),400, "BAD REQUEST", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacao(MethodArgumentNotValidException e) {
        Map<String, String> campos = new HashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(erro -> campos.put(erro.getField(), erro.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(Instant.now(),400, "Dados inválidos", campos.toString()));
    }

}
