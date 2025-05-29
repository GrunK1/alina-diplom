package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.TaskStatus;
import com.example.alinadiplom.services.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-statuses")
public class TaskStatusController {

    @Autowired
    private TaskStatusService service;

    @PostMapping
    public ResponseEntity<TaskStatus> create(@RequestBody TaskStatus taskStatus) {
        return new ResponseEntity<>(service.create(taskStatus), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskStatus>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskStatus> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskStatus> update(@PathVariable Integer id, @RequestBody TaskStatus taskStatus) {
        return ResponseEntity.ok(service.update(id, taskStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}