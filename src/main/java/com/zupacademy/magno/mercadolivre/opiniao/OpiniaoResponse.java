package com.zupacademy.magno.mercadolivre.opiniao;

import com.zupacademy.magno.mercadolivre.usuario.cadastro.UsuarioResponse;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpiniaoResponse {

    private Long id;
    @NotBlank
    private String titulo;
    @NotNull
    private Long produtoId;
    @NotNull @Range(min = 1, max = 5)
    private Integer nota;
    @NotNull
    private UsuarioResponse usuarioOpinador;

    public OpiniaoResponse(Opiniao opiniao) {
        this.id = opiniao.getId();
        this.titulo = opiniao.getTitulo();
        this.produtoId = opiniao.getProduto().getId();
        this.nota = opiniao.getNota();
        this.usuarioOpinador = new UsuarioResponse(opiniao.getUsuarioOpinador());
    }

    /*
    Precisei criar os getters dessa classe porque em caso contrário, o jackson me devolvia 406
     */
    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Integer getNota() {
        return nota;
    }

    public UsuarioResponse getUsuarioOpinador() {
        return usuarioOpinador;
    }
}
