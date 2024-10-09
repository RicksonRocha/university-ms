package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.university.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
