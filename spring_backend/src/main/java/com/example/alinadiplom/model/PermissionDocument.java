package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class PermissionDocument {
    @Id
    Integer pdId;
    String pdType;
    String pdDescription;

}
