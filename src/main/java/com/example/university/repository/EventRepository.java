package com.example.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTeam(String team);
}
