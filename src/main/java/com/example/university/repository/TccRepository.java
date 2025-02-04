package com.example.university.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Tcc;

public interface TccRepository extends JpaRepository<Tcc, Long> {

    // Optional<Tcc> findByCreatedBy(String userEmail);

}
