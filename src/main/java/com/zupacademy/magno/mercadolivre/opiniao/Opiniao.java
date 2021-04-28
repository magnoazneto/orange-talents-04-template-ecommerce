package com.zupacademy.magno.mercadolivre.opiniao;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Opiniao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @NotBlank @Length(max = 500)
    private String descricao;
    @NotNull @ManyToOne
    private Produto produto;
    @NotNull @Range(min = 1, max = 5)
    private Integer nota;
    @NotNull @ManyToOne
    private Usuario usuarioOpinador;

    public Opiniao(@NotBlank String titulo,
                   @NotBlank @Length(max = 500) String descricao,
                   @NotNull Produto produto,
                   @NotNull @Range(min = 1, max = 5) Integer nota,
                   @NotNull Usuario usuarioOpinador) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.nota = nota;
        this.usuarioOpinador = usuarioOpinador;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Produto getProduto() {
        return produto;
    }

    public Integer getNota() {
        return nota;
    }

    public Usuario getUsuarioOpinador() {
        return usuarioOpinador;
    }
}
