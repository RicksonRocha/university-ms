package com.example.university.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.university.dto.event.EventRequestDTO;
import com.example.university.dto.event.EventResponseDTO;
import com.example.university.model.Event;
import com.example.university.repository.EventRepository;

@RestController
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAll() {
        List<EventResponseDTO> eventList = eventRepository.findAll().stream()
                .map(EventResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getStudentById(@PathVariable Long id) {
        Optional<Event> event = eventRepository.findById(id);

        if (event.isPresent()) {
            EventResponseDTO responseDTO = new EventResponseDTO(event.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<EventResponseDTO> saveEvent(@RequestBody EventRequestDTO data) {
        Event eventData = new Event(data);
        eventData = eventRepository.save(eventData);
        EventResponseDTO responseDTO = new EventResponseDTO(eventData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable Long id,
            @RequestBody EventRequestDTO data) {
        Optional<Event> existingEvent = eventRepository.findById(id);

        if (existingEvent.isPresent()) {
            Event event = existingEvent.get();

            event.setName(data.name());
            event.setDescription(data.description());
            event.setStartDate(data.startDate());
            event.setEndDate(data.endDate());
            event.setIsActive(data.isActive());

            eventRepository.save(event);

            EventResponseDTO responseDTO = new EventResponseDTO(event);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<EventResponseDTO> deleteEvent(@PathVariable Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            eventRepository.delete(event);
            EventResponseDTO responseDTO = new EventResponseDTO(event);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
