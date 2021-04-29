package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProibeMetodoDePagamentoNaoCadastradoValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return NovaCompraRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) return;

        NovaCompraRequest request = (NovaCompraRequest) target;
        if(!request.metodoPagamentoValido()){
            errors.rejectValue("metodoPagamento", null, "Não existe método de pagamento cadastrado para o valor informado: " + request.getMetodoPagamento());
        }
    }
}
