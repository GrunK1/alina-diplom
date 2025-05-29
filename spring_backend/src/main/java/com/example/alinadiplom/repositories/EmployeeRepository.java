package com.example.alinadiplom.repositories;

import com.example.alinadiplom.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}