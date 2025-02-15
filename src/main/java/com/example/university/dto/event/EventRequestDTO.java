package com.example.university.dto.event;

import java.util.Date;

public record EventRequestDTO(String name, String description, Date startDate, Date endDate, Boolean isActive) {

}