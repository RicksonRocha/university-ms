package com.example.university.dto;

import com.example.university.model.Team;

public record TeamResponseDTO(
        Long id, Boolean isActive) {

    public TeamResponseDTO(Team team) {
        this(team.getId(), team.getIsActive());
    }
}
