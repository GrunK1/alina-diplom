package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "employee_work_type_period")
public class EmployeeWorkTypePeriod {
    @Id
    @GeneratedValue
    private Long id; // Добавляем простой первичный ключ

    @ManyToOne
    private Employee empId;

    @ManyToOne
    private WorkPosition workPosition;

    private Date dateOfStartWork;
    private Date dateOfEndWork;
}