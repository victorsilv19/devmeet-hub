package com.devmeethub.client;

/**
 * Record Imutável para capturar os dados devolvidos pela API pública do ViaCEP.
 */
public record ViaCepResponse(
    String cep,
    String logradouro,
    String complemento,
    String bairro,
    String localidade,
    String uf,
    String ibge,
    String gia,
    String ddd,
    String siafi,
    Boolean erro
) {}
