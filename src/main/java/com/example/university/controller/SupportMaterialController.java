package com.example.university.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.university.dto.supportmaterial.SupportMaterialRequestDTO;
import com.example.university.dto.supportmaterial.SupportMaterialResponseDTO;
import com.example.university.model.SupportMaterial;
import com.example.university.repository.SupportMaterialRepository;

@RestController
@RequestMapping("support-material")
public class SupportMaterialController {

    @Autowired
    private SupportMaterialRepository supportMaterialRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<SupportMaterialResponseDTO>> getAll(@RequestParam(required = false) Long teamId) {
        Long effectiveTeamId = teamId != null ? teamId : 1L;
        List<SupportMaterialResponseDTO> supportMaterialList = supportMaterialRepository.findAll().stream()
                .filter(material -> material.getTeamId() != null && material.getTeamId().equals(effectiveTeamId))
                .map(SupportMaterialResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(supportMaterialList);
    }

    @GetMapping("/user-team-id")
    public ResponseEntity<Long> getUserTeamId() {
        Long fakeTeamId = 1L; // ID fixo para testes
        return ResponseEntity.ok(fakeTeamId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<SupportMaterialResponseDTO> saveSupportMaterial(@RequestBody SupportMaterialRequestDTO data) {
        SupportMaterial supportMaterialData = new SupportMaterial(data.name(), data.autor(), data.link(),
                data.teamId());
        supportMaterialData = supportMaterialRepository.save(supportMaterialData);
        SupportMaterialResponseDTO responseDTO = new SupportMaterialResponseDTO(supportMaterialData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<SupportMaterialResponseDTO> updateSupportMaterial(
            @PathVariable Long id, @RequestBody SupportMaterialRequestDTO data) {
        Optional<SupportMaterial> existingSupportMaterial = supportMaterialRepository.findById(id);

        if (existingSupportMaterial.isPresent()) {
            SupportMaterial supportMaterial = existingSupportMaterial.get();
            supportMaterial.setName(data.name());
            supportMaterial.setAutor(data.autor());
            supportMaterial.setLink(data.link());
            supportMaterial.setDate(LocalDate.now());
            supportMaterial.setTeamId(data.teamId());

            supportMaterialRepository.save(supportMaterial);

            SupportMaterialResponseDTO responseDTO = new SupportMaterialResponseDTO(supportMaterial);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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