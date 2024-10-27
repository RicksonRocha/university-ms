package com.example.university.dto;

import java.util.List;
import com.example.university.model.Tcc;

public record TccResponseDTO(
    Long id, 
    String name, 
    String description, 
    Boolean isActive, 
    String orientador, // Campo para o orientador
    List<String> integrantes // Lista de integrantes da equipe
) {
    public TccResponseDTO(Tcc tcc) {
        this(
            tcc.getId(), 
            tcc.getName(), 
            tcc.getDescription(), 
            tcc.getIsActive(), 
            tcc.getOrientador(), 
            tcc.getIntegrantes()
        );
    }
}

