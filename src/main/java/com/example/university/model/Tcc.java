package com.example.university.model;

import java.util.Date;

import com.example.university.dto.TccRequestDTO;
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

@Entity(name = "tcc")
@Table(name = "tcc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tcc {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Boolean isActive;

    public Tcc(String name,
            String description,
            Boolean isActive) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

    public Tcc(TccRequestDTO data) {
        this.name = data.name();
        this.description = data.description();
    }
}
