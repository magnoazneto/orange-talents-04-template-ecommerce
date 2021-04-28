package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class EnviadorEmail implements EmailSenderProvider {

    public String enviaEmail(Usuario donoProduto, Produto produto, Pergunta pergunta){
        return donoProduto.getLogin() + ", você recebeu uma nova pergunta no produto " + produto.getNome()
                + " - Pergunta: \"" + pergunta.getTitulo()
                + "\" Acesse sua conta para responder!";
    }
}
