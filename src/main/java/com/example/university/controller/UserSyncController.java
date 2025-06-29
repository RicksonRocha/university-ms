package com.example.university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.example.university.model.Tcc;
import com.example.university.model.MemberInfo;
import com.example.university.repository.TccRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Controlador REST utilizado para sincronização de dados de usuários entre microserviços
// Especialmente entre ms-login e ms-university. Permite remover ou atualizar referências a usuários

@RestController
@RequestMapping("/users")
public class UserSyncController {

    // Repositório JPA para acessar os TCCs
    @Autowired
    private TccRepository tccRepository;

    // Endpoint DELETE usado para remover todas as referências a um usuário em todos os TCCs
    @Transactional // Garante atomicidade da operação no banco
    @DeleteMapping("/remove/{id}/{name}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id, @PathVariable String name) {
        List<Tcc> tccs = tccRepository.findAll();

        for (Tcc tcc : tccs) {
            boolean altered = false;

            // Remove orientador
            if (tcc.getTeacherTcc() != null && tcc.getTeacherTcc().equals(id)) {
                tcc.setTeacherTcc(null);
                altered = true;
                System.out.println("Orientador com ID " + id + " removido do TCC " + tcc.getId());
            }

            // Remove membro pelo ID ou pelo nome
            if (tcc.getMembers() != null) {
                List<MemberInfo> updatedMembers = tcc.getMembers().stream()
                        .filter(member -> !member.getUserId().equals(id)
                                && !member.getUserName().equalsIgnoreCase(name))
                        .collect(Collectors.toList());

                if (updatedMembers.size() != tcc.getMembers().size()) {
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

    // Endpoint PUT usado para atualizar informações de um usuário em todos os TCCs nos quais ele aparece (professor)
    @PutMapping("/sync")
    public ResponseEntity<Void> syncUser(@RequestBody UserDTO user) {
        List<Tcc> tccs = tccRepository.findAll();

        for (Tcc tcc : tccs) {
            boolean altered = false;

            if (tcc.getMembers() != null && !tcc.getMembers().isEmpty()) {
                List<MemberInfo> updatedMembers = new ArrayList<>();

                for (MemberInfo member : tcc.getMembers()) {
                    if (member.getUserName().equalsIgnoreCase(user.getOldName())) {
                        member.setUserId(user.getId());
                        member.setUserName(user.getName());
                        altered = true;
                        System.out.println("Atualizando membro '" + user.getOldName() + "' para '" + user.getName()
                                + "' no TCC " + tcc.getId());
                    }
                    updatedMembers.add(member);
                }

                if (altered) {
                    tcc.setMembers(updatedMembers);
                    tccRepository.save(tcc);
                    System.out.println("TCC " + tcc.getId() + " salvo com membros atualizados.");
                }
            }
        }

        return ResponseEntity.ok().build();
    }

    // DTO utilizado para receber os dados do usuário na sincronização
    public static class UserDTO {
        private Long id;
        private String name;
        private String oldName;
        private String email;
        private String role;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOldName() {
            return oldName;
        }

        public void setOldName(String oldName) {
            this.oldName = oldName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
