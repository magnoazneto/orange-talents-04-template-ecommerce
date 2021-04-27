package com.zupacademy.magno.mercadolivre.produto.cadastro;

import com.zupacademy.magno.mercadolivre.produto.ProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ProibeCaracteristicasComNomeIgualValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()) return;

        ProdutoRequest request = (ProdutoRequest) target;
        Set<String> nomesIguais = request.buscarCategoriasComNomeIgual();
        if(!nomesIguais.isEmpty()){
            errors.rejectValue("caracteristicas", null, "A Requisição contém características com nomes iguais: " + nomesIguais);
        }
    }
}
