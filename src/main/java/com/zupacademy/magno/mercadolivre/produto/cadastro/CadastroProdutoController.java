package com.zupacademy.magno.mercadolivre.produto.cadastro;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produto")
public class CadastroProdutoController {

    @PersistenceContext
    EntityManager manager;

    @InitBinder(value = "produtoRequest")
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new ProibeCaracteristicasComNomeIgualValidator());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaProduto(@RequestBody @Valid ProdutoRequest request, @AuthenticationPrincipal Usuario usuario){
        Produto novoProduto = request.toModel(manager, usuario);
        manager.persist(novoProduto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/{id}/imagens")
    public String adicionaImagens(@Valid ImagensRequest request, @PathVariable("id") Long id){
        return "criando imagem...";
    }
}
