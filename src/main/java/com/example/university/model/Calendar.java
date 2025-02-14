package com.example.university.model;

import java.util.Date;

import com.example.university.dto.CalendarRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "calendar")
@Table(name = "calendar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean isActive;

    public Calendar(String name, Boolean isActive) {
        this.name = name;
        this.isActive = isActive;
    }

    public Calendar(CalendarRequestDTO data) {
        this.name = data.name();
        this.isActive = data.isActive();
    }
}
