package com.zupacademy.magno.mercadolivre.opiniao;

import com.zupacademy.magno.mercadolivre.usuario.cadastro.UsuarioResponse;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OpiniaoResponse {

    @NotBlank
    private String titulo;
    @NotNull @Range(min = 1, max = 5)
    private Integer nota;
    @NotNull
    private UsuarioResponse usuarioOpinador;

    public OpiniaoResponse(Opiniao opiniao) {
        this.titulo = opiniao.getTitulo();
        this.nota = opiniao.getNota();
        this.usuarioOpinador = new UsuarioResponse(opiniao.getUsuarioOpinador());
    }

    /*
    Precisei criar os getters dessa classe porque em caso contrário, o jackson me devolvia 406
     */

    public String getTitulo() {
        return titulo;
    }

    public Integer getNota() {
        return nota;
    }

    public UsuarioResponse getUsuarioOpinador() {
        return usuarioOpinador;
    }
}
