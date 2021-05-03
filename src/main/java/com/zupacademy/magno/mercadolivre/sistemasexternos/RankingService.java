package com.zupacademy.magno.mercadolivre.sistemasexternos;

import com.zupacademy.magno.mercadolivre.compra.Compra;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RankingService {


    public void processa(Compra compra){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),
                "idComprador", compra.getComprador().getId());


        restTemplate.postForEntity("http://localhost:8080/notas-fiscais",
                request, String.class);
    }
}
