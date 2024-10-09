package com.example.university.model;

import java.util.Date;

import com.example.university.dto.StudentRequestDTO;
import com.example.university.dto.TeacherRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "student")
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long registry;

    public Student(Long registry) {
        this.registry = registry;
    }

    public Student(StudentRequestDTO data) {
        this.registry = data.registry();
    }
}
