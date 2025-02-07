package com.example.university.dto;

import com.example.university.model.SupportMaterial;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record SupportMaterialResponseDTO(
    Long id, String name, String autor, String link, String date, Long teamId) {

    public SupportMaterialResponseDTO(SupportMaterial material) {
        this(
            material.getId(),
            material.getName(),
            material.getAutor(), // O autor é o e-mail do usuário logado
            material.getLink(),
            formatDate(material.getDate()),
            material.getTeamId()
        );
    }

    private static String formatDate(LocalDate date) {
        if (date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
}
