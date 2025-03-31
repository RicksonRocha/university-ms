package com.example.university.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.university.model.RequestEntryTcc;

@Repository
public interface RequestEntryTccRepository extends JpaRepository<RequestEntryTcc, Long> {
  List<RequestEntryTcc> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
}
