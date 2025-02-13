package com.example.university.controller;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.example.university.dto.SupportMaterialRequestDTO;
import com.example.university.dto.SupportMaterialResponseDTO;
import com.example.university.model.SupportMaterial;
import com.example.university.repository.SupportMaterialRepository;

@RestController
@RequestMapping("support-material")
@CrossOrigin(origins = "*")
public class SupportMaterialController {

    @Autowired
    private SupportMaterialRepository supportMaterialRepository;

    // Obtém todos os materiais cadastrados
    @GetMapping
    public ResponseEntity<List<SupportMaterialResponseDTO>> getAllMaterials() {
        // Obtém o usuário autenticado 
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Usuário autenticado no Spring Security: " + userEmail);
    
        List<SupportMaterial> materials = supportMaterialRepository.findAll();
    
        return ResponseEntity.ok(
            materials.stream()
                     .map(SupportMaterialResponseDTO::new)
                     .toList()
        );
    }

    // Criação de material de apoio
    @PostMapping
    public ResponseEntity<SupportMaterialResponseDTO> create(@RequestBody SupportMaterialRequestDTO data) {
        // Obtém o e-mail do usuário logado
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // Cria um novo material com o usuário logado como autor
        SupportMaterial material = new SupportMaterial(data, userEmail);
        
        material = supportMaterialRepository.save(material);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SupportMaterialResponseDTO(material));
    }

    // Atualização de material
    @PutMapping("/{id}")
    public ResponseEntity<SupportMaterialResponseDTO> update(
        @PathVariable Long id, @RequestBody SupportMaterialRequestDTO data) {
        
        return supportMaterialRepository.findById(id)
            .map(material -> {
                material.updateFromDTO(data);
                return ResponseEntity.ok(
                    new SupportMaterialResponseDTO(supportMaterialRepository.save(material))
                );
            })
            .orElse(ResponseEntity.notFound().build());
    }

    // Exclusão de material
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupportMaterial(@PathVariable Long id) {
        Optional<SupportMaterial> supportMaterialOptional = supportMaterialRepository.findById(id);

        if (supportMaterialOptional.isPresent()) {
            supportMaterialRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}