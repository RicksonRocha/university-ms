package com.example.university.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.university.dto.TccRequestDTO;
import com.example.university.dto.TccResponseDTO;
import com.example.university.model.Tcc;
import com.example.university.repository.TccRepository;

@RestController
@RequestMapping("tcc")
public class TccController {

    @Autowired
    private TccRepository tccRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<TccResponseDTO>> getAll() {
        List<TccResponseDTO> tccList = tccRepository.findAll().stream()
                .map(TccResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tccList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TccResponseDTO> getTccById(@PathVariable Long id) {
        Optional<Tcc> tcc = tccRepository.findById(id);

        if (tcc.isPresent()) {
            TccResponseDTO responseDTO = new TccResponseDTO(tcc.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<TccResponseDTO> saveTcc(@RequestBody TccRequestDTO data) {

        // Aqui, se espera que o payload já tenha createdById e createdByEmail,
        // extraídos pelo front a partir do serviço de login

        Tcc tccData = new Tcc(data, data.createdById(), data.createdByEmail());
        tccData = tccRepository.save(tccData);

        return ResponseEntity.status(HttpStatus.CREATED).body(new TccResponseDTO(tccData));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<TccResponseDTO> updateTcc(@PathVariable Long id,
            @RequestBody TccRequestDTO data) {

        Optional<Tcc> existingTcc = tccRepository.findById(id);

        if (existingTcc.isPresent()) {
            Tcc tcc = existingTcc.get();
            tcc.setName(data.name());
            tcc.setDescription(data.description());
            tcc.setIsActive(data.isActive());
            tcc.setTeacherTcc(data.teacherTcc());
            tcc.setMembers(data.members());
            tcc.setThemes(data.themes());
            tcc.setCreatedById(data.createdById());
            tcc.setCreatedByEmail(data.createdByEmail());

            tccRepository.save(tcc);
            return ResponseEntity.ok(new TccResponseDTO(tcc));

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTcc(@PathVariable Long id) {
        Optional<Tcc> tccOptional = tccRepository.findById(id);

        if (tccOptional.isPresent()) {
            tccRepository.delete(tccOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
