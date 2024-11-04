package com.example.university.model;

import java.time.LocalDate;
import com.example.university.dto.SupportMaterialRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "supportmaterial")
@Table(name = "supportmaterial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String autor;
    private String link;
    private LocalDate date;
    private Long teamId;

    public SupportMaterial(String name, String autor, String link, Long teamId) {
        this.name = name;
        this.autor = autor;
        this.link = link;
        this.date = LocalDate.now();
        this.teamId = teamId != null ? teamId : 1L;
    }

    public SupportMaterial(SupportMaterialRequestDTO data) {
        this.name = data.name();
        this.autor = data.autor();
        this.link = data.link();
        this.date = LocalDate.now();
        this.teamId = data.teamId() != null ? data.teamId() : 1L;
    }
}
