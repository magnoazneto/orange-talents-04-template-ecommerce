package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.utils.email.EnviadorEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping
public class TentativaPagamentoController {

    @Autowired
    ProibeStatusTentativaNaoConhecido proibeStatusTentativaNaoConhecido;

    @Autowired
    EnviadorEmail email;

    @Autowired
    EntityManager manager;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(proibeStatusTentativaNaoConhecido);
    }

    @PostMapping("/compra/retorno")
    @Transactional
    public ResponseEntity<?> retornaPagamento(@RequestBody @Valid TentativaPagamentoRequest request){

        /*
        0 - Proteção de borda: o statusCompra da request deve ser válido! OK
        1 - Recuperar Compra OK
        2 - Recuperar meio de pagamento OK
        3 - Fazer o match do status da Tentativa recebido com o status padrão do sistema OK
        4 - Passar estado da compra para CONCLUIDO se possivel OK
        5 - Se sucesso, realizar chamadas para sistemas de NF e ranking
        6 - Enviar emails para NF e ranking
        7 - Se falha, enviar email para comprador OK
         */

        Compra compra = manager.find(Compra.class, request.getCompraId());
        MetodoPagamento metodoPagamento = compra.getMetodoPagamento();

        if(metodoPagamento.avaliaTentativaPagamento(request.getStatusTentativa())){
            compra.concluirCompra();
            // realizar chamadas para NF e ranking
            /*
            aqui poderia-se realizar as chamadas com o Feign para endpoints fake
             */

            // enviar email
        }
        else{
            String linkPagamento = metodoPagamento.getGateway().buildUrl(compra);
            email.enviaEmail(
                    compra.getComprador().getLogin(),
                    "Tentativa de pagamento recusada",
                    "A tentativa de pagamento utilizando " + metodoPagamento.toString() +
                            " falhou. Por favor, tente novamente com o seguinte link: " +
                            linkPagamento);
        }

        TentativaPagamento tentativaPagamento = request.toModel(compra);
        manager.persist(tentativaPagamento);

        return ResponseEntity.ok().body(new TentativaPagamentoResponse(tentativaPagamento));
    }
}
