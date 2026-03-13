package com.devmeethub.controller;

import com.devmeethub.domain.dto.EventRequestDTO;
import com.devmeethub.domain.dto.EventResponseDTO;
import com.devmeethub.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller Layer: Apenas interage com o Service.
 * Define os Endpoints HTTP: /api/events
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponseDTO> create(@Valid @RequestBody EventRequestDTO requestDTO) {
        EventResponseDTO created = eventService.createEvent(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created); // 201 CREATED
    }

    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> listAll() {
        return ResponseEntity.ok(eventService.findAll()); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(eventService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build(); // 204 NO CONTENT
    }
}
