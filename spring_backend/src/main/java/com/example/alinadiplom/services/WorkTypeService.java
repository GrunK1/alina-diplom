package com.example.alinadiplom.services;

import com.example.alinadiplom.exceptions.ResourceNotFoundException;
import com.example.alinadiplom.model.WorkType;
import com.example.alinadiplom.repositories.WorkTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTypeService {

    @Autowired
    private WorkTypeRepository repository;

    public WorkType create(WorkType workType) {
        workType.setWtId(null);
        return repository.save(workType);
    }

    public List<WorkType> getAll() {
        return repository.findAll();
    }

    public WorkType getById(Integer id) {
        return repository.findByWtId(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkType not found: " + id));
    }

    public WorkType update(Integer id, WorkType newData) {
        WorkType workType = getById(id);
        workType.setWtType(newData.getWtType());
        workType.setWtDescription(newData.getWtDescription());
        return repository.save(workType);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}