package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.compra.StatusCompra;
import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.sistemasexternos.NotaFiscalService;
import com.zupacademy.magno.mercadolivre.sistemasexternos.RankingService;
import com.zupacademy.magno.mercadolivre.utils.email.EnviadorEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @Autowired
    NotaFiscalService notaFiscalService;

    @Autowired
    RankingService rankingService;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(proibeStatusTentativaNaoConhecido);
    }

    @PostMapping("/compra/retorno")
    @Transactional
    public ResponseEntity<?> retornaPagamento(@RequestBody @Valid TentativaPagamentoRequest request){
        Compra compra = manager.find(Compra.class, request.getCompraId());
        MetodoPagamento metodoPagamento = compra.getMetodoPagamento();

        if(metodoPagamento.tentativaBemSucedida(request.getStatusTentativa())){ // tentativa SUCESSO
            compra.concluirCompra();
            notaFiscalService.processa(compra);
            rankingService.processa(compra);
            email.enviaEmail(compra.getProduto().getUsuarioCriador().getLogin(),
                    "Sua venda foi concluída",
                    "Olá! Sua venda de id número " + compra.getId().toString()
                    + " foi concluída com sucesso. A nota fiscal foi gerada e seu ranking atualizado.");
        }
        else{ // tentativa FALHA
            if(compra.getStatus().equals(StatusCompra.CONCLUIDA)){ // fail fast
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "A tentativa de pagamento deu FALHA, mas essa compra já está marcada como CONCLUÍDA. Não há mais o que se fazer.");
            }
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
