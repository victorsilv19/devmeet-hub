package com.devmeethub.domain.dto;

import com.devmeethub.domain.entity.Event;

import java.time.LocalDateTime;

/**
 * Record Imutável para resposta. Oculta dados irrelevantes da Entidade.
 * O DTO prevê que o endereço já tenha sido formatado pelo Backend na devolução.
 */
public record EventResponseDTO(
        Long id,
        String title,
        String description,
        LocalDateTime eventDate,
        String fullAddress
) {
    /**
     * Padrão Factory - Converte de Entity para DTO.
     */
    public static EventResponseDTO fromEntity(Event event) {
        String address = String.format("%s - %s, %s - %s (CEP: %s)",
                event.getStreet(),
                event.getNeighborhood(),
                event.getCity(),
                event.getState(),
                event.getCep()
        );

        return new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getEventDate(),
                address
        );
    }
}
