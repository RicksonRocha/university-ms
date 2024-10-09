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

import com.example.university.dto.StudentRequestDTO;
import com.example.university.dto.StudentResponseDTO;
import com.example.university.dto.TeacherRequestDTO;
import com.example.university.dto.StudentResponseDTO;
import com.example.university.model.Student;
import com.example.university.model.Teacher;
import com.example.university.repository.StudentRepository;

@RestController
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAll() {
        List<StudentResponseDTO> teacherList = studentRepository.findAll().stream()
                .map(StudentResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(teacherList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            StudentResponseDTO responseDTO = new StudentResponseDTO(student.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<StudentResponseDTO> saveStudent(@RequestBody StudentRequestDTO data) {
        Student studentData = new Student(data);
        studentData = studentRepository.save(studentData);
        StudentResponseDTO responseDTO = new StudentResponseDTO(studentData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id,
            @RequestBody StudentRequestDTO data) {
        Optional<Student> existingStudent = studentRepository.findById(id);

        if (existingStudent.isPresent()) {
            Student student = existingStudent.get();

            student.setRegistry(data.registry());

            studentRepository.save(student);

            StudentResponseDTO responseDTO = new StudentResponseDTO(student);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> deleteTeacher(@PathVariable Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            studentRepository.delete(student);
            StudentResponseDTO responseDTO = new StudentResponseDTO(student);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
