package com.example.university.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.university.dto.SupportMaterialRequestDTO;
import com.example.university.dto.SupportMaterialResponseDTO;
import com.example.university.model.SupportMaterial;
import com.example.university.repository.SupportMaterialRepository;

@RestController
@RequestMapping("support-material")
public class SupportMaterialController {

    @Autowired
    private SupportMaterialRepository supportMaterialRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<SupportMaterialResponseDTO>> getAll() {
        List<SupportMaterialResponseDTO> supportMaterialList = supportMaterialRepository.findAll().stream()
                .map(SupportMaterialResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(supportMaterialList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportMaterialResponseDTO> getSupportMaterialById(@PathVariable Long id) {
        Optional<SupportMaterial> supportMaterial = supportMaterialRepository.findById(id);

        if (supportMaterial.isPresent()) {
            SupportMaterialResponseDTO responseDTO = new SupportMaterialResponseDTO(supportMaterial.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<SupportMaterialResponseDTO> saveSupportMaterial(@RequestBody SupportMaterialRequestDTO data) {
        SupportMaterial supportMaterialData = new SupportMaterial(data);
        supportMaterialData = supportMaterialRepository.save(supportMaterialData);
        SupportMaterialResponseDTO responseDTO = new SupportMaterialResponseDTO(supportMaterialData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<SupportMaterialResponseDTO> updateSupportMaterial(@PathVariable Long id,
            @RequestBody SupportMaterialRequestDTO data) {
        Optional<SupportMaterial> existingSupportMaterial = supportMaterialRepository.findById(id);

        if (existingSupportMaterial.isPresent()) {
            SupportMaterial supportMaterial = existingSupportMaterial.get();

            supportMaterial.setName(data.name());
            supportMaterial.setDocument(data.document());
            supportMaterial.setLink(data.link());
            supportMaterial.setType(data.type());

            supportMaterialRepository.save(supportMaterial);

            SupportMaterialResponseDTO responseDTO = new SupportMaterialResponseDTO(supportMaterial);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<SupportMaterialResponseDTO> deleteSupportMaterial(@PathVariable Long id) {
        Optional<SupportMaterial> supportMaterialOptional = supportMaterialRepository.findById(id);

        if (supportMaterialOptional.isPresent()) {
            SupportMaterial supportMaterial = supportMaterialOptional.get();
            supportMaterialRepository.delete(supportMaterial);
            SupportMaterialResponseDTO responseDTO = new SupportMaterialResponseDTO(supportMaterial);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
