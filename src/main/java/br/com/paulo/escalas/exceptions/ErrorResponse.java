package br.com.paulo.escalas.exceptions;

import java.time.Instant;

public class ErrorResponse {
    private int status;
    private String title;
    private String message;
    private Instant timestamp;

    public ErrorResponse(Instant timestamp, int status, String title, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.title = title;
    }

    // Getters e Setters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public Instant getTimestamp() {return timestamp;}
    public String getTitle() {return title;}
}

