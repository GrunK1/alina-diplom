package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.RecordDevice;
import com.example.alinadiplom.services.RecordDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/record-devices")
public class RecordDeviceController {

    @Autowired
    private RecordDeviceService service;

    @PostMapping
    public ResponseEntity<RecordDevice> create(@RequestBody RecordDevice device) {
        return new ResponseEntity<>(service.create(device), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RecordDevice>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{serial}")
    public ResponseEntity<RecordDevice> get(@PathVariable String serial) {
        return ResponseEntity.ok(service.getBySerial(serial));
    }

    @PutMapping("/{serial}")
    public ResponseEntity<RecordDevice> update(@PathVariable String serial, @RequestBody RecordDevice device) {
        return ResponseEntity.ok(service.update(serial, device));
    }

    @DeleteMapping("/{serial}")
    public ResponseEntity<Void> delete(@PathVariable String serial) {
        service.delete(serial);
        return ResponseEntity.noContent().build();
    }
}