package com.example.university.controller;

import com.example.university.dto.NotificationRequestDTO;
import com.example.university.dto.NotificationResponseDTO;
import com.example.university.model.Notification;
import com.example.university.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Controlador REST para gerenciamento de notificações
// Permite criar, listar, contar, marcar como lidas e excluir notificações
// Utiliza DTOs para trafegar dados entre o frontend e o backend e o NotificationRepository para acessar o banco

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

  @Autowired
  private NotificationRepository notificationRepository;

  @PostMapping
  public ResponseEntity<NotificationResponseDTO> createNotification(@RequestBody NotificationRequestDTO request) {
    Notification notification = new Notification();
    notification.setSenderId(request.senderId());
    notification.setNomeRemetente(request.nomeRemetente());
    notification.setReceiverId(request.receiverId());
    notification.setNomeDestinatario(request.nomeDestinatario());
    notification.setMessage(request.message());
    notification.setCreatedAt(LocalDateTime.now());

    Notification saved = notificationRepository.save(notification);
    return ResponseEntity.ok(new NotificationResponseDTO(saved));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
    Optional<Notification> notif = notificationRepository.findById(id);
    if (notif.isPresent()) {
      notificationRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/user/{receiverId}")
  public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByReceiver(@PathVariable Long receiverId) {
    List<Notification> notifications = notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId);
    List<NotificationResponseDTO> response = notifications.stream()
        .map(NotificationResponseDTO::new)
        .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @PutMapping("/user/{receiverId}/read-all")
  public ResponseEntity<Void> markAllAsRead(@PathVariable Long receiverId) {
    List<Notification> notifications = notificationRepository.findByReceiverIdOrderByCreatedAtDesc(receiverId);
    for (Notification n : notifications) {
      if (n.isUnRead()) {
        n.setUnRead(false);
      }
    }
    notificationRepository.saveAll(notifications);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/user/{receiverId}/unread-count")
  public ResponseEntity<Long> getUnreadNotificationCount(@PathVariable Long receiverId) {
    Long count = notificationRepository.countByReceiverIdAndIsUnReadTrue(receiverId);
    return ResponseEntity.ok(count);
  }

}
