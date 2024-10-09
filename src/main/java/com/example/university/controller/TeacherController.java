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

import com.example.university.dto.TeacherRequestDTO;
import com.example.university.dto.TeacherResponseDTO;
import com.example.university.model.Teacher;
import com.example.university.repository.TeacherRepository;

@RestController
@RequestMapping("teacher")
public class TeacherController {

    @Autowired
    private TeacherRepository teacherRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<TeacherResponseDTO>> getAll() {
        List<TeacherResponseDTO> teacherList = teacherRepository.findAll().stream()
                .map(TeacherResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teacherList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> getTeacherById(@PathVariable Long id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        if (teacher.isPresent()) {
            TeacherResponseDTO responseDTO = new TeacherResponseDTO(teacher.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<TeacherResponseDTO> saveTeacher(@RequestBody TeacherRequestDTO data) {
        Teacher teacherData = new Teacher(data);
        teacherData = teacherRepository.save(teacherData);
        TeacherResponseDTO responseDTO = new TeacherResponseDTO(teacherData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> updateTeacher(@PathVariable Long id,
            @RequestBody TeacherRequestDTO data) {
        Optional<Teacher> existingteacher = teacherRepository.findById(id);

        if (existingteacher.isPresent()) {
            Teacher teacher = existingteacher.get();

            teacher.setIsAdvisor(data.isAdvisor());
            teacher.setTeachingSince(data.teachingSince());

            teacherRepository.save(teacher);

            TeacherResponseDTO responseDTO = new TeacherResponseDTO(teacher);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<TeacherResponseDTO> deleteTeacher(@PathVariable Long id) {
        Optional<Teacher> teacherOptional = teacherRepository.findById(id);

        if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            teacherRepository.delete(teacher);
            TeacherResponseDTO responseDTO = new TeacherResponseDTO(teacher);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
