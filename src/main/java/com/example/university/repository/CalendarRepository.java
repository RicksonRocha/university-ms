package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

}
