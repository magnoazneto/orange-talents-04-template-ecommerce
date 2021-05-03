package com.zupacademy.magno.mercadolivre.sistemasexternos;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
public class SistemasFakesControllers {

    @PostMapping("/notas-fiscais")
    public void criaNota(@RequestBody @Valid NotaFiscalRequest request) throws InterruptedException {
        System.out.println("Criando nota: " + request.toString());
        Thread.sleep(150);
    }

    @PostMapping("/ranking")
    public void atualizaRanking(@RequestBody @Valid RankingRequest request) throws InterruptedException {
        System.out.println("Atualizando Ranking: " + request.toString());
        Thread.sleep(150);
    }
}
