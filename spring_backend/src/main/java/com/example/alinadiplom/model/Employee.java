package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Entity
@Data
public class Employee {
    @Id
    Integer empId;
    String name;
    String second_name;
    String middle_name;
}
