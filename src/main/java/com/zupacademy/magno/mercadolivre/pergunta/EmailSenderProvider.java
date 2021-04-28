package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;

public interface EmailSenderProvider {
    public String enviaEmail(Usuario donoProduto, Produto produto, Pergunta pergunta);
}
