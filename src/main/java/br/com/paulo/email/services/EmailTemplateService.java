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

    public String montarTemplateResetSenha(String nome, String token, int validadeMinutos) {
        return """
                Olá %s,

                Recebemos uma solicitação para redefinir sua senha.
                Use o código abaixo no aplicativo (válido por %d minutos):

                %s

                Se você não fez essa solicitação, ignore este e-mail.
                """.formatted(nome, validadeMinutos, token);
    }

    public String montarTemplatePrimeiroAcesso(String nome, String token, int validadeMinutos) {
        return """
                Olá %s,

                Seu acesso ao sistema de escalas foi liberado.
                No aplicativo, em "Primeiro acesso", informe o código abaixo
                para cadastrar seu nome e sua senha (válido por %d minutos):

                %s

                Se você não fez essa solicitação, ignore este e-mail.
                """.formatted(nome, validadeMinutos, token);
    }
}
