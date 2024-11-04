package com.example.university.dto;

import com.example.university.model.SupportMaterial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record SupportMaterialResponseDTO(
        Long id, String name, String autor, String link, String date, Long teamId) {

    public SupportMaterialResponseDTO(SupportMaterial supportMaterial) {
        this(
            supportMaterial.getId(),
            supportMaterial.getName(),
            supportMaterial.getAutor(),
            supportMaterial.getLink(),
            formatDate(supportMaterial.getDate()),
            supportMaterial.getTeamId()
        );
    }

    private static String formatDate(LocalDate date) {
        if (date == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
}
