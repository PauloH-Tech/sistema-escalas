package br.com.paulo.email.scheduler;

import br.com.paulo.email.entities.EmailOutbox;
import br.com.paulo.email.repositories.EmailRepository;
import br.com.paulo.email.services.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class EmailScheduler {

    private final EmailRepository emailRepository;
    private final EmailSenderService emailSenderService;


    @Scheduled(fixedRate = 10000)
    public void processarFilaDeEmails() {
        List<EmailOutbox> emailsPendentes = emailRepository.findEmailsPendentes();

        for(EmailOutbox email : emailsPendentes) {
            emailSenderService.enviarEmail(email);
        }


    }



}
