package com.example.university.model;

import java.util.List;

import com.example.university.dto.TccRequestDTO;
import jakarta.persistence.ElementCollection;
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
    private String orientador; // Novo campo para o orientador

    @ElementCollection
    private List<String> integrantes; // Lista de integrantes da equipe

    public Tcc(String name, String description, Boolean isActive, String orientador, List<String> integrantes) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.orientador = orientador;
        this.integrantes = integrantes;
    }

    public Tcc(TccRequestDTO data) {
        this.name = data.name();
        this.description = data.description();
        this.isActive = data.isActive();
        this.orientador = data.orientador();
        this.integrantes = data.integrantes();
    }
}

