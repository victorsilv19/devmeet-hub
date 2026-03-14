package com.devmeethub.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Cliente de Integração. Encapsula toda lógida de ir até a internet buscar dados.
 */
@Component
public class ViaCepClient {

    private final RestTemplate restTemplate;
    
    @Value("${viacep.api.url}")
    private String viaCepUrl;

    public ViaCepClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Consome a API REST externa do ViaCEP
     * Retorna Data Transfer Object populado ou objeto indicando erro
     */
    public ViaCepResponse fetchAddressByCep(String cep) {
        // Limpa formatação caso venha com traço: 01001-000 -> 01001000
        String cleanCep = cep.replaceAll("[^\\d]", "");
        
        String url = String.format("%s/%s/json", viaCepUrl, cleanCep);
        
        try {
            return restTemplate.getForObject(url, ViaCepResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao comunicar com a API do ViaCEP", e);
        }
    }
}
