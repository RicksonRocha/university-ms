package com.example.university.dto;

import java.io.File;
import com.example.university.model.SupportMaterial;

public record SupportMaterialResponseDTO(
        Long id, String name, String autor, File document, String type) {

    public SupportMaterialResponseDTO(SupportMaterial supportMaterial) {
        this(supportMaterial.getId(), supportMaterial.getName(), supportMaterial.getAutor(),
                supportMaterial.getDocument(), supportMaterial.getType());
    }
}