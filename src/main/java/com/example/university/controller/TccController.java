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
import org.springframework.web.bind.annotation.ModelAttribute;
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
        Tcc tccData = new Tcc(data);
        tccData = tccRepository.save(tccData);
        TccResponseDTO responseDTO = new TccResponseDTO(tccData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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

            tccRepository.save(tcc);

            TccResponseDTO responseDTO = new TccResponseDTO(tcc);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<TccResponseDTO> deleteTcc(@PathVariable Long id) {
        Optional<Tcc> tccOptional = tccRepository.findById(id);

        if (tccOptional.isPresent()) {
            Tcc tcc = tccOptional.get();
            tccRepository.delete(tcc);
            TccResponseDTO responseDTO = new TccResponseDTO(tcc);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
