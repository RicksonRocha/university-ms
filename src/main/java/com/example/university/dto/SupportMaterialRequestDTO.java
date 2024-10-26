package com.example.university.dto;

import java.io.File;

public record SupportMaterialRequestDTO(String name, String link, File document, String type) {

}