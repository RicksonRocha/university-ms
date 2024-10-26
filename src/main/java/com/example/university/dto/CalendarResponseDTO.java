package com.example.university.dto;

import com.example.university.model.Calendar;

public record CalendarResponseDTO(Long id, String name, Boolean isActive) {

    public CalendarResponseDTO(Calendar calendar) {
        this(calendar.getId(), calendar.getName(), calendar.getIsActive());
    }
}
