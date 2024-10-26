package com.example.university.dto;

import com.example.university.model.Tcc;

public record TccResponseDTO(Long id, String name, String description, Boolean isActive) {

    public TccResponseDTO(Tcc tcc) {
        this(tcc.getId(), tcc.getName(), tcc.getDescription(), tcc.getIsActive());
    }
}
