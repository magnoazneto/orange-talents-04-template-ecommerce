package com.zupacademy.magno.mercadolivre.produto.cadastro;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.produto.ProdutoRequest;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/produto")
public class CadastroProdutoController {

    @PersistenceContext
    EntityManager manager;

    @PostMapping
    @Transactional
    public String criaProduto(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario){
        Produto novoProduto = request.toModel(manager, usuario);
        manager.persist(novoProduto);
        return request.toString();
    }
}
