package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestEntryTccResponseDTO {
  private Long id;
  private Long tccid;
  private Long requesterId;
  private String requesterName;
  private Long ownerId;
  private String ownerEmail;
  private LocalDateTime createdAt;
}
