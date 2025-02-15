package com.example.university.dto;

import java.util.List;
import com.example.university.model.Tcc;

public record TccResponseDTO(
    Long id, 
    String name, 
    String description, 
    Boolean isActive, 
    String teacherTcc, 
    List<String> members,
    List<String> themes,
    Long createdById,
    String createdByEmail
) {
    public TccResponseDTO(Tcc tcc) {
        this(
            tcc.getId(), 
            tcc.getName(), 
            tcc.getDescription(), 
            tcc.getIsActive(), 
            tcc.getTeacherTcc(), 
            tcc.getMembers(),
            tcc.getThemes(),
            tcc.getCreatedById(),
            tcc.getCreatedByEmail()
        );
    }
}

