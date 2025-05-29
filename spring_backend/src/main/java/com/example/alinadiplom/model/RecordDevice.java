package com.example.alinadiplom.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class RecordDevice {
    @Id
    String puSerialNumber;
    @ManyToOne
    DeviceForCollectionAndTransferInformation uspdSerialNumber;
    String puModel;
    String puAddress;
    String puCoordinates;


}
