package com.example.university.dto;

import java.io.File;

public record SupportMaterialRequestDTO(String name, String autor, File document, String type) {
}
