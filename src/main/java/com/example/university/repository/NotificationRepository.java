package com.example.university.repository;

import com.example.university.model.Notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByReceiverIdOrderByCreatedAtDesc(Long receiverId);

  Long countByReceiverIdAndIsUnReadTrue(Long receiverId);
}