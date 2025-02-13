package com.example.university.model;

import java.time.LocalDate;
import com.example.university.dto.SupportMaterialRequestDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supportmaterial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportMaterial {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String autor; 
    
    @Column(nullable = false)
    private String link;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(name = "team_id")
    private Long teamId;

    // Construtor para receber dados do DTO e definir a data automaticamente
    public SupportMaterial(SupportMaterialRequestDTO data, String userEmail) {
        this.name = data.name();
        this.autor = userEmail; // Define automaticamente o usuário logado como autor
        this.link = data.link();
        this.date = LocalDate.now();
        // Usa o valor enviado no DTO para teamId
        this.teamId = data.teamId();
    }

    // Atualiza os dados sem modificar o autor
    public void updateFromDTO(SupportMaterialRequestDTO data) {
        this.name = data.name();
        this.link = data.link();
        this.date = LocalDate.now();
    }
}
