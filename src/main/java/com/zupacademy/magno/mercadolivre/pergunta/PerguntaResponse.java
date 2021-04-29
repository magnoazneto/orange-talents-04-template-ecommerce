package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.usuario.cadastro.UsuarioResponse;


public class PerguntaResponse {

    private String titulo;
    private UsuarioResponse usuarioCriador;

    public PerguntaResponse(Pergunta pergunta, Usuario usuarioCriador) {
        this.titulo = pergunta.getTitulo();
        this.usuarioCriador = new UsuarioResponse(usuarioCriador);
    }

    public String getTitulo() {
        return titulo;
    }

    public UsuarioResponse getUsuarioCriador() {
        return usuarioCriador;
    }
}
