package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.EmployeeWorkTypePeriod;
import com.example.alinadiplom.services.EmployeeWorkTypePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-work-type-periods")
public class EmployeeWorkTypePeriodController {

    @Autowired
    private EmployeeWorkTypePeriodService service;

    @PostMapping
    public ResponseEntity<EmployeeWorkTypePeriod> create(@RequestBody EmployeeWorkTypePeriod period) {
        return new ResponseEntity<>(service.create(period), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeWorkTypePeriod>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeWorkTypePeriod> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeWorkTypePeriod> update(@PathVariable Long id, @RequestBody EmployeeWorkTypePeriod period) {
        return ResponseEntity.ok(service.update(id, period));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}