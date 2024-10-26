package com.example.university.model;

import com.example.university.dto.TimelineRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "timeline")
@Table(name = "timeline")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean isActive;

    public Timeline(String name, Boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    public Timeline(TimelineRequestDTO data) {
        this.name = data.name();
        this.isActive = data.isActive();
    }
}
