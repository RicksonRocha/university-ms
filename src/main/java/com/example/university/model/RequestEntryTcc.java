package com.example.university.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "entry_requests_tcc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntryTcc {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // id do tcc
  private Long tccid;

  // id do solicitante
  private Long requesterId;

  // nome do solicitante;
  private String requesterName;

  // id do dono da equipe
  private Long ownerId;

  // email do dono da equipe
  private String ownerEmail;

  private LocalDateTime createdAt = LocalDateTime.now();

}
