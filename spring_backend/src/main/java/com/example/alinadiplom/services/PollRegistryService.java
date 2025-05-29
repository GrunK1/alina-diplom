package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.PollRegistry;
import com.example.alinadiplom.repositories.PollRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollRegistryService {

    @Autowired
    private PollRegistryRepository repository;

    public PollRegistry create(PollRegistry pollRegistry) {
        pollRegistry.setPrId(null);
        return repository.save(pollRegistry);
    }

    public List<PollRegistry> getAll() {
        return repository.findAll();
    }

    public PollRegistry getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PollRegistry not found: " + id));
    }

    public PollRegistry update(Integer id, PollRegistry newData) {
        PollRegistry pollRegistry = getById(id);
        pollRegistry.setPuSerialNumber(newData.getPuSerialNumber());
        pollRegistry.setPollFact(newData.getPollFact());
        pollRegistry.setPollDate(newData.getPollDate());
        pollRegistry.setIndications(newData.getIndications());
        return repository.save(pollRegistry);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}