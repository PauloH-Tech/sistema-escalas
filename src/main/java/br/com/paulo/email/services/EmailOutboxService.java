package br.com.paulo.email.services;

import br.com.paulo.email.entities.EmailOutbox;
import br.com.paulo.email.entities.EmailStatus;
import br.com.paulo.email.repositories.EmailRepository;
import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import br.com.paulo.escalas.entities.usuarios.Usuario;
import br.com.paulo.escalas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailOutboxService {

    private final EmailTemplateService templateService;
    private final EmailRepository emailRepository;
    private final UsuarioRepository usuarioRepository;


    public void salvarEmail(EscalaExtra escala) {
        EmailOutbox email = new EmailOutbox();
        // o e-mail do militar vem do usuário vinculado (acesso ao app)
        String destino = usuarioRepository.findByMilitarId(escala.getMilitar().getId())
                .map(Usuario::getEmail)
                .orElse("paulohsantos2005@gmail.com");
        email.setTo(destino);
        email.setSubject("NOVA ESCALA AGENDADA");
//        se precisar mandar cc -> colocar o escalador;
        email.setBody(templateService.montarTemplateEscala(escala));
        email.setDataCriacao(LocalDateTime.now());
        email.setStatus(EmailStatus.PENDENTE);

//        System.out.println(email);
        emailRepository.save(email);

    }

    public void salvarEmail(String to, String subject, String body) {
        EmailOutbox email = new EmailOutbox();
        email.setTo(to);
        email.setSubject(subject);
        email.setBody(body);
        email.setDataCriacao(LocalDateTime.now());
        email.setStatus(EmailStatus.PENDENTE);
        emailRepository.save(email);
    }
}
