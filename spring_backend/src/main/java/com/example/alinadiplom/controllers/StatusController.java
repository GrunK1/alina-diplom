package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.Status;
import com.example.alinadiplom.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    @Autowired
    private StatusService service;

    @PostMapping
    public ResponseEntity<Status> create(@RequestBody Status status) {
        return new ResponseEntity<>(service.create(status), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Status> update(@PathVariable Integer id, @RequestBody Status status) {
        return ResponseEntity.ok(service.update(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}