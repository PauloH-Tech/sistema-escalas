package br.com.paulo.email.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class EmailOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "msg_to")
    private String to;
    @Column(name = "msg_cc")
    private String cc;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    private int tentativas;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataEnvio;
    @Column(columnDefinition = "TEXT")
    private String erro;

}
