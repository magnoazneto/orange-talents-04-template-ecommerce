package com.zupacademy.magno.mercadolivre.utils.email;

import java.util.HashMap;

public interface EmailSenderProvider {
    public boolean enviaEmail(String destinatario, String assunto, String corpo);
}
