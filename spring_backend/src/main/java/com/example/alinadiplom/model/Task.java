package com.example.alinadiplom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    Long taskNumber;

    String address;
    String comment;
    Date dateOfCreation;

    @ManyToOne
    RouteList mlNumber;

    @ManyToOne
    PermissionDocument pdId;

    @Enumerated(EnumType.ORDINAL)
    private Priority priorityId;

    @ManyToOne
    WorkType wtId;

    @ManyToOne
    private Employee assignee;
}
