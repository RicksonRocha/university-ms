package com.example.university.model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  // id do remetente
  private Long senderId;

  private String nomeRemetente;

  // Destinatário da notificação
  private Long receiverId;

  private String nomeDestinatario;

  private String message;

  private boolean isUnRead = true;

  private LocalDateTime createdAt;

}
