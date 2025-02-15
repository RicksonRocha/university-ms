package com.example.university.dto.tcc;

import java.util.List;

public record TccRequestDTO(
        String name,
        String description,
        Boolean isActive,
        String teacherTcc, // Campo para o orientador
        List<String> members, // Lista de integrantes da equipe
        List<String> themes // Lista de temas do TCC
) {
}
