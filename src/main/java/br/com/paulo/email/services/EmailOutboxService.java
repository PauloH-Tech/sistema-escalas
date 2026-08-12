package br.com.paulo.email.services;

import br.com.paulo.email.entities.EmailOutbox;
import br.com.paulo.email.entities.EmailStatus;
import br.com.paulo.email.repositories.EmailRepository;
import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailOutboxService {

    private final EmailTemplateService templateService;
    private final EmailRepository emailRepository;


    public void salvarEmail(EscalaExtra escala) {
        EmailOutbox email = new EmailOutbox();
        if(escala.getMilitar().getEmail() == null) {
            email.setTo("paulohsantos2005@gmail.com");
        } else {
            email.setTo(escala.getMilitar().getEmail());
        }
        email.setSubject("NOVA ESCALA AGENDADA");
//        se precisar mandar cc -> colocar o escalador;
        email.setBody(templateService.montarTemplateEscala(escala));
        email.setDataCriacao(LocalDateTime.now());
        email.setStatus(EmailStatus.PENDENTE);

//        System.out.println(email);
        emailRepository.save(email);

    }
}
