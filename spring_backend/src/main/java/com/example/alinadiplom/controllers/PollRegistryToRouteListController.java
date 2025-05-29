package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.PollRegistryToRouteList;
import com.example.alinadiplom.services.PollRegistryToRouteListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poll-registry-to-route-lists")
public class PollRegistryToRouteListController {

    @Autowired
    private PollRegistryToRouteListService service;

    @PostMapping
    public ResponseEntity<PollRegistryToRouteList> create(@RequestBody PollRegistryToRouteList pollRegistryToRouteList) {
        return new ResponseEntity<>(service.create(pollRegistryToRouteList), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PollRegistryToRouteList>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollRegistryToRouteList> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PollRegistryToRouteList> update(@PathVariable Long id, @RequestBody PollRegistryToRouteList pollRegistryToRouteList) {
        return ResponseEntity.ok(service.update(id, pollRegistryToRouteList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}