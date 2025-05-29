package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.DeviceForCollectionAndTransferInformation;
import com.example.alinadiplom.services.DeviceForCollectionAndTransferInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceForCollectionAndTransferInformationController {

    @Autowired
    private DeviceForCollectionAndTransferInformationService service;

    @PostMapping
    public ResponseEntity<DeviceForCollectionAndTransferInformation> create(@RequestBody DeviceForCollectionAndTransferInformation device) {
        return new ResponseEntity<>(service.create(device), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DeviceForCollectionAndTransferInformation>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{serial}")
    public ResponseEntity<DeviceForCollectionAndTransferInformation> get(@PathVariable String serial) {
        return ResponseEntity.ok(service.getBySerial(serial));
    }

    @PutMapping("/{serial}")
    public ResponseEntity<DeviceForCollectionAndTransferInformation> update(@PathVariable String serial, @RequestBody DeviceForCollectionAndTransferInformation device) {
        return ResponseEntity.ok(service.update(serial, device));
    }

    @DeleteMapping("/{serial}")
    public ResponseEntity<Void> delete(@PathVariable String serial) {
        service.delete(serial);
        return ResponseEntity.noContent().build();
    }
}