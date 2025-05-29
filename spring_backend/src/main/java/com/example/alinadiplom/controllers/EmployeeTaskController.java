package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.EmployeeTask;
import com.example.alinadiplom.services.EmployeeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-tasks")
public class EmployeeTaskController {

    @Autowired
    private EmployeeTaskService service;

    @PostMapping
    public ResponseEntity<EmployeeTask> create(@RequestBody EmployeeTask employeeTask) {
        return new ResponseEntity<>(service.create(employeeTask), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeTask>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeTask> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeTask> update(@PathVariable Long id, @RequestBody EmployeeTask employeeTask) {
        return ResponseEntity.ok(service.update(id, employeeTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}