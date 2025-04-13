package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
