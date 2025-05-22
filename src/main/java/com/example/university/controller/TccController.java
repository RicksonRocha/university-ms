package com.example.university.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.university.model.Tcc;
import com.example.university.repository.TccRepository;

import com.example.university.dto.TccResponseDTO;
import com.example.university.dto.AddMemberDTO;
import com.example.university.dto.TccRequestDTO;

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

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<TccResponseDTO>> getTccsByidTeacher(@PathVariable Long id) {
        List<Tcc> tccList = tccRepository.findByTeacherTcc(id);

        if (tccList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<TccResponseDTO> responseDTOs = tccList.stream()
                .map(TccResponseDTO::new)
                .toList();

        return ResponseEntity.ok(responseDTOs);
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

    @GetMapping("createby/{id}")
    public ResponseEntity<TccResponseDTO> getTccByCreateById(@PathVariable Long id) {
        Optional<Tcc> tcc = tccRepository.findByCreatedById(id);

        if (tcc.isPresent()) {
            return ResponseEntity.ok(new TccResponseDTO(tcc.get()));
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<TccResponseDTO> saveTcc(@RequestBody TccRequestDTO data) {
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

    @GetMapping("/by-member/{userId}")
    public ResponseEntity<Tcc> getTccByMemberId(@PathVariable Long userId) {
        List<Tcc> allTccs = tccRepository.findAll();

        return allTccs.stream()
                .filter(tcc -> tcc.getMembers() != null &&
                        tcc.getMembers().stream().anyMatch(member -> member.getUserId().equals(userId)))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<Map<String, String>> checkUserStatus(@PathVariable Long userId) {
        Map<String, String> response = new HashMap<>();

        List<Tcc> allTccs = tccRepository.findAll();

        for (Tcc tcc : allTccs) {
            if (tcc.getCreatedById() != null && tcc.getCreatedById().equals(userId)) {
                response.put("status", "owner");
                return ResponseEntity.ok(response);
            }
            if (tcc.getMembers() != null && tcc.getMembers().stream().anyMatch(m -> m.getUserId().equals(userId))) {
                response.put("status", "member");
                return ResponseEntity.ok(response);

            }
        }

        response.put("status", "free");
        return ResponseEntity.ok(response);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Transactional
    @PutMapping("/remove-member/{userId}")
    public ResponseEntity<Void> removeMemberFromAllTeams(@PathVariable Long userId) {
        List<Tcc> tccs = tccRepository.findAll();
        boolean updated = false;

        for (Tcc tcc : tccs) {
            if (tcc.getMembers() != null && tcc.getMembers().removeIf(member -> member.getUserId().equals(userId))) {
                tccRepository.saveAndFlush(tcc);
                updated = true;
            }
        }

        return updated ? ResponseEntity.ok().build() : ResponseEntity.noContent().build();
    }
}
