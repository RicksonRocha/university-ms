package com.example.university.dto;

import java.util.List;

public record TccRequestDTO(
    String name, 
    String description, 
    Boolean isActive, 
    String orientador, // Novo campo para o orientador
    List<String> integrantes // Lista de integrantes da equipe
) {}
