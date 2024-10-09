package com.example.university.model;

import java.util.Date;

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

@Entity(name = "teacher")
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date teachingSince;
    private Boolean isAdvisor;

    public Teacher(Date teachingSince, Boolean isAdvisor) {
        this.teachingSince = teachingSince;
        this.isAdvisor = isAdvisor;
    }

    public Teacher(TeacherRequestDTO data) {
        this.teachingSince = data.teachingSince();
        this.isAdvisor = data.isAdvisor();
    }
}
