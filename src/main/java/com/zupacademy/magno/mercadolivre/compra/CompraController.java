package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping
@Validated
public class CompraController {

    @Autowired
    EntityManager manager;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new ProibeMetodoDePagamentoNaoCadastradoValidator());
    }

    @PostMapping("/produto/{id}/compra")
    @Transactional
    public String criaCompra(
            @RequestBody @Valid NovaCompraRequest request,
            @AuthenticationPrincipal Usuario comprador,
            @PathVariable @ExistsValue(
                    targetClass = Produto.class,
                    fieldName = "id",
                    message = "Produto não encontrado para esse ID") Long id
            ){

        /*
        1 - Recuperar produto OK
        2 - abater estoque OK
        3 - identificar gateway OK
        4 - gerar compra OK
        5 - enviar email
        6 - redirecionar
         */

        // existentia de ID validada na @ExistsValue
        Produto produto = manager.find(Produto.class, id);
        boolean estoqueAbatido = produto.abaterEstoque(request.getQuantidade());
        if(!estoqueAbatido) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade inválida para o estoque atual.");
        }

        Compra novaCompra = request.toModel(produto, comprador);
        manager.persist(novaCompra);
        // enviar email aqui!
        return novaCompra.toString();
    }
}
