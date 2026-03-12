package com.devmeethub.service;

import com.devmeethub.client.ViaCepClient;
import com.devmeethub.client.ViaCepResponse;
import com.devmeethub.domain.dto.EventRequestDTO;
import com.devmeethub.domain.dto.EventResponseDTO;
import com.devmeethub.domain.entity.Event;
import com.devmeethub.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Layer: Onde as regras de negócio devem viver.
 * Mantém Controllers "magros" e lida com Orquestração, APIs externas e persistência.
 */
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ViaCepClient viaCepClient;

    @Transactional
    public EventResponseDTO createEvent(EventRequestDTO dto) {
        
        // 1. Busca os dados externos na API (Ponto extra em Entrevistas de Emprego)
        ViaCepResponse addressInfo = viaCepClient.fetchAddressByCep(dto.cep());

        if (addressInfo != null && Boolean.TRUE.equals(addressInfo.erro())) {
             throw new IllegalArgumentException("CEP inválido ou inexistente no banco dos correios.");
        }

        // 2. Constrói o Objeto (Uso de Padrão Builder gerado pelo Lombok)
        Event newEvent = Event.builder()
                .title(dto.title())
                .description(dto.description())
                .eventDate(dto.eventDate())
                .cep(dto.cep())
                .street(addressInfo != null ? addressInfo.logradouro() : "Não encontrado")
                .neighborhood(addressInfo != null ? addressInfo.bairro() : "Não encontrado")
                .city(addressInfo != null ? addressInfo.localidade() : "Não encontrado")
                .state(addressInfo != null ? addressInfo.uf() : "NA")
                .build();

        // 3. Salva no banco via Spring Data JPA
        Event savedEvent = eventRepository.save(newEvent);

        // 4. Retorna DTO protegendo os dados
        return EventResponseDTO.fromEntity(savedEvent);
    }

    @Transactional(readOnly = true)
    public List<EventResponseDTO> findAll() {
        return eventRepository.findAll()
                .stream()
                .map(EventResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventResponseDTO findById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado (ID: " + id + ")"));

        return EventResponseDTO.fromEntity(event);
    }

    @Transactional
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Não é possível deletar, evento não encontrado.");
        }
        eventRepository.deleteById(id);
    }
}
