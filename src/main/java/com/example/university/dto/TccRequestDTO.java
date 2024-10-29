package com.example.university.dto;

import java.util.List;

public record TccRequestDTO(
    String name, 
    String description, 
    Boolean isActive, 
    String orientador, // Campo para o orientador
    List<String> integrantes, // Lista de integrantes da equipe
    List<String> temas // Lista de temas do TCC
) {}

