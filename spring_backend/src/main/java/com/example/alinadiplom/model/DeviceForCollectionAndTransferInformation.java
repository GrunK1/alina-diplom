package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
public class DeviceForCollectionAndTransferInformation {
    @Id
    String uspdSerialNumber;
    String uspdType;
    String uspdAddress;
    String uspdPlaceOfInstallation;
    String connectionType;

}
