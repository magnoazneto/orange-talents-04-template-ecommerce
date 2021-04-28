package com.zupacademy.magno.mercadolivre.opiniao;

import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/produto/opiniao")
public class OpiniaoController {

    @Autowired
    EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<OpiniaoResponse> criaOpiniao(@RequestBody @Valid OpiniaoRequest request, @AuthenticationPrincipal Usuario usuarioOpinador){
        Opiniao opiniao = request.toModel(manager, usuarioOpinador);
        manager.persist(opiniao);
        return ResponseEntity.ok().body(new OpiniaoResponse(opiniao));
    }
}
