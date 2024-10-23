package com.tcc.betaservice.controller;

import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class BetaController {

    final RestTemplate restTemplate;

    public BetaController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @GetMapping("/beta")
    @Observed(
            name = "user.name",
            contextualName = "beta-->gama",
            lowCardinalityKeyValues = {"userType", "userType2"}
    )
    public String comunicacaoModulos(){
        log.info("Serviço Beta foi chamado");
        log.info("Serviço chamando o Serviço gama");
        ResponseEntity<String> response = restTemplate.exchange(
                "http://localhost:5050/gama-svc/gama",
                HttpMethod.GET,
                null,
                String.class
        );
        return "Comunicação entre os serviços(Beta): "+response.getBody();    }
}
