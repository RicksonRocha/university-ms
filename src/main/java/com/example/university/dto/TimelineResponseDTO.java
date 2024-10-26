package com.example.university.dto;

import com.example.university.model.Timeline;

public record TimelineResponseDTO(
        Long id, String name, Boolean isActive) {

    public TimelineResponseDTO(Timeline timeline) {
        this(timeline.getId(), timeline.getName(), timeline.getIsActive());
    }
}
