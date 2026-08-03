package br.com.paulo.email.services;

import br.com.paulo.email.entities.EmailOutbox;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender mailSender;

    public void enviarEmail(EmailOutbox emailOutbox){

        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(emailOutbox.getTo());
        email.setFrom(emailOutbox.getTo());
        email.setSubject(emailOutbox.getSubject());
        email.setText(emailOutbox.getBody());

        mailSender.send(email);

    }
}
