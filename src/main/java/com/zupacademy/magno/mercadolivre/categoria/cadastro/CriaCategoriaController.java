package com.zupacademy.magno.mercadolivre.categoria.cadastro;

import com.zupacademy.magno.mercadolivre.categoria.Categoria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/categoria")
public class CriaCategoriaController {

    @PersistenceContext
    EntityManager manager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaCategoria(@RequestBody @Valid CategoriaRequest request){
        Categoria novaCategoria = request.toModel(manager);
        manager.persist(novaCategoria);
        return ResponseEntity.ok().build();
    }
}
