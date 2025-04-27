package com.example.university.model;

import java.util.Date;

import com.example.university.dto.event.EventRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "event")
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Boolean isActive;
    private String team;

    public Event(String name, String description, Date startDate, Date endDate, Boolean isActive, String team) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.team = team;
    }

    public Event(EventRequestDTO data) {
        this.name = data.name();
        this.description = data.description();
        this.startDate = data.startDate();
        this.endDate = data.endDate();
        this.isActive = data.isActive();
        this.team = data.team();
    }
}
