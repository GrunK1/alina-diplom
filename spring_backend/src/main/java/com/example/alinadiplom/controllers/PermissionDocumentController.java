package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.PermissionDocument;
import com.example.alinadiplom.services.PermissionDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission-documents")
public class PermissionDocumentController {

    @Autowired
    private PermissionDocumentService service;

    @PostMapping
    public ResponseEntity<PermissionDocument> create(@RequestBody PermissionDocument document) {
        return new ResponseEntity<>(service.create(document), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PermissionDocument>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionDocument> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PermissionDocument> update(@PathVariable Integer id, @RequestBody PermissionDocument document) {
        return ResponseEntity.ok(service.update(id, document));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}