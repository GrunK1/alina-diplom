package com.example.alinadiplom.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class PollRegistry {
    @Id
    @GeneratedValue
    Integer prId;
    @ManyToOne
    RecordDevice puSerialNumber;
    Boolean pollFact;
    Date pollDate;
    String indications;
}
