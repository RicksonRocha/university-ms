package com.example.university.dto;

import com.example.university.model.MemberInfo;
import java.util.List;

public record TccRequestDTO(
                String name,
                String description,
                Boolean isActive,
                Long teacherTcc, // Campo para o orientador
                List<MemberInfo> members, // Lista de integrantes da equipe
                List<String> themes, // Lista de temas do TCC
                Long createdById,
                String createdByEmail) {
}
