package br.com.paulo.email.services;

import br.com.paulo.email.entities.EmailOutbox;
import br.com.paulo.email.entities.EmailStatus;
import br.com.paulo.email.repositories.EmailRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    public void enviarEmail(EmailOutbox email){

        try {
            SimpleMailMessage emailSender = new SimpleMailMessage();

            emailSender.setTo(email.getTo());
            emailSender.setFrom(email.getTo());
            emailSender.setSubject(email.getSubject());
            emailSender.setText(email.getBody());

            mailSender.send(emailSender);

            email.setDataEnvio(LocalDateTime.now());
            email.setStatus(EmailStatus.ENVIADO);

        } catch (Exception e) {
            email.setStatus(EmailStatus.ERRO);
            email.setTentativas(email.getTentativas() + 1);
            email.setErro(e.getMessage());
            e.printStackTrace();
        }

        emailRepository.save(email);
    }
}
