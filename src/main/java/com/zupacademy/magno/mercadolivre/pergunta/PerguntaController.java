package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produto/pergunta")
public class PerguntaController {

    @Autowired
    EntityManager manager;

    @Autowired
    EnviadorEmail enviadorEmail;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaPergunta(@RequestBody @Valid PerguntaRequest request, @AuthenticationPrincipal Usuario criadorPergunta){
        Produto produtoAlvo = manager.find(Produto.class, request.getProdutoId());
        Pergunta novaPergunta = request.toModel(criadorPergunta, produtoAlvo);

        manager.persist(novaPergunta);
        String emailEnviado = enviadorEmail.enviaEmail(
                produtoAlvo.getUsuarioCriador(),
                produtoAlvo,
                novaPergunta
        );

        return ResponseEntity.ok().body(emailEnviado);
    }

}
