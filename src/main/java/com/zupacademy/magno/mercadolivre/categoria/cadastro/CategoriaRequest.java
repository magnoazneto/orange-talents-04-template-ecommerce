package com.zupacademy.magno.mercadolivre.categoria.cadastro;

import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import com.zupacademy.magno.mercadolivre.utils.validations.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class CategoriaRequest {

    @NotBlank
    @UniqueValue(targetClass = Categoria.class, fieldName = "nome", message = "Nome da Categoria deve ser único.")
    private final String nome;
    @ExistsValue(targetClass = Categoria.class, fieldName = "id", required = false, message = "Não existe Categoria cadastrada com esse id")
    private Long categoriaMaeId;

    public CategoriaRequest(String nome) {
        this.nome = nome;
    }

    public CategoriaRequest(String nome, Long categoriaMaeId) {
        this.nome = nome;
        this.categoriaMaeId = categoriaMaeId;
    }

    public String getNome() {
        return nome;
    }

    public Long getCategoriaMaeId() {
        return categoriaMaeId;
    }

    @Override
    public String toString() {
        return "CategoriaRequest{" +
                "nome='" + nome + '\'' +
                ", categoriaMaeId=" + categoriaMaeId +
                '}';
    }

    public Categoria toModel(EntityManager manager) {
        Categoria novaCategoria = new Categoria(this.nome);

        if(categoriaMaeId != null){
            Categoria categoriaMae = manager.find(Categoria.class, categoriaMaeId);
            Assert.notNull(categoriaMae, "Categoria não deveria ser nula.");
            novaCategoria.setCategoriaMae(categoriaMae);
        }
        return novaCategoria;
    }
}
