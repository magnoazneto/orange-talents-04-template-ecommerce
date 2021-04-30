package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.utils.validations.ExistsValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping
@Validated
public class TentativaPagamentoController {

    @Autowired
    EntityManager manager;

    @PostMapping("/compra/{id}/retorno")
    @Transactional
    public ResponseEntity<?> retornaPagamento(@RequestBody @Valid TentativaPagamentoRequest request,
                                           @PathVariable @ExistsValue(
                                           targetClass = Compra.class,
                                           fieldName = "id",
                                           message = "Compra não encontrada para esse ID."
                                   ) Long id){

        /*
        0 - Proteção de borda: o statusCompra da request deve ser válido!

        1 - Recuperar Compra OK
        2 - Recuperar meio de pagamento OK
        3 - Fazer o match do status da Tentativa recebido com o status padrão do sistema OK
        4 - Passar estado da compra para CONCLUIDO se possivel
        5 - Se sucesso, realizar chamadas para sistemas de NF e ranking
        6 - Enviar emails para NF e ranking
        7 - Se falha, enviar email para comprador
         */

        Compra compra = manager.find(Compra.class, id);
        MetodoPagamento metodoPagamento = compra.getMetodoPagamento();
        TentativaPagamento tentativaPagamento = request.toModel(compra);
        manager.persist(tentativaPagamento);

        if(metodoPagamento.avaliaTentativaPagamento(request.getStatusTentativa())){
            // atualizar o status da compra para CONCLUIDO
            compra.concluirCompra();
            // realizar chamadas para NF e ranking

            // enviar email
        }
        else{
            // enviar um email com link de nova tentativa para o cliente
        }


        // devolver o status do pagamento aqui no body com 200
        return ResponseEntity.ok().build();
    }
}
