package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.WorkPosition;
import com.example.alinadiplom.services.WorkPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-positions")
public class WorkPositionController {

    @Autowired
    private WorkPositionService service;

    @PostMapping
    public ResponseEntity<WorkPosition> create(@RequestBody WorkPosition position) {
        return new ResponseEntity<>(service.create(position), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkPosition>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkPosition> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkPosition> update(@PathVariable Integer id, @RequestBody WorkPosition position) {
        return ResponseEntity.ok(service.update(id, position));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}