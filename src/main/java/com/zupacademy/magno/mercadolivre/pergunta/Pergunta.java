package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @NotNull @ManyToOne
    private Produto produto;
    @NotNull @ManyToOne
    private Usuario usuarioCriador;
    private final LocalDateTime instanteCriacao = LocalDateTime.now();

    /**
     * para uso exclusivo da JPA
     */
    @Deprecated
    public Pergunta() {
    }

    /**
     * Deve ser usado como construtor padrão
     * @param titulo titulo da pergunta
     * @param produto produto cadastrado no sistema ao qual a pergunta está sendo feita
     * @param usuarioCriador usuario autenticado que enviou a pergunta
     */
    public Pergunta(@NotBlank String titulo, @NotNull Produto produto, @NotNull Usuario usuarioCriador) {
        Assert.notNull(usuarioCriador, "O usuário não deveria ser nulo.");
        Assert.notNull(produto, "O Produto não deveria ser nulo.");

        this.titulo = titulo;
        this.produto = produto;
        this.usuarioCriador = usuarioCriador;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getUsuarioCriador() {
        return usuarioCriador;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }
}
