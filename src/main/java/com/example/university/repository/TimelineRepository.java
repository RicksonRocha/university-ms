package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Timeline;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

}
