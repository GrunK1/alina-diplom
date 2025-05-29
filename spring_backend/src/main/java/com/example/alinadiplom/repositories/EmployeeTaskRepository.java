package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.EmployeeTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeTaskRepository extends JpaRepository<EmployeeTask, Long> {
}