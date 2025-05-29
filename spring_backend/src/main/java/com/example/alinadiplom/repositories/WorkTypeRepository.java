package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
    Optional<WorkType> findByWtId(Integer wtId);
}