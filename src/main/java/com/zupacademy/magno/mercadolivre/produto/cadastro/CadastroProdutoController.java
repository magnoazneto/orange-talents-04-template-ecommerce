package com.zupacademy.magno.mercadolivre.produto.cadastro;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.produto.cadastro.imagens.ImagensRequest;
import com.zupacademy.magno.mercadolivre.produto.cadastro.imagens.UploaderFalso;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/produto")
public class CadastroProdutoController {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    private UploaderFalso uploaderFalso;

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
    @Transactional
    public String adicionaImagens(@Valid ImagensRequest request, @PathVariable("id") Long id){
        Set<String> links = uploaderFalso.envia(request.getImagens());
        Produto produto = manager.find(Produto.class, id);
        produto.associaLinks(links);
        manager.merge(produto);

        return produto.toString();
    }
}
