package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
