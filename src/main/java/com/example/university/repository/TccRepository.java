package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.university.model.Tcc;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TccRepository extends JpaRepository<Tcc, Long> {

  // Método para buscar todos os TCCs de um orientador específico
  List<Tcc> findByTeacherTcc(Long teacherTcc);

  Optional<Tcc> findByCreatedById(Long createdById);

}
