package com.example.university.dto;

public record NotificationRequestDTO(
        Long senderId,
        String nomeRemetente,
        Long receiverId,
        String nomeDestinatario,
        String message) {
}
