package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.Status;
import com.example.alinadiplom.repositories.StatusRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository repository;

    @Transactional
    public Status create(Status status) {
        status.setStatusId(null);
        return repository.save(status);
    }

    public List<Status> getAll() {
        return repository.findAll();
    }

    public Status getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + id));
    }

    public Status update(Integer id, Status newData) {
        Status status = getById(id);
        status.setStatusType(newData.getStatusType());
        return repository.save(status);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}