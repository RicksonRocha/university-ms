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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserSyncController {

    @Autowired
    private TccRepository tccRepository;

    @Transactional
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) {
        List<Tcc> tccs = tccRepository.findAll();

        for (Tcc tcc : tccs) {
            boolean altered = false;

            if (tcc.getTeacherTcc() != null && tcc.getTeacherTcc().equals(id)) {
                tcc.setTeacherTcc(null);
                altered = true;
            }

            if (tcc.getMembers() != null) {
                List<String> currentMembers = tcc.getMembers();
                List<String> updatedMembers = currentMembers.stream()
                        .filter(memberId -> !memberId.equals(String.valueOf(id)))
                        .collect(Collectors.toList());

                if (updatedMembers.size() != currentMembers.size()) {
                    tcc.setMembers(updatedMembers);
                    altered = true;
                    System.out.println("Removido membro " + id + " da equipe " + tcc.getId());
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