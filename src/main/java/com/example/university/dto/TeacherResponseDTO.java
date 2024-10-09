package com.example.university.dto;

import java.util.Date;

import com.example.university.model.Teacher;

public record TeacherResponseDTO(
        Long id, Date teachingSince, Boolean isAdvisor) {

    public TeacherResponseDTO(Teacher teacher) {
        this(teacher.getId(), teacher.getTeachingSince(), teacher.getIsAdvisor());
    }
}
