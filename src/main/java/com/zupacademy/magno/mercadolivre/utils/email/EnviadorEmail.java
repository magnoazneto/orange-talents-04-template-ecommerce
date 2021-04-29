package com.zupacademy.magno.mercadolivre.utils.email;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Primary
public class EnviadorEmail implements EmailSenderProvider {

    public boolean enviaEmail(String destinatario, String assunto, String corpo){
            HashMap<String, String> email = new HashMap<>();
            email.put("destinatario", destinatario);
            email.put("assunto", assunto);
            email.put("mensagem", corpo);

            System.out.println(email);
            return true;
    }
}
