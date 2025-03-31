package com.example.university.controller;

import com.example.university.dto.RequestEntryTccRequestDTO;
import com.example.university.dto.RequestEntryTccResponseDTO;
import com.example.university.model.RequestEntryTcc;
import com.example.university.model.Notification;
import com.example.university.model.Tcc;
import com.example.university.repository.RequestEntryTccRepository;
import com.example.university.repository.NotificationRepository;
import com.example.university.repository.TccRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/request-entry")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RequestEntryTccController {

  @Autowired
  private RequestEntryTccRepository requestEntryTccRepository;

  @Autowired
  private TccRepository tccRepository;

  @Autowired
  private NotificationRepository notificationRepository;

  @PostMapping
  public ResponseEntity<RequestEntryTccResponseDTO> createRequest(@RequestBody RequestEntryTccRequestDTO dto) {
    RequestEntryTcc request = new RequestEntryTcc();
    request.setTccid(dto.getTccid());
    request.setRequesterId(dto.getRequesterId());
    request.setRequesterName(dto.getRequesterName());
    request.setOwnerId(dto.getOwnerId());
    request.setOwnerEmail(dto.getOwnerEmail());
    request.setCreatedAt(LocalDateTime.now());

    RequestEntryTcc saved = requestEntryTccRepository.save(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(saved));
  }

  @GetMapping("/owner/{ownerId}")
  public ResponseEntity<List<RequestEntryTccResponseDTO>> getRequestsByOwner(@PathVariable Long ownerId) {
    List<RequestEntryTcc> requests = requestEntryTccRepository.findByOwnerIdOrderByCreatedAtDesc(ownerId);
    List<RequestEntryTccResponseDTO> response = requests.stream()
        .map(this::toResponseDTO)
        .collect(Collectors.toList());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
    if (requestEntryTccRepository.existsById(id)) {
      requestEntryTccRepository.deleteById(id);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/{id}/accept")
  public ResponseEntity<Void> acceptRequest(@PathVariable Long id) {
    Optional<RequestEntryTcc> optionalRequest = requestEntryTccRepository.findById(id);
    if (optionalRequest.isEmpty())
      return ResponseEntity.notFound().build();

    RequestEntryTcc request = optionalRequest.get();

    Optional<Tcc> optionalTcc = tccRepository.findById(request.getTccid());
    if (optionalTcc.isEmpty())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    Tcc tcc = optionalTcc.get();
    if (!tcc.getMembers().contains(String.valueOf(request.getRequesterName()))) {
      tcc.getMembers().add(String.valueOf(request.getRequesterName()));
    }
    tccRepository.save(tcc);

    Notification notification = new Notification();
    notification.setSenderId(request.getOwnerId());
    notification.setNomeRemetente(request.getOwnerEmail());
    notification.setReceiverId(request.getRequesterId());
    notification.setNomeDestinatario(request.getRequesterName());
    notification.setMessage("Você foi aceito na equipe!");
    notification.setUnRead(true);
    notification.setCreatedAt(LocalDateTime.now());
    notificationRepository.save(notification);

    requestEntryTccRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{id}/reject")
  public ResponseEntity<Void> rejectRequest(@PathVariable Long id) {
    Optional<RequestEntryTcc> optionalRequest = requestEntryTccRepository.findById(id);
    if (optionalRequest.isEmpty())
      return ResponseEntity.notFound().build();

    RequestEntryTcc request = optionalRequest.get();

    Notification notification = new Notification();
    notification.setSenderId(request.getOwnerId());
    notification.setNomeRemetente(request.getOwnerEmail());
    notification.setReceiverId(request.getRequesterId());
    notification.setNomeDestinatario(request.getRequesterName());
    notification.setMessage("Seu pedido de entrada na equipe foi recusado.");
    notification.setUnRead(true);
    notification.setCreatedAt(LocalDateTime.now());
    notificationRepository.save(notification);

    requestEntryTccRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  private RequestEntryTccResponseDTO toResponseDTO(RequestEntryTcc entity) {
    RequestEntryTccResponseDTO dto = new RequestEntryTccResponseDTO();
    dto.setId(entity.getId());
    dto.setTccid(entity.getTccid());
    dto.setRequesterId(entity.getRequesterId());
    dto.setRequesterName(entity.getRequesterName());
    dto.setOwnerId(entity.getOwnerId());
    dto.setOwnerEmail(entity.getOwnerEmail());
    dto.setCreatedAt(entity.getCreatedAt());
    return dto;
  }
}