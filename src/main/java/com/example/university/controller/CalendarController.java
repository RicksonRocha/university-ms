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

import com.example.university.dto.CalendarRequestDTO;
import com.example.university.dto.CalendarResponseDTO;
import com.example.university.model.Calendar;
import com.example.university.repository.CalendarRepository;

@RestController
@RequestMapping("calendar")
public class CalendarController {

    @Autowired
    private CalendarRepository calendarRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<CalendarResponseDTO>> getAll() {
        List<CalendarResponseDTO> calendarList = calendarRepository.findAll().stream()
                .map(CalendarResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(calendarList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarResponseDTO> getStudentById(@PathVariable Long id) {
        Optional<Calendar> calendar = calendarRepository.findById(id);

        if (calendar.isPresent()) {
            CalendarResponseDTO responseDTO = new CalendarResponseDTO(calendar.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<CalendarResponseDTO> saveCalendar(@RequestBody CalendarRequestDTO data) {
        Calendar calendarData = new Calendar(data);
        calendarData = calendarRepository.save(calendarData);
        CalendarResponseDTO responseDTO = new CalendarResponseDTO(calendarData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<CalendarResponseDTO> updateCalendar(@PathVariable Long id,
            @RequestBody CalendarRequestDTO data) {
        Optional<Calendar> existingCalendar = calendarRepository.findById(id);

        if (existingCalendar.isPresent()) {
            Calendar calendar = existingCalendar.get();

            calendar.setName(data.name());
            calendar.setIsActive(data.isActive());

            calendarRepository.save(calendar);

            CalendarResponseDTO responseDTO = new CalendarResponseDTO(calendar);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<CalendarResponseDTO> deleteCalendar(@PathVariable Long id) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);

        if (calendarOptional.isPresent()) {
            Calendar calendar = calendarOptional.get();
            calendarRepository.delete(calendar);
            CalendarResponseDTO responseDTO = new CalendarResponseDTO(calendar);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
