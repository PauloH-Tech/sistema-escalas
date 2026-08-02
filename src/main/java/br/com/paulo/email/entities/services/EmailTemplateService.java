package br.com.paulo.email.entities.services;

import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateService {

    public String montarTemplateEscala(EscalaExtra escala) {

        return """
                Olá %s,
                
                Você foi escalado para o dia %s.
                
                Não responda este e-mail.
                """;

    }


}
