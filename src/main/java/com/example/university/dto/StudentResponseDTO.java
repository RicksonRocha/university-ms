package com.example.university.dto;

import com.example.university.model.Student;

public record StudentResponseDTO(Long id, Long registry) {

    public StudentResponseDTO(Student student) {
        this(student.getId(), student.getRegistry());
    }
}
