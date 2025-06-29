package com.example.university.controller;

import com.example.university.dto.RequestEntryTccRequestDTO;
import com.example.university.dto.RequestEntryTccResponseDTO;
import com.example.university.model.RequestEntryTcc;
import com.example.university.model.MemberInfo;
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

/**
 * Controlador REST responsável pelo gerenciamento de solicitações de entrada em equipes de TCC.
 * Permite criar solicitações, listar as solicitações de um orientador, aceitar ou recusar pedidos,
 * adicionar membros à equipe, designar orientador e notificar os envolvidos.
 */

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

  // Lista todas as solicitações recebidas por um dono de equipe (owner)
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

  // Aceita uma solicitação de entrada de aluno na equipe
  // Adiciona o aluno na lista de membros do TCC e envia notificação
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

    // Verifica se o usuário já está na lista
    boolean alreadyMember = tcc.getMembers().stream()
        .anyMatch(m -> m.getUserId().equals(request.getRequesterId()));

    if (!alreadyMember) {
      MemberInfo newMember = new MemberInfo(request.getRequesterId(), request.getRequesterName());
      tcc.getMembers().add(newMember);
      tccRepository.save(tcc);
    }

    // Envia notificação
    Notification notification = new Notification();
    notification.setSenderId(request.getOwnerId());
    notification.setNomeRemetente(request.getOwnerEmail());
    notification.setReceiverId(request.getRequesterId());
    notification.setNomeDestinatario(request.getRequesterName());
    notification.setMessage("Você foi aceito na equipe!");
    notification.setUnRead(true);
    notification.setCreatedAt(LocalDateTime.now());
    notificationRepository.save(notification);

    // Remove a solicitação de entrada
    requestEntryTccRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  // Aceite de um pedido para um professor se tornar orientador da equipe
  // Define o professor como orientador e envia notificação
  @PostMapping("/{id}/accept/teacher")
  public ResponseEntity<Void> acceptRequestTeacher(@PathVariable Long id) {
    Optional<RequestEntryTcc> optionalRequest = requestEntryTccRepository.findById(id);
    if (optionalRequest.isEmpty())
      return ResponseEntity.notFound().build();

    RequestEntryTcc request = optionalRequest.get();

    Optional<Tcc> optionalTcc = tccRepository.findById(request.getTccid());
    if (optionalTcc.isEmpty())
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    Tcc tcc = optionalTcc.get();
    Long teacherId = request.getOwnerId();
    tcc.setTeacherTcc(teacherId);
    tccRepository.save(tcc);

    Notification notification = new Notification();
    notification.setSenderId(request.getOwnerId());
    notification.setNomeRemetente(request.getOwnerEmail());
    notification.setReceiverId(request.getRequesterId());
    notification.setNomeDestinatario(request.getRequesterName());
    notification.setMessage("Seu pedido de orientação na equipe foi aceito.");
    notification.setUnRead(true);
    notification.setCreatedAt(LocalDateTime.now());
    notificationRepository.save(notification);

    requestEntryTccRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  // Recusa uma solicitação de entrada e notifica o solicitante
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

  // Converte uma entidade para o DTO de resposta
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