package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.Employee;
import com.example.alinadiplom.repositories.EmployeeWorkTypePeriodRepository;
import com.example.alinadiplom.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(service.create(employee), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody Employee employee) {
        return ResponseEntity.ok(service.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Autowired
    private EmployeeWorkTypePeriodRepository ewRepository;

    @GetMapping("/inspectors")
    public List<Employee> getActiveInspectors() {
        return ewRepository
                .findByWorkPosition_PosIdAndDateOfEndWorkIsNull(1)
                .stream()
                .map(period -> period.getEmpId())
                .distinct()
                .toList();
    }
}