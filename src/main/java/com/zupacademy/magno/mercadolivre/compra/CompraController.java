package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.email.EnviadorEmail;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping
@Validated
public class CompraController {

    @Autowired
    EntityManager manager;

    @Autowired
    EnviadorEmail enviadorEmail;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(new ProibeMetodoDePagamentoNaoCadastradoValidator());
    }

    @PostMapping("/produto/{id}/compra")
    @Transactional
    public ResponseEntity<?> criaCompra(
            @RequestBody @Valid NovaCompraRequest request,
            @AuthenticationPrincipal Usuario comprador,
            @PathVariable @ExistsValue(
                    targetClass = Produto.class,
                    fieldName = "id",
                    message = "Produto não encontrado para esse ID") Long id
            ){

        // existentia de ID validada na @ExistsValue
        Produto produto = manager.find(Produto.class, id);
        boolean estoqueAbatido = produto.abaterEstoque(request.getQuantidade());
        if(!estoqueAbatido) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade inválida para o estoque atual.");
        }

        Compra novaCompra = request.toModel(produto, comprador);
        manager.persist(novaCompra);

        enviadorEmail.enviaEmail(
                produto.getUsuarioCriador().getLogin(),
                "Nova solicitação de compra recebida",
                "Oi! Seu produto \"" + produto.getNome() +
                        " acabou de receber uma nova intenção de compra." +
                        " a quantidade foi de " + novaCompra.getQuantidade().toString() +
                        " no valor unitário de " + novaCompra.getValorCorrente().toString() +
                        " pelo usuário: " + comprador.getLogin() +
                        ". O comprador recebrá um link para realizar o pagamento pelo método escolhido, que nesse caso foi: " +
                        novaCompra.getMetodoPagamento().toString()
        );

        // essa urlRetornoProvisoria acredito eu que será implementada na parte 2
        String redirectUrl = novaCompra.getMetodoPagamento().getGateway().buildUrl(novaCompra.getUuid(), "urlRetornoProvisoria");
        return ResponseEntity.status(302).body(redirectUrl);
    }
}
