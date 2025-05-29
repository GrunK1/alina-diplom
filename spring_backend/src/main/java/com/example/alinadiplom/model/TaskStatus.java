package com.example.alinadiplom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Time;
import java.util.Date;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "task_status")
public class TaskStatus {
    @Id
    @GeneratedValue
    Integer id;
    @ManyToOne
    Status statusId;
    @ManyToOne
    Task taskNumber;
    Date dateOfStatusSet;
    Time timeOfStatusSet;
}
