package com.zupacademy.magno.mercadolivre.produto.cadastro;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.produto.ProdutoRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class CadastroProdutoController {

    @PersistenceContext
    EntityManager manager;

    @PostMapping
    @Transactional
    public String criaProduto(@RequestBody @Valid ProdutoRequest request){
        Produto novoProduto = request.toModel(manager);
        manager.persist(novoProduto);
        return request.toString();
    }
}
