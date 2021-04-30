package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping
@Validated
public class TentativaPagamentoController {

    @PostMapping("/compra/{id}/retorno")
    @Transactional
    public String retornaPagamento(@RequestBody @Valid TentativaPagamentoRequest request,
                                   @PathVariable @ExistsValue(
                                           targetClass = Compra.class,
                                           fieldName = "id",
                                           message = "Compra não encontrada para esse ID."
                                   ) Long id){

        return "recebendo retorno de pagamento";
    }
}
