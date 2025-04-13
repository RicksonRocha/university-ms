package com.example.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.university.model.Tcc;
import com.example.university.repository.TccRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserSyncController {

    @Autowired
    private TccRepository tccRepository;

    @Transactional
    @DeleteMapping("/remove/{id}/{name}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id, @PathVariable String name) {
        List<Tcc> tccs = tccRepository.findAll();

        for (Tcc tcc : tccs) {
            boolean altered = false;

            // Remover orientador pelo ID
            if (tcc.getTeacherTcc() != null && tcc.getTeacherTcc().equals(id)) {
                tcc.setTeacherTcc(null);
                altered = true;
                System.out.println("Orientador com ID " + id + " removido do TCC " + tcc.getId());
            }

            // Remover membro pelo nome
            if (tcc.getMembers() != null) {
                List<String> currentMembers = tcc.getMembers();
                List<String> updatedMembers = currentMembers.stream()
                        .filter(memberName -> !memberName.equalsIgnoreCase(name))
                        .collect(Collectors.toList());

                if (updatedMembers.size() != currentMembers.size()) {
                    tcc.setMembers(updatedMembers);
                    altered = true;
                    System.out.println("Membro '" + name + "' removido do TCC " + tcc.getId());
                }
            }

            if (altered) {
                tccRepository.save(tcc);
                System.out.println("TCC " + tcc.getId() + " salvo com alterações.");
            }
        }

        return ResponseEntity.ok().build();
    }
}