package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
public class EmployeeTask {
    @Id
    @GeneratedValue
    private Long id; // Добавляем простой первичный ключ

    @ManyToOne
    private Task taskNumber;

    @ManyToOne
    private Employee empId;

    private Date assignDate;
    private String taskAuthor;
}