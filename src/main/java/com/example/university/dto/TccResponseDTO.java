package com.example.university.dto;

import java.util.List;
import com.example.university.model.Tcc;
import com.example.university.model.MemberInfo;

public record TccResponseDTO(
        Long id,
        String name,
        String description,
        Boolean isActive,
        Long teacherTcc,
        List<MemberInfo> members,
        List<String> themes,
        Long createdById,
        String createdByEmail) {
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
                tcc.getCreatedByEmail());
    }
}
