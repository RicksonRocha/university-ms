package com.example.university.model;

import java.util.List;

import com.example.university.dto.TccRequestDTO;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
    private String teacherTcc;

    @ElementCollection
    @CollectionTable(name = "tcc_members", joinColumns = @JoinColumn(name = "tcc_id"))
    @Column(name = "member")
    private List<String> members;

    @ElementCollection
    @CollectionTable(name = "tcc_themes", joinColumns = @JoinColumn(name = "tcc_id"))
    @Column(name = "theme")
    private List<String> themes;

    // NOVOS CAMPOS para identificar o criador da equipe
    private Long createdById;
    private String createdByEmail;

    public Tcc(String name, String description, Boolean isActive, String teacherTcc, List<String> members,
            List<String> themes, Long createdById, String createdByEmail) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.teacherTcc = teacherTcc;
        this.members = members;
        this.themes = themes;
        this.createdById = createdById;
        this.createdByEmail = createdByEmail;
    }

    public Tcc(TccRequestDTO data, Long createdById, String createdByEmail) {
        this.name = data.name();
        this.description = data.description();
        this.isActive = data.isActive();
        this.teacherTcc = data.teacherTcc();
        this.members = data.members();
        this.themes = data.themes();
        this.createdById = createdById;
        this.createdByEmail = createdByEmail;
    }
}

