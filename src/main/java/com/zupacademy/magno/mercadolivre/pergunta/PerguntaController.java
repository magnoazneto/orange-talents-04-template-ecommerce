package com.zupacademy.magno.mercadolivre.pergunta;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.utils.email.EnviadorEmail;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping
@Validated
public class PerguntaController {

    @Autowired
    EntityManager manager;

    @Autowired
    EnviadorEmail enviadorEmail;

    @PostMapping(value = "/produto/{id}/pergunta")
    @Transactional
    public ResponseEntity<?> criaPergunta(@RequestBody @Valid PerguntaRequest request,
                                          @PathVariable @ExistsValue(fieldName = "id", targetClass = Produto.class, message = "Produto não existente para esse Id.") Long id,
                                          @AuthenticationPrincipal Usuario criadorPergunta){
        Produto produtoAlvo = manager.find(Produto.class, id);
        Pergunta novaPergunta = request.toModel(criadorPergunta, produtoAlvo);

        manager.persist(novaPergunta);

        enviadorEmail.enviaEmail(
                produtoAlvo.getUsuarioCriador().getLogin(),
                "Nova pergunta recebida",
                "você recebeu uma nova pergunta no produto " + produtoAlvo.getNome()
                + " - Pergunta: " + novaPergunta.getTitulo()
                + " Acesse sua conta para responder!"
        );

        return ResponseEntity.ok(new PerguntaResponse(novaPergunta, criadorPergunta));
    }

}
