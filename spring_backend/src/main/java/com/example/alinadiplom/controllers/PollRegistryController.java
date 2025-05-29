package com.example.alinadiplom.controllers;

import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.repositories.PollRegistryRepository;
import com.example.alinadiplom.services.PollRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/poll-registries")
public class PollRegistryController {

    @Autowired
    private PollRegistryService service;

    @PostMapping
    public ResponseEntity<PollRegistry> create(@RequestBody PollRegistry pollRegistry) {
        return new ResponseEntity<>(service.create(pollRegistry), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PollRegistry>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollRegistry> get(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PollRegistry> update(@PathVariable Integer id, @RequestBody PollRegistry pollRegistry) {
        return ResponseEntity.ok(service.update(id, pollRegistry));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    private final PollRegistryRepository pollRegistryRepository;

    public PollRegistryController(PollRegistryRepository pollRegistryRepository) {
        this.pollRegistryRepository = pollRegistryRepository;
    }

    @GetMapping("/latest")
    public ResponseEntity<PollRegistry> getLatestPoll(@RequestParam String serial) {
        return pollRegistryRepository
                .findTopByPuSerialNumber_PuSerialNumberOrderByPollDateDesc(serial)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}