package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@Entity
@RequiredArgsConstructor

public class RouteList {
    @Id
    Integer mlNumber;
    Date plannedStartDate;
    Date plannedEndDate;
    String responsibleOrganization;
}
