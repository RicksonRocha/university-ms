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

import com.example.university.dto.TeamRequestDTO;
import com.example.university.dto.TeamResponseDTO;
import com.example.university.model.Team;
import com.example.university.repository.TeamRepository;

@RestController
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAll() {
        List<TeamResponseDTO> teamList = teamRepository.findAll().stream()
                .map(TeamResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teamList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamRepository.findById(id);

        if (team.isPresent()) {
            TeamResponseDTO responseDTO = new TeamResponseDTO(team.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<TeamResponseDTO> saveTeam(@RequestBody TeamRequestDTO data) {
        Team teamData = new Team(data);
        teamData = teamRepository.save(teamData);
        TeamResponseDTO responseDTO = new TeamResponseDTO(teamData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> updateTcc(@PathVariable Long id,
            @RequestBody TeamRequestDTO data) {
        Optional<Team> existingTeam = teamRepository.findById(id);

        if (existingTeam.isPresent()) {
            Team tcc = existingTeam.get();

            tcc.setIsActive(data.isActive());

            teamRepository.save(tcc);

            TeamResponseDTO responseDTO = new TeamResponseDTO(tcc);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<TeamResponseDTO> deleteTcc(@PathVariable Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);

        if (teamOptional.isPresent()) {
            Team team = teamOptional.get();
            teamRepository.delete(team);
            TeamResponseDTO responseDTO = new TeamResponseDTO(team);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
