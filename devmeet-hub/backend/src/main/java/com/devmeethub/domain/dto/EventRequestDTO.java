package com.devmeethub.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * Padrão DTO usando Record (Java 14+) - Imutável e seguro.
 * Validações de entrada aplicadas diretamente nos atributos.
 */
public record EventRequestDTO(
        @NotBlank(message = "O título é obrigatório.")
        String title,

        String description,

        @NotNull(message = "A data não pode ser nula.")
        @Future(message = "O evento deve ocorrer no futuro.")
        LocalDateTime eventDate,

        @NotBlank(message = "O CEP é obrigatório.")
        String cep
) {}
