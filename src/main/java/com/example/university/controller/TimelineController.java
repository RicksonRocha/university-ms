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

import com.example.university.dto.TimelineRequestDTO;
import com.example.university.dto.TimelineResponseDTO;
import com.example.university.model.Timeline;
import com.example.university.repository.TimelineRepository;

@RestController
@RequestMapping("timeline")
public class TimelineController {

    @Autowired
    private TimelineRepository timelineRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<TimelineResponseDTO>> getAll() {
        List<TimelineResponseDTO> timelineList = timelineRepository.findAll().stream()
                .map(TimelineResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timelineList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimelineResponseDTO> getTimelineById(@PathVariable Long id) {
        Optional<Timeline> timeline = timelineRepository.findById(id);

        if (timeline.isPresent()) {
            TimelineResponseDTO responseDTO = new TimelineResponseDTO(timeline.get());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<TimelineResponseDTO> saveTimeline(@RequestBody TimelineRequestDTO data) {
        Timeline timelineData = new Timeline(data);
        timelineData = timelineRepository.save(timelineData);
        TimelineResponseDTO responseDTO = new TimelineResponseDTO(timelineData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<TimelineResponseDTO> updateTimeline(@PathVariable Long id,
            @RequestBody TimelineRequestDTO data) {
        Optional<Timeline> existingTimeline = timelineRepository.findById(id);

        if (existingTimeline.isPresent()) {
            Timeline timeline = existingTimeline.get();

            timeline.setName(data.name());
            timeline.setIsActive(data.isActive());

            timelineRepository.save(timeline);

            TimelineResponseDTO responseDTO = new TimelineResponseDTO(timeline);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<TimelineResponseDTO> deleteTimeline(@PathVariable Long id) {
        Optional<Timeline> timelineOptional = timelineRepository.findById(id);

        if (timelineOptional.isPresent()) {
            Timeline timeline = timelineOptional.get();
            timelineRepository.delete(timeline);
            TimelineResponseDTO responseDTO = new TimelineResponseDTO(timeline);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
