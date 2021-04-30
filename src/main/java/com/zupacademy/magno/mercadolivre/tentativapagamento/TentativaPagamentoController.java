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

        /*
        1 - Recuperar Compra
        2 - Recuperar meio de pagamento
        3 - Fazer o match do status da Tentativa recebido com o status padrão do sistema
        4 - Passar estado da compra para CONCLUIDO se possivel
        5 - Se sucesso, realizar chamadas para sistemas de NF e ranking
        6 - Enviar emails para NF e ranking
        7 - Se falha, enviar email para comprador
         */
        return "recebendo retorno de pagamento";
    }
}
