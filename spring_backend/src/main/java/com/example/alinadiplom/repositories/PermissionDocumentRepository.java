package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.PermissionDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionDocumentRepository extends JpaRepository<PermissionDocument, Integer> {
    Optional<PermissionDocument> findByPdId(Integer prId);
}