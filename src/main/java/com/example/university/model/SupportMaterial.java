package com.example.university.model;

import java.io.File;

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
    private String link;
    private File document;
    private String type;

    public SupportMaterial(String name,
            String link,
            File document, String type) {
        this.name = name;
        this.link = link;
        this.document = document;
        this.type = type;
    }

    public SupportMaterial(SupportMaterialRequestDTO data) {
        this.name = data.name();
        this.link = data.link();
        this.document = data.document();
        this.type = data.type();
    }
}
