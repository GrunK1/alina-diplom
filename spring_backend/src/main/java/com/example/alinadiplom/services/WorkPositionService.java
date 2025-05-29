package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.WorkPosition;
import com.example.alinadiplom.repositories.WorkPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPositionService {

    @Autowired
    private WorkPositionRepository repository;

    public WorkPosition create(WorkPosition position) {
        position.setPosId(null);
        return repository.save(position);
    }

    public List<WorkPosition> getAll() {
        return repository.findAll();
    }

    public WorkPosition getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkPosition not found: " + id));
    }

    public WorkPosition update(Integer id, WorkPosition newData) {
        WorkPosition position = getById(id);
        position.setPosTitle(newData.getPosTitle());
        return repository.save(position);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}