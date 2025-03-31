package com.example.university.dto;

import java.time.LocalDateTime;

import com.example.university.model.Notification;

public record NotificationResponseDTO(
    Long id,
    Long senderId,
    String nomeRemetente,
    Long receiverId,
    String nomeDestinatario,
    String message,
    LocalDateTime createdAt) {
  public NotificationResponseDTO(Notification notification) {
    this(
        notification.getId(),
        notification.getSenderId(),
        notification.getNomeRemetente(),
        notification.getReceiverId(),
        notification.getNomeDestinatario(),
        notification.getMessage(),
        notification.getCreatedAt());
  }
}
