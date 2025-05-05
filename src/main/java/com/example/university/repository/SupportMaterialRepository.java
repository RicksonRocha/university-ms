package com.example.university.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.university.model.SupportMaterial;

public interface SupportMaterialRepository extends JpaRepository<SupportMaterial, Long> {
    List<SupportMaterial> findByAutor(String autor);

    List<SupportMaterial> findByTeamId(Long teamId);
}
