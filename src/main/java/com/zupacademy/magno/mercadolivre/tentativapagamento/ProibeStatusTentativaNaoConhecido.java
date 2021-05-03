package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@Component
public class ProibeStatusTentativaNaoConhecido implements Validator {

    @Autowired
    EntityManager manager;

    @Override
    public boolean supports(Class<?> aClass) {
        return TentativaPagamentoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) return;

        TentativaPagamentoRequest request = (TentativaPagamentoRequest) target;

        Compra compra = recuperaCompra(request, errors);
        if(compra == null) return;

        String statusTentativaRecebido = request.getStatusTentativa();
        MetodoPagamento metodoPagamento = compra.getMetodoPagamento();

        if(!metodoPagamento.contemStatus(statusTentativaRecebido)){
            errors.rejectValue("statusTentativa",
                    null,
                    "status de compra desconhecido para o método de pagamento: " + compra.getMetodoPagamento().toString());
        }

    }

    private Compra recuperaCompra(TentativaPagamentoRequest request, Errors errors){
        Long compraId = request.getCompraId();
        Query query = manager.createQuery("select c from Compra c where c.id=:pCompraId");
        query.setParameter("pCompraId", compraId);
        List<?> comprasEncontradas = query.getResultList();

        if(comprasEncontradas.isEmpty()){ // proteção extra. Existência do ID já validada na classe de Request
            errors.rejectValue("compraId", null, "Não existe compra no sistema com o id: " + compraId.toString());
            return null;
        }
        else{
            return (Compra) comprasEncontradas.get(0);
        }

    }
}
