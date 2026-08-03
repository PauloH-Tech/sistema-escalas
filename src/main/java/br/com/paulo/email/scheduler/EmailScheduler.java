package br.com.paulo.email.scheduler;

import br.com.paulo.email.entities.EmailOutbox;
import br.com.paulo.email.repositories.EmailRepository;
import br.com.paulo.email.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final EmailRepository emailRepository;
    private final EmailSenderService emailSenderService;


    public void processarFilaDeEmails() {
        List<EmailOutbox> emailsPendentes = emailRepository.findEmailsPendentes();

        for(EmailOutbox email : emailsPendentes) {
            emailSenderService.enviarEmail(email);
        }


    }



}
