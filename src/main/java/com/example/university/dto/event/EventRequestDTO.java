package com.example.university.dto.event;

import java.util.Date;

// Recebe do frontend apenas os dados necessários para criar/editar um evento
public record EventRequestDTO(String name, String description, Date startDate, Date endDate, Boolean isActive,
        String team) {

}