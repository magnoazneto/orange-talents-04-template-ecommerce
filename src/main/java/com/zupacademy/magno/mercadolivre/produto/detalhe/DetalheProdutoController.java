package com.zupacademy.magno.mercadolivre.produto.detalhe;

import com.zupacademy.magno.mercadolivre.produto.Produto;
import com.zupacademy.magno.mercadolivre.produto.ProdutoRepository;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
@Validated
public class DetalheProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping("/produto/{id}")
    public ResponseEntity<DetalhaProdutoResponse> detalhaProduto(
            @PathVariable @ExistsValue(
                    targetClass = Produto.class,
                    fieldName = "id",
                    message = "Não existe produto cadastrado com esse Id.") Long id){

        Optional<Produto> produto = produtoRepository.findById(id);

        return ResponseEntity.ok(new DetalhaProdutoResponse(produto.get(), produtoRepository));
    }
}
