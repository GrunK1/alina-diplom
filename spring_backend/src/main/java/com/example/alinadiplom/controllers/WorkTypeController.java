package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.WorkType;
import com.example.alinadiplom.services.WorkTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-types")
public class WorkTypeController {

    @Autowired
    private WorkTypeService service;

    @PostMapping
    public ResponseEntity<WorkType> create(@RequestBody WorkType workType) {
        return new ResponseEntity<>(service.create(workType), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkType>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkType> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkType> update(@PathVariable Integer id, @RequestBody WorkType workType) {
        return ResponseEntity.ok(service.update(id, workType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}