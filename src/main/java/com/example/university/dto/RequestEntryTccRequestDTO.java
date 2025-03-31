package com.example.university.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestEntryTccRequestDTO {
  private Long tccid;
  private Long requesterId;
  private String requesterName;
  private Long ownerId;
  private String ownerEmail;
}
