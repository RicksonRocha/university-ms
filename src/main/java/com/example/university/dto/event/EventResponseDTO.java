package com.example.university.dto.event;

import java.util.Date;

import com.example.university.model.Event;

public record EventResponseDTO(Long id, String name, String description, Date startDate, Date endDate,
        Boolean isActive) {

    public EventResponseDTO(Event event) {
        this(event.getId(), event.getName(), event.getDescription(), event.getStartDate(), event.getEndDate(),
                event.getIsActive());
    }
}
