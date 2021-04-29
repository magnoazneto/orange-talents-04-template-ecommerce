package com.zupacademy.magno.mercadolivre.compra;

import com.zupacademy.magno.mercadolivre.compra.gateways.MetodoPagamento;
import com.zupacademy.magno.mercadolivre.produto.cadastro.ProdutoRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;
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
        Set<String> metodosPagamento = Stream.of(MetodoPagamento.values())
                .map(MetodoPagamento::name)
                .collect(Collectors.toSet());

        String metodoSelecionado = request.getMetodoPagamento();
        if(!metodosPagamento.contains(metodoSelecionado)){
            errors.rejectValue("metodoPagamento", null, "Não existe método de pagamento cadastrado para o valor informado: " + metodoSelecionado);
        }
    }
}
