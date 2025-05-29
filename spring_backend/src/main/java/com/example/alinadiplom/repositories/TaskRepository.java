package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}