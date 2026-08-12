package br.com.paulo.email.services;

import br.com.paulo.escalas.entities.escalas.EscalaExtra;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class EmailTemplateService {

    public String montarTemplateEscala(EscalaExtra escala) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = escala.getRodada().getData().format(fmt);

        return """
                Olá %s,
                
                Você foi escalado para o dia %s.
                
                Não responda este e-mail.
                """.formatted(escala.getMilitar().getNome(), dataFormatada);

    }


}
