package com.zupacademy.magno.mercadolivre.tentativapagamento;

import com.zupacademy.magno.mercadolivre.compra.Compra;
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

        Compra compra = verificaExistenciaDeCompraId(request, errors);
        if(compra == null) return;

        String statusTentativaRecebido = request.getStatusTentativa();
        HashMap<?, StatusTentativa> statusPossiveis = compra.getMetodoPagamento().getGateway().possiveisStatusDoGateway();
        if(!statusPossiveis.containsKey(statusTentativaRecebido)){
            errors.rejectValue("statusTentativa",
                    null,
                    "status de compra desconhecido para o método de pagamento: " + compra.getMetodoPagamento().toString());
        }

    }

    private Compra verificaExistenciaDeCompraId(TentativaPagamentoRequest request, Errors errors){
        Long compraId = request.getCompraId();
        Query query = manager.createQuery("select c from Compra c where c.id=:pCompraId");
        query.setParameter("pCompraId", compraId);
        List<?> comprasEncontradas = query.getResultList();

        if(comprasEncontradas.isEmpty()){
            errors.rejectValue("compraId", null, "Não existe compra no sistema com o id: " + compraId.toString());
            return null;
        }
        else{
            return (Compra) comprasEncontradas.get(0);
        }

    }
}
